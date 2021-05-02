package ru.javawebinar.basejava.model;

public enum ContactName {
    CONTACT_PHONE_NUMBER("Тел.: "),
    SKYPE("Skype: "),
    E_MAIL("Почта: "),
    LINKED_IN_PROFILE("Профиль LinkedIn: "),
    GITHUB_PROFILE("Профиль GitHub: "),
    STACKOVERFLOW_PROFILE("Профиль Stackoverflow: "),
    HOME_PAGE("Домашняя страница: ");

    private String title;

    ContactName(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
