package ru.javawebinar.basejava.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<SectionType, Section> sections;
    private static final String LINE_SEPARATOR = System.lineSeparator();


    private Resume(Builder builder) {
        this.uuid = builder.uuid == null ? UUID.randomUUID().toString() : builder.uuid;
        this.fullName = builder.fullName;
        this.sections = builder.sections;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String uuid;
        private String fullName;
        private final Map<SectionType, Section> sections = new LinkedHashMap<>();

        private Builder() {
        }

        public Resume build() {
            return new Resume(this);
        }

        public Builder withUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder withFullName(String fullName) {
            Objects.requireNonNull(fullName, "Full name must not be null");
            this.fullName = fullName;
            return this;
        }

        public Builder withPersonal(String personalValue) {
            Objects.requireNonNull(personalValue, "Personal value must not be null");
            this.sections.put(SectionType.PERSONAL, new SingleLineSection(personalValue));
            return this;
        }

        public Builder withObjective(String objectiveValue) {
            Objects.requireNonNull(objectiveValue, "Objective value must not be null");
            this.sections.put(SectionType.OBJECTIVE, new SingleLineSection(objectiveValue));
            return this;
        }

        public Builder withAchievements(List<String> achievementsValue) {
            Objects.requireNonNull(achievementsValue, "Achievements value must not be null");
            this.sections.put(SectionType.ACHIEVEMENTS, new BulletedListSection(achievementsValue));
            return this;
        }

        public Builder withExperience(List<String> experienceValue) {
            Objects.requireNonNull(experienceValue, "Experience value must not be null");
            this.sections.put(SectionType.EXPERIENCE, new BulletedListSection(experienceValue));
            return this;
        }

        public Builder withEducation(List<String> educationValue) {
            Objects.requireNonNull(educationValue, "Education value must not be null");
            this.sections.put(SectionType.EDUCATION, new BulletedListSection(educationValue));
            return this;
        }


    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder().append("Resume: ")
                .append(fullName)
                .append(LINE_SEPARATOR)
                .append(LINE_SEPARATOR);
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            content.append(entry.getKey().getTitle())
                    .append(LINE_SEPARATOR)
                    .append(entry.getValue())
                    .append(LINE_SEPARATOR)
                    .append(LINE_SEPARATOR);
        }
        return content.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (uuid != null ? !uuid.equals(resume.uuid) : resume.uuid != null) return false;
        return fullName != null ? fullName.equals(resume.fullName) : resume.fullName == null;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}
