package com.example.neft;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Chat {
    private String user1uid;
    private String user2uid;
    private String lastmessage;
    private boolean online;
    private String username;
    private long time;
    private String userPhotourl;


    public Chat () {}

    public Chat (Invite invite) {
        this.username = invite.getUsername();
        this.user1uid= invite.getUser1uid();
        this.user2uid= invite.getUser2uid();

    }

    public Chat (User user1, User user2) {
        this.user1uid= user1.getUseruid();
        this.user2uid = user2.getUseruid();
        this.username = user2.getLogin();
        this.userPhotourl = user2.getPhotoUrl();
    }

    public Chat (User user1, User user2, Message lastmessage ) {
        this.user1uid= user1.getUseruid();
        this.user2uid = user2.getUseruid();
        this.username = user2.getLogin();
        this.lastmessage = lastmessage.getText();
        this.time = lastmessage.getTime();
        this.userPhotourl = user2.getPhotoUrl();
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("user1uid", user1uid);
        result.put("user2uid", user2uid);
        result.put("username", username);
        result.put("online", online);
        result.put("lastmessage", lastmessage);
        result.put("userPhotourl", userPhotourl);

        return result;
    }

    public String getUser1uid () {        return user1uid;    }

    public void setUser1uid(String user1uid) {
        this.user1uid = user1uid;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public String getUser2uid() {
        return user2uid;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUserPhotourl() {
        return userPhotourl;
    }

    public void setUserPhotourl(String userPhotourl) {
        this.userPhotourl = userPhotourl;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public void setUser2uid(String user2uid) {
        this.user2uid = user2uid;
    }

    public long getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
