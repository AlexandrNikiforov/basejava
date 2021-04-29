package ru.javawebinar.basejava.model;

import java.util.List;

public class Experience implements Section {
//    private final Map<String, List<ExperienceDescription>> contentList;
    private final List<ExperienceDescription> experience;
    public static final char SPACE_CHARACTER = ' ';
    public static final char BULLET_POINT_SIGN = '\u2022';
    private static final String LINE_SEPARATOR = System.lineSeparator();

//    public Experience(String companyName, List<String> contentList) {
    public Experience(List<ExperienceDescription> anExperience) {
//        this.contentList = new LinkedHashMap<>();
        this.experience = anExperience;

    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        for (ExperienceDescription anExperience : experience) {
            content.append(anExperience)
                    .append(LINE_SEPARATOR)
                    .append(LINE_SEPARATOR);
        }
        return content.toString();
    }
}
