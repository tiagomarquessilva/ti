package jaxb.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement(name = "placeholder")
@XmlAccessorType(XmlAccessType.FIELD)
public class Placeholder {
    private UUID id;
    private String firstname;
    private int age;

    public Placeholder() {
    }

    public Placeholder(UUID id, String firstname, int age) {
        this.id = id;
        this.firstname = firstname;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
