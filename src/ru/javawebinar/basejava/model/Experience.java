package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Experience {
    private final Link homePage;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    private Experience(Builder builder) {
        this.homePage = builder.homePage;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.title = builder.title;
        this.description = builder.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (!homePage.equals(that.homePage)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!title.equals(that.title)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        String lineSeparator = System.lineSeparator();
        String lineSeparatorPlusDescription = description == null? "": System.lineSeparator() + description;

        return homePage.toString() + lineSeparator +
                formatDate(startDate) + " - " + formatDate(endDate) + lineSeparator +
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

    public static class Builder {
        private Link homePage;
        private LocalDate startDate;
        private LocalDate endDate;
        private String title;
        private String description;

        private Builder() {
        }

        public Experience build() {
            return new Experience(this);
        }

        public Builder homePage(String companyName, String webSite) {
            this.homePage = new Link (companyName, webSite);
            return this;
        }

        public Builder withStartDate(LocalDate startDate) {
            Objects.requireNonNull(startDate, "Start date must not be null");
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(LocalDate endDate) {
            Objects.requireNonNull(endDate, "End date must not be null");
            this.endDate = endDate;
            return this;
        }

        public Builder withTitle (String title) {
            Objects.requireNonNull(title, "Title must not be null");
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }
    }
}
