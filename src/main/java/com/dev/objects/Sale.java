package com.dev.objects;

import javax.persistence.*;

@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "store")
    private Store store;

    //test2
    @Column(name = "beginDate")
    private String beginDate;

    @Column(name = "expirationDate")
    private String expirationDate;

    @Column(name = "forAllUsers")
    private boolean forAllUsers;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isForAllUsers() {
        return forAllUsers;
    }

    public void setForAllUsers(boolean forAllUsers) {
        this.forAllUsers = forAllUsers;
    }
}
