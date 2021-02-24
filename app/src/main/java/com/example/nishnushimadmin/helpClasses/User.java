package com.example.nishnushimadmin.helpClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String id;
    String name;
    String phoneNumber;
    String email;
    List<MyAddress> addresses = new ArrayList<>();
    List<Order> orderList = new ArrayList<>();
    List<String> restaurantWishList = new ArrayList<>();


    public User() {
    }

    public User(String id, String name, String phoneNumber, String email, List<MyAddress> addresses, List<Order> orderList, List<String> restaurantWishList) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.addresses = addresses;
        this.orderList = orderList;
        this.restaurantWishList = restaurantWishList;
    }


    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<MyAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<MyAddress> addresses) {
        this.addresses = addresses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<String> getRestaurantWishList() {
        return restaurantWishList;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setRestaurantWishList(List<String> restaurantWishList) {
        this.restaurantWishList = restaurantWishList;
    }



    public MyAddress getChosenAddress(){

        for (int i = 0; i < this.addresses.size(); i++) {
            if (addresses.get(i).isChosen()){
                return addresses.get(i);
            }
        }

        return this.addresses.get(0);
    }



    public String getChosenAddressString() {

        for (int i = 0; i < this.getAddresses().size(); i++) {

            if (this.getAddresses().get(i).isChosen()) {
                return "" + getAddresses().get(i).getCityName() + ", " + getAddresses().get(i).getStreetName() +
                        " " + getAddresses().get(i).getHouseNumber();
            }
        }

        return "אנא הכנס כתובת למשלוח";
    }
}
