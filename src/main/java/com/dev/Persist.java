package com.dev;

import com.dev.objects.*;

import com.dev.responses.Response;
import com.dev.responses.ResponseData;
import com.dev.utils.UsersObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Persist {

    private final SessionFactory sessionFactory;

    @Autowired
    public Persist (SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public List<Object> getSalesByUserToken(String token){
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
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return userSales;
    }

    public List<Object> getOrganizations(String token){
        List<Organization> userOrganizations =null;
        List<Organization> allOrganizations =null;
        List<Object> totalOrganizations = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            userOrganizations = (List<Organization>) session.createQuery(
                    "SELECT r.organization  FROM RelationshipUO r " +
                            "WHERE r.user.token = :token ")
                    .setParameter("token", token)
                    .list();
            allOrganizations = (List<Organization>) session.createQuery(
                    " FROM Organization")
                    .list();
            session.close();

            if (userOrganizations != null) {
                for (Organization organization : allOrganizations) {
                    UsersObject totalOrganization = new UsersObject(organization);
                    if(userOrganizations.contains(organization)){
                        totalOrganization.setBelongsToUser(true);
                    }
                    totalOrganizations.add(totalOrganization);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return totalOrganizations;
    }

    public Integer getUserIdByToken (String token) {
        Integer id = null;
        Session session = sessionFactory.openSession();
        User user = (User) session.createQuery("FROM User WHERE token = :token")
                .setParameter("token", token)
                .uniqueResult();
        session.close();
        if (user != null) {
            id = user.getId();
        }
        return id;
    }

    public boolean removeRelationshipUO (String token, int organizationId) {
        boolean success = false;
        Integer userId = getUserIdByToken(token);
        if (userId != null) {
            try {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                session.createQuery("DELETE FROM RelationshipUO r WHERE r.id = :id AND user.id = :userId")
                        .setParameter("id", organizationId)
                        .setParameter("userId", userId)
                        .executeUpdate();
                transaction.commit();
                session.close();
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return success;

    }


    public boolean addRelationshipUOByUserId(String token,int organizationId) {
        boolean success = false;
        Integer userId = getUserIdByToken(token);
        if (userId != null) {
            RelationshipUO relationshipUO = new RelationshipUO();
            User user = new User();
            Organization organization = new Organization();
            user.setId(userId);
            organization.setId(organizationId);
            relationshipUO.setUser(user);
            relationshipUO.setOrganization(organization);
            try {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                session.saveOrUpdate(relationshipUO);
                transaction.commit();
                session.close();
                if (relationshipUO.getId() > 0) {
                    success = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return success;

    }

    public List<Object> getStores(){
        List<Object> stores = new ArrayList<>();
        Session session = sessionFactory.openSession();
        stores = (List<Object>) session.createQuery(
                " FROM Store ")
                .list();
        session.close();
        return stores;
    }

}