package com.dev.objects;


import javax.persistence.*;

@Entity
@Table (name = "posts")
public class PostObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @Column (name = "sender_name")
    private String senderName;

    @Column (name = "creation_date")
    private String date;

    @Column (name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserObject userObject;


    public UserObject getUserObject() {
        return userObject;
    }

    public void setUserObject(UserObject userObject) {
        this.userObject = userObject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
