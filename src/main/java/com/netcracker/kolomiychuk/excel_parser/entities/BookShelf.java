package com.netcracker.kolomiychuk.excel_parser.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookShelf implements Entity {

    private int shelfNumber;
    private int length;

    public int getShelfNumber() {
        return shelfNumber;
    }

    @XmlElement
    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public int getLength() {
        return length;
    }

    @XmlElement
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "BookShelf{" +
                "shelfNumber=" + shelfNumber +
                ", length=" + length +
                '}';
    }
}
