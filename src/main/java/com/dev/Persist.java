package com.dev;

import com.dev.objects.PostObject;
import com.dev.objects.UserObject;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class Persist {
    private Connection connection;

    private final SessionFactory sessionFactory;

    @Autowired
    public Persist (SessionFactory sf) {
        this.sessionFactory = sf;
    }


    @PostConstruct
    public void createConnectionToDatabase () {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ashCollege", "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String doesUserExists (String username, String password) {
        String token = null;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT token FROM users WHERE username = ? AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                token = resultSet.getString("token");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return token;
    }

    public boolean createAccount (String username, String password, String token) {
        boolean success = false;
        if(!userNameAlreadyExists(username)) {
            try {
                PreparedStatement preparedStatement = this.connection.prepareStatement(
                        "INSERT INTO users (username, password, token) VALUES (?, ?, ?)");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3,token);
                preparedStatement.executeUpdate();
                success = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean addPost(String token,String content, String currentDateTime){
        boolean success = false;
        UserObject userObject = getUserByToken(token);
        if(userObject != null){
            try{

                PreparedStatement preparedStatement = this.connection.prepareStatement(
                        "INSERT INTO posts (content, creation_date,author_id) VALUES (?,?,?)"
                );
                preparedStatement.setString(1,content);
                preparedStatement.setString(2,currentDateTime);
                preparedStatement.setInt(3,userObject.getId());
                preparedStatement.executeUpdate();
                success = true;
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
        return success;

    }

    public List<PostObject> getUserPostsByToken(String token){
        List<PostObject> posts = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT content,creation_date FROM posts p INNER JOIN users u ON u.id = p.author_id WHERE u.token = ?"
            );
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                PostObject postObject = new PostObject();
                postObject.setDate(resultSet.getString("creation_date"));
                postObject.setContent(resultSet.getString("content"));
                posts.add(postObject);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return posts;
    }

    public UserObject getFollowerProfilePage(String token, int followerId){

        UserObject follower = null;
        try{
            int userId = getUserByToken(token).getId();
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT u.id ,u.username FROM users u INNER JOIN followers f ON f.follower_id = u.id WHERE f.follower_id = ? AND f.followed_id = ?"
            );
            preparedStatement.setInt(1, followerId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                follower = new UserObject();
                follower.setId(followerId);
                follower.setUsername(resultSet.getString("username"));
                return follower;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return follower;
    }

    public List<UserObject> getFollowers(String token){
        List<UserObject> followers = new ArrayList<>();
        try{
            int userId = getUserByToken(token).getId();
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT f.follower_id,u.username FROM users u INNER JOIN followers f ON f.follower_id = u.id WHERE f.followed_id = ?"
            );
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                UserObject follower = new UserObject();
                follower.setId(resultSet.getInt("follower_id"));
                follower.setUsername(resultSet.getString("username"));
                followers.add(follower);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return followers;
    }

    private boolean userNameAlreadyExists(String username) {
        boolean exists = true;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT token FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                exists = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    private UserObject getUserByToken(String token){
        UserObject userObject = null;
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT * FROM users WHERE token = ?"
            );
            preparedStatement.setString(1,token);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                userObject = new UserObject();
                userObject.setId(resultSet.getInt("id"));
                userObject.setUsername(resultSet.getString("username"));
                userObject.setUsername(resultSet.getString("token"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return userObject;
    }



}
