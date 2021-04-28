package ru.javawebinar.basejava.model;

public class SingleLineSection implements Section {
    private final String sectionContent;
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public SingleLineSection(String sectionContent) {
        this.sectionContent = sectionContent;
    }

    @Override
    public String toString() {
        return sectionContent.toString();
    }
}
