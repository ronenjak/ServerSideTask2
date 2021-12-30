package com.dev.controllers;

import com.dev.Persist;
import com.dev.objects.PostObject;
import com.dev.objects.UserObject;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;


@RestController
public class TestController {

    @Autowired
    private Persist persist;

    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public Object test () {
        return "Adi";
    }

    @RequestMapping(value = "/get-random-value", method = {RequestMethod.GET, RequestMethod.POST})
    public int random () {
        Random random = new Random();
        return random.nextInt();
    }

    @RequestMapping(value = "/get-posts", method = {RequestMethod.GET, RequestMethod.POST})
    public List<PostObject> getPosts (String token) {
        List<PostObject> posts = persist.getUserPostsByToken(token);
        return posts;
    }

    @RequestMapping("sign-in")
    public String signIn (String username, String password) {
        String token = persist.getTokenByUsernameAndPassword(username, password);
        return token;
    }

    @RequestMapping("create-account")
    public boolean createAccount (String username, String password) {
        String token = createHash(username,password);
        boolean success = persist.createAccount(username,password, token);
        return success;
    }

    public String createHash (String username, String password) {
        String myHash = null;
        try {
            String hash = "35454B055CC325EA1AF2126E27707052";

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((username + password).getBytes());
            byte[] digest = md.digest();
            myHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return myHash;
    }

    @RequestMapping("add-post")
    public boolean addPost (String token, String content) {
        boolean success = persist.addPost(token,content,getCurrentDate());
        return success;
    }

    @RequestMapping("get-followers")
    public List<UserObject> getFollowers (String token) {
        List<UserObject> followers = persist.getFollowers(token);
        return followers;
    }

    @RequestMapping("get-userprofile")
    public UserObject getFollowerProfilePage (String token, int followerId) {
        UserObject follower = persist.getFollowerProfilePage(token, followerId);
        return follower;
    }


    private String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }

    @RequestMapping(value = "/add-post", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public boolean addPost (@RequestParam(value = "file", required = false) MultipartFile multipartFile, String token, String content) {
        if (multipartFile != null) {
            try {
                File fileOnDisk = new File("C:\\Users\\Ronen\\pictures\\2.jpg");
                multipartFile.transferTo(fileOnDisk);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return persist.addPost(token, content, getCurrentDate());
    }


    @RequestMapping(value = "/get-post-image", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public void postImage (HttpServletResponse response, int postId) {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        String path = "C:\\Users\\Ronen\\pictures\\2.jpg";
        if (new File(path).exists()) {
            serveImage(response, path);
        }

    }

    public static void serveImage (HttpServletResponse response, String path) {
        try {
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            StreamUtils.copy(fileInputStream, response.getOutputStream());
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
