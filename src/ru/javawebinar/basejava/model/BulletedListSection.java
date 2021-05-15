package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class BulletedListSection extends Section implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> contentList;

    public BulletedListSection(List<String> contentList) {
        Objects.requireNonNull(contentList, "items must not be null");
        this.contentList = contentList;
    }

    public BulletedListSection() {
    }

    public BulletedListSection(String... content) {
        this(Arrays.asList(content));
    }

    public List<String> getContentList() {
        return contentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BulletedListSection that = (BulletedListSection) o;

        return contentList.equals(that.contentList);
    }

    @Override
    public int hashCode() {
        return contentList.hashCode();
    }

    @Override
    public String toString() {
        char bulletPointSign = '\u2022';
        String lineSeparator = System.lineSeparator();
        StringBuilder content = new StringBuilder();
        for (String line : contentList) {
            content.append(bulletPointSign)
                    .append(" ")
                    .append(line)
                    .append(lineSeparator);
        }
        return content.toString();
    }
}
