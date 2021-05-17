package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String companyWebSite;

    public Link() {
    }

    public Link(String name, String companyWebSite) {
        Objects.requireNonNull(name, "Name should not be null");
        this.name = name;
        this.companyWebSite = companyWebSite == null? "": companyWebSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!name.equals(link.name)) return false;
        return companyWebSite != null ? companyWebSite.equals(link.companyWebSite) : link.companyWebSite == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (companyWebSite != null ? companyWebSite.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String lineSeparatorPlusCompanyWebSite = companyWebSite == null ? "" : System.lineSeparator() + companyWebSite;
        return name +
                lineSeparatorPlusCompanyWebSite;
    }

    public String getName() {
        return name;
    }

    public String getCompanyWebSite() {
        return companyWebSite;
    }
}
