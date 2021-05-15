package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.of;
import static ru.javawebinar.basejava.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    private Link homePage;
    private List<Position> positions;

    public Organization() {
    }

    private Organization(Builder builder) {
        this.homePage = builder.homePage;
        this.positions = builder.experienceItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (homePage != null ? !homePage.equals(that.homePage) : that.homePage != null) return false;
        return positions != null ? positions.equals(that.positions) : that.positions == null;
    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + (positions != null ? positions.hashCode() : 0);
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

        for (Position experienceItem : positions) {
            sb.append(experienceItem);
            sb.append(lineSeparator);
        }

        return sb.toString();
    }

    public static class Builder {
        private Link homePage;
        private List<Position> experienceItems;

        private Builder() {
        }

        public Organization build() {
            return new Organization(this);
        }

        public Builder homePage(String companyName, String webSite) {
            this.homePage = new Link(companyName, webSite);
            return this;
        }

        public Builder withExperience(List<Position> experienceItems) {
            Objects.requireNonNull(experienceItems, "Experience must not be null");
            this.experienceItems = experienceItems;
            return this;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public Position() {
        }

        public Position(PositionBuilder builder) {
            this.startDate = builder.startDate;
            this.endDate = builder.endDate;
            this.title = builder.title;
            this.description = builder.description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position that = (Position) o;

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
            if (date.equals(NOW)) {
                return "now";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.yyyy");
            return date.format(formatter);
        }

        public static PositionBuilder experienceItemBuilder() {
            return new PositionBuilder();
        }
    }

    public static class PositionBuilder {
        private LocalDate startDate;
        private LocalDate endDate;
        private String title;
        private String description;

        private PositionBuilder() {
        }

        public Position build() {
            return new Position(this);
        }

        public PositionBuilder withStartDate(int startYear, Month startMonth) {
            Objects.requireNonNull(startYear, "Start year must not be null");
            Objects.requireNonNull(startMonth, "Start month must not be null");
            this.startDate = of(startYear, startMonth);
            return this;
        }

        public PositionBuilder withEndDate(int endYear, Month endMonth) {
            Objects.requireNonNull(endYear, "End year must not be null");
            Objects.requireNonNull(endMonth, "End month must not be null");
            this.endDate = of(endYear, endMonth);
            return this;
        }

        public PositionBuilder withoutEndDate() {
            this.endDate = NOW;
            return this;
        }

        public PositionBuilder withTitle(String title) {
            Objects.requireNonNull(title, "Title must not be null");
            this.title = title;
            return this;
        }

        public PositionBuilder withDescription(String description) {
            this.description = description;
            return this;
        }
    }
}
