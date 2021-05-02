package ru.javawebinar.basejava.model;

public class SingleLineSection implements Section {
    private final String content;

    public SingleLineSection(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
