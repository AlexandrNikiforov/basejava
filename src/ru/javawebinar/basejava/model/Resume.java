package ru.javawebinar.basejava.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final String uuid;
    private final String fullName;
    private final Map<SectionName, Section> sections;
    private final Map<ContactName, String> contacts;

    private Resume(Builder builder) {
        this.uuid = builder.uuid == null ? UUID.randomUUID().toString() : builder.uuid;
        this.fullName = builder.fullName;
        this.sections = builder.sections;
        this.contacts = builder.contacts;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String uuid;
        private String fullName;
        private final Map<SectionName, Section> sections = new LinkedHashMap<>();
        private final Map<ContactName, String> contacts = new LinkedHashMap<>();

        private Builder() {
        }

        public Resume build() {
            return new Resume(this);
        }

        public Builder withUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            Objects.requireNonNull(phoneNumber, "Phone number must not be null");
            this.contacts.put(ContactName.CONTACT_PHONE_NUMBER, phoneNumber);
            return this;
        }

        public Builder withSkype(String skype) {
            Objects.requireNonNull(skype, "Skype profile must not be null");
            this.contacts.put(ContactName.SKYPE, skype);
            return this;
        }

        public Builder withEmail(String email) {
            Objects.requireNonNull(email, "Email must not be null");
            this.contacts.put(ContactName.E_MAIL, email);
            return this;
        }

        public Builder withLinkedIn(String linkedInProfile) {
            Objects.requireNonNull(linkedInProfile, "LinkedIn profile must not be null");
            this.contacts.put(ContactName.LINKED_IN_PROFILE, linkedInProfile);
            return this;
        }

        public Builder withGitHubProfile(String gitHubProfile) {
            Objects.requireNonNull(gitHubProfile, "GitHub profile must not be null");
            this.contacts.put(ContactName.GITHUB_PROFILE, gitHubProfile);
            return this;
        }

        public Builder withStackoverflow(String stackoverflowProfile) {
            Objects.requireNonNull(stackoverflowProfile, "Stackoverflow profile must not be null");
            this.contacts.put(ContactName.STACKOVERFLOW_PROFILE, stackoverflowProfile);
            return this;
        }

        public Builder withHomePage(String homePage) {
            Objects.requireNonNull(homePage, "Home page profile must not be null");
            this.contacts.put(ContactName.HOME_PAGE, homePage);
            return this;
        }

        public Builder withFullName(String fullName) {
            Objects.requireNonNull(fullName, "Full name must not be null");
            this.fullName = fullName;
            return this;
        }

        public Builder withPersonal(String personalValue) {
            Objects.requireNonNull(personalValue, "Personal value must not be null");
            this.sections.put(SectionName.PERSONAL, new SingleLineSection(personalValue));
            return this;
        }

        public Builder withObjective(String objectiveValue) {
            Objects.requireNonNull(objectiveValue, "Objective value must not be null");
            this.sections.put(SectionName.OBJECTIVE, new SingleLineSection(objectiveValue));
            return this;
        }

        public Builder withAchievements(List<String> achievementsValue) {
            Objects.requireNonNull(achievementsValue, "Achievements value must not be null");
            this.sections.put(SectionName.ACHIEVEMENTS, new BulletedListSection(achievementsValue));
            return this;
        }

        public Builder withQualifications(List<String> achievementsValue) {
            Objects.requireNonNull(achievementsValue, "Qualifications value must not be null");
            this.sections.put(SectionName.QUALIFICATIONS, new BulletedListSection(achievementsValue));
            return this;
        }

        public Builder withExperience(List<ExperienceDescription> experienceValue) {
            Objects.requireNonNull(experienceValue, "Experience value must not be null");
            this.sections.put(SectionName.EXPERIENCE, new Organization(experienceValue));
            return this;
        }

        public Builder withEducation(List<ExperienceDescription> educationValue) {
            Objects.requireNonNull(educationValue, "Education value must not be null");
            this.sections.put(SectionName.EDUCATION, new Organization(educationValue));
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

        for (Map.Entry<ContactName, String> entry : contacts.entrySet()) {
            content.append(entry.getKey().getTitle())
                    .append(entry.getValue())
                    .append(LINE_SEPARATOR);
        }
            content.append(LINE_SEPARATOR);

        for (Map.Entry<SectionName, Section> entry : sections.entrySet()) {
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
