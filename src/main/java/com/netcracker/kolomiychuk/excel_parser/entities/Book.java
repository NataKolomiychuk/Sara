package com.netcracker.kolomiychuk.excel_parser.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement
public class Book implements Entity {
    private String name;
    private int pagesCount;

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    @XmlElement
    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", pagesCount=" + pagesCount +
                '}';
    }
}

