package com.dev;

import com.dev.objects.*;

import com.dev.utils.UsersObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.sql.*;

@Component
public class Persist {

    private final SessionFactory sessionFactory;

    @Autowired
    public Persist(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public List<Object> getSalesByUserToken(String token) {
        List<Integer> userOrganizationsIds = null;
        List<Sale> allSales = null;
        List<Object> userSales = new ArrayList<>(); // return object list so we can add this to the responseData

        try {
            Session session = sessionFactory.openSession(); // if somebody can do this in 1 query, the foreach lines won't be necessary
            userOrganizationsIds = session.createQuery("SELECT ruo.organization.id FROM RelationshipUO ruo WHERE ruo.user.token = :token")
                    .setParameter("token", token)
                    .list();
            allSales = session.createQuery("FROM Sale").list();
            session.close();

            for (Sale sale : allSales) {
                UsersObject usersSale = new UsersObject(sale);
                if (userOrganizationsIds.contains(sale.getId()) || sale.isForAllUsers()) {
                    usersSale.setBelongsToUser(true);
                }
                userSales.add(usersSale);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return userSales;
    }
    // Methods related to login and sign in

    public int checkBlockCounter(String username) {
        int blockedCounter = 5;
        try {
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("SELECT blockCounter FROM User WHERE username = :usernameParam")
                    .setParameter("usernameParam", username);
            blockedCounter = (int) query.uniqueResult();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blockedCounter;


        public boolean usernameExist (String username){
        }

        public String getTokenByUsernameAndPassword (String username, String password){
        }

        public int checkAndUpdateUserBlock (String username,boolean b){
        }
    }
