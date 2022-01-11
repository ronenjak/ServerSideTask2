package com.dev.objects;
import javax.persistence.*;

@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "members")
    int members;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

}
