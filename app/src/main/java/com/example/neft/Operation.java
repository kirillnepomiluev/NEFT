package com.example.neft;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Operation {
    private String name;
    private int amount;
    private boolean plus;
    private String id;
    private long time;

    public Operation(){}

    public Operation(String name,boolean plus,int x, String userid ){
        this.name=name;
        this.plus=plus;
        amount=x;
        time=new Date().getTime();
        id=time+userid;


    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("time", time);
        result.put("plus", plus);
        result.put("amount", amount);

        return result;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean getPlus() {
        return plus;
    }

    public void setPlus(boolean plus) {
        this.plus = plus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
