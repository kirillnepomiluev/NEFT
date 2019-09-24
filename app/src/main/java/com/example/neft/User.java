package com.example.neft;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String login;
    private String sponsorlogin;
    private String phone;
    private String photoUrl;
    private String useruid;
    private boolean male;
    private int money;
    private boolean status;
    private long statusend;
    private String sponsorid;
    private String sponsor2id;
    private String sponsor3id;
    private String sponsor4id;
    private String sponsor5id;

    public User () {

    }

    public User (String useruid, String phone, String login) {
        this.useruid = useruid;
        this.phone = phone;
        this.male=true;
        this.login=login;
        this.money=0;



    }
    public User (Invite invite) {
        this.useruid = invite.getUser2uid();
        this.login=invite.getUsername();

    }

    public User (Chat chat) {
        this.useruid = chat.getUser2uid();
        this.login=chat.getUsername();
        this.photoUrl=chat.getUserPhotourl();

    }

    public User (String login, String sponsorlogin, String password, String phone, String useruid) {
        this.login=login;
        this.sponsorlogin=sponsorlogin;

        this.phone=phone;
        this.useruid=useruid;
        this.male=true;


    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("useruid", useruid);
        result.put("login", login);
        result.put("sponsorlogin", sponsorlogin);
        result.put("sponsorid", sponsorid);
        result.put("phone", phone);
        result.put("photoUrl", photoUrl);
        result.put("money", money);
        result.put("status", status);
        result.put("male", male);
        result.put("sponsor2id", sponsor2id);
        result.put("sponsor3id", sponsor3id);
        result.put("sponsor4id", sponsor4id);
        result.put("sponsor5id", sponsor5id);
        result.put("statusend", statusend);
        return result;
    }




    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSponsorlogin() {
        return sponsorlogin;
    }

    public void setSponsorlogin(String sponsorlogin) {
        this.sponsorlogin = sponsorlogin;
    }





    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUseruid() {
        return useruid;
    }

    public void setUseruid(String useruid) {this.useruid = useruid;}

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    public int getMoney() {
        return money;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSponsorid() {
        return sponsorid;
    }

    public void setSponsorid(String sponsorid) {
        this.sponsorid = sponsorid;
    }

    public String getSponsor2id() {
        return sponsor2id;
    }

    public void setSponsor2id(String sponsor2id) {
        this.sponsor2id = sponsor2id;
    }

    public String getSponsor3id() {
        return sponsor3id;
    }

    public void setSponsor3id(String sponsor3id) {
        this.sponsor3id = sponsor3id;
    }

    public String getSponsor4id() {
        return sponsor4id;
    }

    public void setSponsor4id(String sponsor4id) {
        this.sponsor4id = sponsor4id;
    }

    public String getSponsor5id() {
        return sponsor5id;
    }

    public void setSponsor5id(String sponsor5id) {
        this.sponsor5id = sponsor5id;
    }

    public long getStatusend() {
        return statusend;
    }

    public void setStatusend(long statusend) {
        this.statusend = statusend;
    }
}
