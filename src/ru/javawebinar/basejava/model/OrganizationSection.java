package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends Section implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Organization> experience;

    public OrganizationSection(List<Organization> experience) {
        this.experience = experience;
    }

    public OrganizationSection() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return experience != null ? experience.equals(that.experience) : that.experience == null;
    }

    @Override
    public int hashCode() {
        return experience != null ? experience.hashCode() : 0;
    }

    @Override
    public String toString() {
        String lineSeparator = System.lineSeparator();
        StringBuilder content = new StringBuilder();
        for (Organization anExperience : experience) {
            content.append(anExperience)
                    .append(lineSeparator)
                    .append(lineSeparator);
        }
        return content.toString();
    }
}
