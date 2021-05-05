package ru.javawebinar.basejava.model;

import java.util.List;

public class OrganizationSection implements Section {
    private final List<Organization> experience;

    public OrganizationSection(List<Organization> experience) {
        this.experience = experience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return experience != null ? experience.equals(that.experience) : that.experience == null;
    }

    @Override
    public int hashCode() {
        return experience != null ? experience.hashCode() : 0;
    }

    @Override
    public String toString() {
        String lineSeparator = System.lineSeparator();
        StringBuilder content = new StringBuilder();
        for (Organization anExperience : experience) {
            content.append(anExperience)
                    .append(lineSeparator)
                    .append(lineSeparator);
        }
        return content.toString();
    }
}
