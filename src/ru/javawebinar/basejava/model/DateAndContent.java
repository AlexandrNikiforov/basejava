package ru.javawebinar.basejava.model;

import java.time.LocalDate;

public class DateAndContent {
    public static final String SPACE_CHARACTER = " ";
    public static final String DASH_CHARACTER = "-";
    public static final String LINE_SEPARATOR = System.lineSeparator();
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;

    public DateAndContent(LocalDate startDate, LocalDate endDate, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    @Override
    public String toString() {
        return startDate +
                SPACE_CHARACTER +
                DASH_CHARACTER +
                SPACE_CHARACTER +
                endDate +
                LINE_SEPARATOR +
                description;
    }
}
