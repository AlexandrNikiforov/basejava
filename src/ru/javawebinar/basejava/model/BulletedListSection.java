package ru.javawebinar.basejava.model;

import java.util.List;

public class BulletedListSection implements Section {
    private final List<String> contentList;
    public static final char SPACE_CHARACTER = ' ';
    public static final char BULLET_POINT_SIGN = '\u2022';

    public BulletedListSection(List<String> contentList) {
        this.contentList = contentList;
    }

    @Override
    public String toString() {
        String lineSeparator = System.lineSeparator();
        StringBuilder content = new StringBuilder();
        for (String line : contentList) {
            content.append(BULLET_POINT_SIGN)
                    .append(SPACE_CHARACTER)
                    .append(line)
                    .append(lineSeparator);
        }
        return content.toString();
    }
}
