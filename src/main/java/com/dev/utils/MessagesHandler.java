package com.dev.utils;

import com.dev.Persist;
import com.dev.objects.RelationshipSaleO;
import com.dev.objects.RelationshipUO;
import com.dev.objects.Sale;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MessagesHandler extends TextWebSocketHandler {

    private final static List<WebSocketSession> sessionList = new CopyOnWriteArrayList<>();
    private final static Map<String, WebSocketSession> sessionMap = new HashMap<>();

    private static int totalSessions;

    @Autowired
    private Persist persist;

    @PostConstruct
    public void init () {
        new Thread(() -> {
            while (true) {
                try {
                    //sendToEveryone();
                    Thread.sleep(5000);
                    List<RelationshipSaleO> rsoList = persist.getAllRelationshipsSaleO();
                    List<RelationshipUO> ruoList = persist.getAllRelationshipsUO();
                    System.out.println(LocalDate.now());
                    for(RelationshipSaleO rso : rsoList){
                        Sale sale = rso.getSale();
                        if(sale.getNotifiedStatus() == Constants.SALE_NOT_NOTIFIED && Utils.todayDateAfter(sale.getBeginDate())){
                            notifyUsers(ruoList,rso, "There is a new sale!!");
                        }
                        else if(sale.getNotifiedStatus() == Constants.SALE_STARTED_NOTIFIED && Utils.todayDateAfter(sale.getExpirationDate())){
                            notifyUsers(ruoList,rso , "Sale is expired!!");
                        }
                    }



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void notifyUsers(List<RelationshipUO> ruoList, RelationshipSaleO rso, String message)  {
        for (RelationshipUO ruo : ruoList) {
            if (ruo.getOrganization().getId() == rso.getOrganization().getId()) {
                WebSocketSession session = sessionMap.get(ruo.getUser().getToken());
                if (session != null) {
                    try {
                        JSONObject notification = new JSONObject();
                        notification.put("message", message + " " + rso.getSale().toString());
                        session.sendMessage(new TextMessage(notification.toString()));
                        System.out.println("sent message!");
                        persist.updateNotificationStatus(rso.getSale().getId());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        Map<String, String> map = Utils.splitQuery(session.getUri().getQuery());
        sessionMap.put(map.get("token"), session);
        sessionList.add(session);
        totalSessions = sessionList.size();
        System.out.println("sessionList size: " +totalSessions);
        System.out.println("afterConnectionEstablished: token " + map.get("token"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        System.out.println("handleTextMessage");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessionList.remove(session);
        System.out.println("afterConnectionClosed");
    }

    public void sendToEveryone () {
        for (WebSocketSession session : sessionList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("test", String.valueOf(System.currentTimeMillis()));
            try {
                session.sendMessage(new TextMessage(jsonObject.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}