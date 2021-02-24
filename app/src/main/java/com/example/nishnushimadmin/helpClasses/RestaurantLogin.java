package com.example.nishnushimadmin.helpClasses;

import java.io.Serializable;

public class RestaurantLogin implements Serializable {

    String userName;
    String password;
    String idKey;

    public RestaurantLogin() {
    }

    public RestaurantLogin(String userName, String password, String idKey) {
        this.userName = userName;
        this.password = password;
        this.idKey = idKey;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }
}
