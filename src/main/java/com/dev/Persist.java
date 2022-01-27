package com.dev;

import com.dev.objects.*;

import com.dev.responses.Response;
import com.dev.responses.ResponseData;
import com.dev.utils.UsersObject;
import org.aspectj.weaver.ast.Or;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Persist {
    final int empty = 0;
    final int firstObject = 0;
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

    public List<Object> getOrganizations(String token) {
        List<Organization> userOrganizations = null;
        List<Organization> allOrganizations = null;
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
                    if (userOrganizations.contains(organization)) {
                        totalOrganization.setBelongsToUser(true);
                    }
                    totalOrganizations.add(totalOrganization);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalOrganizations;
    }


    public User getUserByToken(String token) {
        User user = null;
        try {
            Session session = sessionFactory.openSession();
            user = (User) session.createQuery("FROM User WHERE token = :token")
                    .setParameter("token", token)
                    .uniqueResult();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public Organization getOrganizationById(int organizationId) {
        Organization organization = null;
        try {
            Session session = sessionFactory.openSession();
            organization = (Organization) session.createQuery("FROM Organization WHERE id = :id")
                    .setParameter("id", organizationId)
                    .uniqueResult();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return organization;
    }

    public Integer getUserIdByToken(String token) {
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


    public boolean changeRelationshipUO(String token, int organizationId, boolean friendShip) {
        boolean success = false;
        User user = getUserByToken(token);
        Organization organization = getOrganizationById(organizationId);
        if (user != null && organization != null) {
            try {
                if (friendShip) {
                    RelationshipUO relationshipUO = new RelationshipUO(user, organization);
                    Session session = sessionFactory.openSession();
                    Transaction transaction = session.beginTransaction();
                    session.saveOrUpdate(relationshipUO);
                    transaction.commit();
                    session.close();
                } else {
                    Session session = sessionFactory.openSession();
                    Transaction transaction = session.beginTransaction();
                    session.createQuery("DELETE FROM RelationshipUO r WHERE r.organization.id = :id AND r.user.id = :userId")
                            .setParameter("id", organizationId)
                            .setParameter("userId", user.getId())
                            .executeUpdate();
                    transaction.commit();
                    session.close();
                }
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return success;
    }


    public List<Object> getStoreByStoreId(int storeId) {
        List<Object> listToReturn = null;

        try {
            Session session = sessionFactory.openSession();
            Store store = (Store) session.createQuery("FROM Store s WHERE s.id = :id")
                    .setParameter("id", storeId)
                    .uniqueResult();
            session.close();
            if (store != null) {
                listToReturn = new ArrayList<>();
                listToReturn.add(store);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listToReturn;
    }

    public List<Object> getSalesByStoreId(int storeId) {
        List<Object> sales = null;
        try {
            Session session = sessionFactory.openSession();
            sales = (List<Object>) session.createQuery("FROM Sale s WHERE s.store.id = :id")
                    .setParameter("id", storeId)
                    .list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sales;
    }

    public List<Object> getAllStores() {
        List<Object> stores = null;
        try {
            Session session = sessionFactory.openSession();
            stores = (List<Object>) session.createQuery(
                            " FROM Store ")
                    .list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stores;
    }

    public boolean validateToken(String token) {
        User user = null;
        try {
            Session session = sessionFactory.openSession();
            user = (User) session.createQuery("FROM User WHERE token = :token")
                    .setParameter("token", token)
                    .uniqueResult();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (user == null);
    }

    public List<Object> getTokenByUsernameAndPassword(String username, String password) {
        List<Object> tokenList = null;
        try{
            Session session = sessionFactory.openSession();
            String token = (String) session.createQuery("SELECT token FROM User WHERE username = :username AND password = :password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            session.close();
            if(token != null){
                tokenList = new ArrayList<>();
                tokenList.add(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokenList;
    }
}