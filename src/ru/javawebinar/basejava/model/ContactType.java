package ru.javawebinar.basejava.model;

public enum ContactType implements SectionName {
    CONTACT_PHONE_NUMBER("Тел.: "),
    SKYPE("Skype: "),
    E_MAIL("Почта: "),
    LINKED_IN_PROFILE("Профиль LinkedIn: "),
    GITHUB_PROFILE("Профиль GitHub: "),
    STACKOVERFLOW_PROFILE("Профиль Stackoverflow: "),
    HOME_PAGE("Домашняя страница: ");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
