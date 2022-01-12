package com.dev.objects;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstTimeLoggedIn")
    private boolean firstTimeLoggedIn;

    @Column(name = "token")
    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFirstTimeLoggedIn() {
        return firstTimeLoggedIn;
    }

    public void setFirstTimeLoggedIn(boolean firstTimeLoggedIn) {
        this.firstTimeLoggedIn = firstTimeLoggedIn;
    }
}
