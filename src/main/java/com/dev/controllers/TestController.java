package com.dev.controllers;
import com.dev.objects.Organization;
import com.dev.Persist;
import com.dev.objects.RelationshipUO;
import com.dev.objects.Store;
import com.dev.responses.ErrorCodes;
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

    // Login and signup related
    @RequestMapping("/login")
    public ResponseData login(String username , String password){
        List<Object> token = persist.getTokenByUsernameAndPassword(username, password);
        return new ResponseData(token);
    }

    @RequestMapping("/validateToken")
    public Response validateToken(String token) { // this method will check if the token is valid
        boolean validToken = persist.validateToken(token);
        return new Response(validToken);
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

    @RequestMapping("get-organizations")
    public ResponseData getOrganizations(String token){
        List<Object> organizations =  persist.getOrganizations(token);
        return new ResponseData(organizations);
    }

    @RequestMapping("change-relationshipUO")
    public Response removeRelationshipUO(String token,int organizationId, boolean friendShip){
        boolean success = persist.changeRelationshipUO(token, organizationId,friendShip);
        return new Response(success);
    }


    @RequestMapping("get-stores")
    public ResponseData getAllStores(){
        List<Object> stores =  persist.getAllStores();
        return new ResponseData(stores);
    }

    @RequestMapping("getStoreByStoreId")
    public ResponseData getStoreByStoreId(int storeId){
        List<Object> listToReturn = persist.getStoreByStoreId(storeId);
        return new ResponseData(listToReturn);
    }

    @RequestMapping("getSalesByStoreId")
    public ResponseData getSalesByStoreId(int storeId){
        List<Object> listToReturn = persist.getSalesByStoreId(storeId);
        return new ResponseData(listToReturn);
    }




}