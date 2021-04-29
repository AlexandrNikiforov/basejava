package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExperienceDescription2 implements ExperienceDescription{
    private final String companyName;
    private final String companyWebSite;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String position;
    private final String description;
    private static final String SPACE_CHARACTER = " ";
    private static final String DASH_CHARACTER = "-";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private ExperienceDescription2(Builder builder) {
        this.companyName = builder.companyName;
        this.companyWebSite = builder.companyWebSite;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.position = builder.position;
        this.description = builder.description;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return companyName +
                LINE_SEPARATOR +
                companyWebSite +
                LINE_SEPARATOR +
                formatDate(startDate) +
                SPACE_CHARACTER +
                DASH_CHARACTER +
                SPACE_CHARACTER +
                formatDate(endDate) +
                LINE_SEPARATOR +
                position +
                LINE_SEPARATOR +
                description;
    }

    private String formatDate(LocalDate date) {
        if (date.equals(LocalDate.now())) {
            return "now";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.yyyy");
        return date.format(formatter);
    }

    public static class Builder {
        private String companyName;
        private String companyWebSite;
        private LocalDate startDate;
        private LocalDate endDate;
        private String position;
        private String description;

        private Builder() {
        }

        public ExperienceDescription2 build() {
            return new ExperienceDescription2(this);
        }

        public Builder withCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder withCompanyWebSite(String companyWebSite) {
            this.companyWebSite = companyWebSite;
            return this;
        }

        public Builder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder withPosition(String position) {
            this.position = position;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }
    }
}
