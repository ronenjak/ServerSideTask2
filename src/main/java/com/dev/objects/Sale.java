package com.dev.objects;

import org.json.JSONObject;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate beginDate;

    @Column(name = "expirationDate")
    private LocalDate expirationDate;

    @Column(name = "forAllUsers")
    private boolean forAllUsers;

    @Column(name = "notifiedStatus")
    private int notifiedStatus;


    public int getNotifiedStatus() {
        return notifiedStatus;
    }

    public void setNotifiedStatus(int notifiedStatus) {
        this.notifiedStatus = notifiedStatus;
    }

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

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isForAllUsers() {
        return forAllUsers;
    }

    public void setForAllUsers(boolean forAllUsers) {
        this.forAllUsers = forAllUsers;
    }

    /*public JSONObject toJsonObject(){
        JSONObject sale = new JSONObject();
        JSONObject store = new JSONObject();
        sale.put("description", this.description);
        sale.put("expirationDate", this.expirationDate.toString());
        store.put("name", this.store.getName());
        store.put("area", this.store.getArea());
        sale.put("store",store);
        return sale;
    }*/

    public String toString(){
        return "Sale: " + description + " at the store: " + store.getName();
    }
}
