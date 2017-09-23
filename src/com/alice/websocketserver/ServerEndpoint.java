package com.alice.websocketserver;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@javax.websocket.server.ServerEndpoint(value = "/alice",configurator = WebSocketServerConfig.class)
public class ServerEndpoint {

    private Map<String,String> files = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        session.setMaxBinaryMessageBufferSize(100000000);
        session.setMaxTextMessageBufferSize(100000000);
        System.out.println(session.getId() + "Buffer size " + session.getMaxBinaryMessageBufferSize() +  " and " + session.getMaxTextMessageBufferSize());
    }
    @OnMessage
    public void onMessage(Session session,String message) {

        System.out.println("Message from session with id: " + session.getId() + ": " + message);

        if (parseJson(message)) {
            try {
                session.getBasicRemote().sendText("Files received!");
            } catch (IOException exception){
                System.out.println(exception.getMessage());
            }
        } else {
            try {
                session.getBasicRemote().sendText("Hello from the server");
            } catch (IOException exception){
                System.out.println(exception.getMessage());
            }
        }

    }
    @OnMessage
    public void onBinaryMessage(Session session,byte[] byte_message) {

        System.out.println("Binary message from session with id: " + session.getId());

        File file = new File( files.get("name"));

        try {
            if (file.createNewFile()) {
                System.out.println(file.getAbsolutePath());
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(byte_message);
            } else {
                System.out.println("Error creating the file!");
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        files.clear();

    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Session " + session.getId() + " closed.");
    }
    @OnError
    public void onError(Session session,Throwable error) {
        System.out.println("Session " + session.getId() + " error: " + error);
    }


    private boolean parseJson(String message) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(message);
            files.put("name",jsonObject.get("name").toString());
            files.put("type",jsonObject.get("type").toString());
        } catch (ParseException exception) {
            System.out.println("Message with no json!");
            return false;
        }
        return true;
    }
}
