package ru.javawebinar.basejava.model;

public enum SectionName {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENTS("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionName(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
