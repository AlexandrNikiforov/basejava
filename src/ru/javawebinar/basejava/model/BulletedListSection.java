package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BulletedListSection implements Section, Serializable {
    private static final long serialVersionUID = 1L;
    private final List<String> contentList;

    public BulletedListSection(List<String> contentList) {
        Objects.requireNonNull(contentList, "items must not be null");
        this.contentList = contentList;
    }

    public BulletedListSection(String... content) {
        this(Arrays.asList(content));
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
