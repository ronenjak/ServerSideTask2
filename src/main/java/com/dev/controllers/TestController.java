package com.dev.controllers;

import com.dev.Persist;
import com.dev.responses.ErrorCodes;
import com.dev.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
public class TestController {

    @Autowired
    private Persist persist;

    @PostConstruct
    private void init() {

    }


    @RequestMapping("/test")
    public Response validateToken(String token) { // this method will check if the token is valid
        return new Response();
    }




}
