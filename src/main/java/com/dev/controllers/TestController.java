package com.dev.controllers;

import com.dev.Persist;
import com.dev.responses.Response;
import com.dev.responses.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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



}
