package ru.javawebinar.basejava.model;

import java.util.List;

public class Organization implements Section {
    private final List<ExperienceDescription> experience;

    public Organization(List<ExperienceDescription> anExperience) {
        this.experience = anExperience;
    }

    @Override
    public String toString() {
        String lineSeparator = System.lineSeparator();
        StringBuilder content = new StringBuilder();
        for (ExperienceDescription anExperience : experience) {
            content.append(anExperience)
                    .append(lineSeparator)
                    .append(lineSeparator);
        }
        return content.toString();
    }
}
