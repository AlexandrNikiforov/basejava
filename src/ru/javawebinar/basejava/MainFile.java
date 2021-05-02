package ru.javawebinar.basejava;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class MainFile {
    public static void main(String[] args) {
//        String filePath = "./.gitignore";
//
////        File file = new File(filePath);
////        try {
////            System.out.println(file.getCanonicalPath());
////        } catch (IOException e) {
////            throw new RuntimeException("Error", e);
////        }
//
//        File dir = new File("./src/ru/javawebinar/basejava");
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if (list != null) {
//            for (String name : list) {
//                System.out.println(name);
//            }
//        }
//
//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        printAllFilesNamesWithComparator("./src/ru/javawebinar/basejava");
        File dir = new File("./src/ru/javawebinar/basejava");
        printAllFilesNames(dir);
    }


    //это выводило все файлы, но не сортировало по директориям
    private static void printAllFilesNames(File dir) {
        String offset = "";
        printAllFilesNames0(dir, offset);
    }

    private static void printAllFilesNames0(File dir, String offset) {
        File[] files = sortFiles(dir);
//        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(offset + "F: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(offset + "D: " + file.getName());
                    printAllFilesNames0(file, offset + "  ");
                }
            }
        }
    }

    private static File[] sortFiles(File dir) {
        File[] filesList = dir.listFiles();

        Comparator<File> comparator = (file1, file2) -> {
            if (!file1.isDirectory() && file2.isDirectory()) {
                return -1;
            } else if (file1.isDirectory() && !file2.isDirectory()) {
                return 1;
            }
            return 0;
        };
        if (filesList != null) {
            filesList = Arrays.stream(filesList).sorted(comparator).toArray(File[]::new);
        }
        return filesList;
    }
}
