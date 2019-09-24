package com.example.neft;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Message {
    private String aftorid;
    private String text;
    private long time;
    private String id;

    public  Message () {}

    public Message (String aftorid, String text) {
        this.aftorid = aftorid;
        this.text = text;
        this.time = new Date().getTime();
        this.id = this.time+aftorid;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("aftorid", aftorid);
        result.put("text", text);
        result.put("time", time);
        result.put("id", id);

        return result;
    }



    public String getAftorid() {
        return aftorid;
    }

    public void setAftorid(String aftorid) {
        this.aftorid = aftorid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
