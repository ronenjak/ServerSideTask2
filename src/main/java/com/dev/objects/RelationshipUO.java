package com.dev.objects;

import javax.persistence.*;

@Entity
@Table(name = "relationshipUO")
public class RelationshipUO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int id;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn (name = "organization_id")
    private Organization organization;

    public RelationshipUO(){

    }
    public RelationshipUO(User user, Organization organization) {
        this.user = user;
        this.organization = organization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }


}
