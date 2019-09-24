package com.example.neft;

import java.util.HashMap;
import java.util.Map;

public class Invite {


        private String user1uid;
        private String user2uid;
        private boolean online;
        private String username;
        private String username2;



        public Invite () {}

        public Invite (User user2, User user1 ) {
            this.user1uid= user1.getUseruid();
            this.user2uid = user2.getUseruid();
            this.username = user1.getLogin();
            this.username2 = user2.getLogin();


        }
        public Invite (Invite invite) {
            this.user1uid=invite.getUser2uid();
            this.username=invite.getUsername2();
            this.user2uid=invite.getUser1uid();
            this.username2=invite.getUsername();
        }


        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("user1uid", user1uid);
            result.put("user2uid", user2uid);
            result.put("username", username);
            result.put("username2", username2);
            result.put("online", online);


            return result;
        }

        public String getUser1uid () {        return user1uid;    }

        public void setUser1uid(String user1uid) {
            this.user1uid = user1uid;
        }

        public String getUser2uid() {
            return user2uid;
        }



        public void setUser2uid(String user2uid) {
            this.user2uid = user2uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }
}

