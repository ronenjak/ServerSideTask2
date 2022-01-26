package com.dev.controllers;

import com.dev.Persist;
import com.dev.responses.ErrorCodes;
import com.dev.responses.Response;
import com.dev.responses.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
public class TestController {

    @Autowired
    private Persist persist;

    @PostConstruct
    private void init() {

    }


    @RequestMapping(value = "/test" , method = {RequestMethod.GET, RequestMethod.POST})
    public Response test(String token) {
        List<Object> sales1 = persist.getSalesByUserToken(token);
        List<Object> sales2 = persist.getSalesByUserToken(token);
        List<Object> salesTotal = new ArrayList<>();
        salesTotal.add(sales1);
        salesTotal.add(sales2);
        return new ResponseData(salesTotal);
    }

    @RequestMapping(value = "/get-sales" , method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData getSalesByToken(String token) {
        List<Object> sales = persist.getSalesByUserToken(token);
        return new ResponseData(sales);
    }

    @RequestMapping(value = "/login" , method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData login(String username, String password) {
        //also count number of blocked accounts
        String token;
        int blockedCounter;
        boolean isFirstTime;
        int errorCode;
        if (persist.usernameExist(username)) { // if username exists in the table

            token = persist.getTokenByUsernameAndPassword(username, password); // post username and password and gets the token from DB

            if (token == null) {// if the query returned null token
                //update the block counter in DB and return it to counter here.
                blockedCounter = persist.checkAndUpdateUserBlock(username, false);
                //encapsulated assignment for error code depending if the user got blocked.
                errorCode = (blockedCounter >= 5) ? ErrorCodes.BLOCKED_ACCOUNT : ErrorCodes.INCORRECT_PASSWORD;
                //Assign to the list data
                return new ResponseData(false, errorCode, Collections.singletonList(blockedCounter + " = number of wrong tries"));
            } else {
                // If it's the correct credentials, and we got back good token!!! ,
                blockedCounter = persist.checkAndUpdateUserBlock(username, true);

                //If user is not blocked return token
                if (blockedCounter < 5)
                    return new ResponseData(Collections.singletonList(token));
                else
                    return new ResponseData(true, ErrorCodes.BLOCKED_ACCOUNT, Collections.singletonList(username + "is Blocked "));
            }
        }
        //If the username is wrong
        return new ResponseData(false, ErrorCodes.INCORRECT_USERNAME, Collections.singletonList(username + "This username is wrong"));
    }

    @RequestMapping(value = "/isFirstTime" , method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData
}
