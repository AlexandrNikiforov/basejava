package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Experience {
    private final Link homePage;
    private final List<ExperienceItem> experienceItems;

    private Experience(Builder builder) {
        this.homePage = builder.homePage;
        this.experienceItems = builder.experienceItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (homePage != null ? !homePage.equals(that.homePage) : that.homePage != null) return false;
        return experienceItems != null ? experienceItems.equals(that.experienceItems) : that.experienceItems == null;
    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + (experienceItems != null ? experienceItems.hashCode() : 0);
        return result;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        String lineSeparator = System.lineSeparator();

        StringBuilder sb = new StringBuilder()
                .append(homePage.toString())
                .append(lineSeparator);

        for (ExperienceItem experienceItem : experienceItems) {
            sb.append(experienceItem);
            sb.append(lineSeparator);
        }

        return sb.toString();
    }

    public static class Builder {
        private Link homePage;
        private List<ExperienceItem> experienceItems;

        private Builder() {
        }

        public Experience build() {
            return new Experience(this);
        }

        public Builder homePage(String companyName, String webSite) {
            this.homePage = new Link(companyName, webSite);
            return this;
        }

        public Builder withExperience(List<ExperienceItem> experienceItems) {
            Objects.requireNonNull(experienceItems, "Experience must not be null");
            this.experienceItems = experienceItems;
            return this;
        }
    }

    public static class ExperienceItem {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public ExperienceItem(ExperienceItemBuilder builder) {
            this.startDate = builder.startDate;
            this.endDate = builder.endDate;
            this.title = builder.title;
            this.description = builder.description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ExperienceItem that = (ExperienceItem) o;

            if (!startDate.equals(that.startDate)) return false;
            if (!endDate.equals(that.endDate)) return false;
            if (!title.equals(that.title)) return false;
            return description != null ? description.equals(that.description) : that.description == null;
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {

            String lineSeparator = System.lineSeparator();
            String lineSeparatorPlusDescription = description == null ? "" : System.lineSeparator() + description;

            return formatDate(startDate) + " - " + formatDate(endDate) + lineSeparator +
                    title +
                    lineSeparatorPlusDescription;
        }

        private String formatDate(LocalDate date) {
            if (date.equals(LocalDate.now())) {
                return "now";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.yyyy");
            return date.format(formatter);
        }


        public static ExperienceItemBuilder experienceItemBuilder() {
            return new ExperienceItemBuilder();
        }
    }

    public static class ExperienceItemBuilder {
        private LocalDate startDate;
        private LocalDate endDate;
        private String title;
        private String description;

        private ExperienceItemBuilder() {
        }

        public ExperienceItem build() {
            return new ExperienceItem(this);
        }

        public ExperienceItemBuilder withStartDate(LocalDate startDate) {
            Objects.requireNonNull(startDate, "Start date must not be null");
            this.startDate = startDate;
            return this;
        }

        public ExperienceItemBuilder withEndDate(LocalDate endDate) {
            Objects.requireNonNull(endDate, "End date must not be null");
            this.endDate = endDate;
            return this;
        }

        public ExperienceItemBuilder withTitle(String title) {
            Objects.requireNonNull(title, "Title must not be null");
            this.title = title;
            return this;
        }

        public ExperienceItemBuilder withDescription(String description) {
            this.description = description;
            return this;
        }
    }
}
