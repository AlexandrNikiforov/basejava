package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Map;

public class Experience implements Section {
    private final Map<String, List<DateAndContent>> contentList;
    public static final char SPACE_CHARACTER = ' ';
    public static final char BULLET_POINT_SIGN = '\u2022';
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public Experience(List<String> contentList) {
        this.contentList = contentList;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        for (String line : contentList) {
            content.append(BULLET_POINT_SIGN)
                    .append(SPACE_CHARACTER)
                    .append(line)
                    .append(LINE_SEPARATOR);
        }
        return content.toString();
    }
}
