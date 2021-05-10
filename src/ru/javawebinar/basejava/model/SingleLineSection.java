package ru.javawebinar.basejava.model;

import java.io.Serializable;

public class SingleLineSection implements Section, Serializable {
    private static final long serialVersionUID = 1L;
    private final String content;

    public SingleLineSection(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleLineSection that = (SingleLineSection) o;

        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }

    @Override
    public String toString() {
        return content;
    }
}
