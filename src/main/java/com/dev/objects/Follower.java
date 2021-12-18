package com.dev.objects;

import javax.persistence.*;

@Entity
@Table(name = "followers")
public class Follower {

    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "follower")
    private UserObject follower;

    @Column (name = "followed")
    private UserObject followed;

    @Column (name = "view_count")
    private int view_count;

    @Column (name = "creation_date")
    private String creation_date;




    public Follower(){

    }

    public Follower(int id, UserObject follower, UserObject followed, int view_count, String creation_date) {
        this.id = id;
        this.follower = follower;
        this.followed = followed;
        this.view_count = view_count;
        this.creation_date = creation_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserObject getFollower() {
        return follower;
    }

    public void setFollower(UserObject follower) {
        this.follower = follower;
    }

    public UserObject getFollowed() {
        return followed;
    }

    public void setFollowed(UserObject followed) {
        this.followed = followed;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
