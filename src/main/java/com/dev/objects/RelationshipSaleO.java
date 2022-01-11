package com.dev.objects;
import javax.persistence.*;

@Entity
@Table(name = "relationshipSaleO")
public class RelationshipSaleO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int id;

    @ManyToOne
    @JoinColumn (name = "sale_id")
    private Sale sale;

    @ManyToOne
    @JoinColumn (name = "organization_id")
    private Organization organization;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }











}
