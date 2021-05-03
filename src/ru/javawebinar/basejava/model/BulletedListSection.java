package ru.javawebinar.basejava.model;

import java.util.List;

public class BulletedListSection implements Section {
    private final List<String> contentList;

    public BulletedListSection(List<String> contentList) {
        this.contentList = contentList;
    }

    public List<String> getContentList() {
        return contentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BulletedListSection that = (BulletedListSection) o;

        return contentList.equals(that.contentList);
    }

    @Override
    public int hashCode() {
        return contentList.hashCode();
    }

    @Override
    public String toString() {
        char bulletPointSign = '\u2022';
        String lineSeparator = System.lineSeparator();
        StringBuilder content = new StringBuilder();
        for (String line : contentList) {
            content.append(bulletPointSign)
                    .append(" ")
                    .append(line)
                    .append(lineSeparator);
        }
        return content.toString();
    }
}
