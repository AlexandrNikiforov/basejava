//package ru.javawebinar.basejava.storage;
//
//import java.io.File;
//
//public enum SerializationType {
//    FILE {
//        public SerializationTechnology getSerializer(String directory) {
//            File file = new File(directory);
//            return new FileStorage (file);
//        }
//    },
//    PATH {
//        public SerializationTechnology getSerializer(String directory) {
//            return new PathStorage(directory);
//        }
//    };
//
//    abstract SerializationTechnology getSerializer(String directory);
//}
