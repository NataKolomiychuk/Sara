package com.netcracker.kolomiychuk.excel_parser.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Author implements Entity{
    private String firstName;
    private String lastName;
    private Integer age;

    public String getFirstName() {
        return firstName;
    }

    @XmlElement
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @XmlElement
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    @XmlElement
    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName=" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
