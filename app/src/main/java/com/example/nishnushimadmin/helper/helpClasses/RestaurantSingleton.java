package com.example.nishnushimadmin.helper.helpClasses;

import com.example.nishnushimadmin.model.Restaurant;

public class RestaurantSingleton {

    private static final RestaurantSingleton instance = new RestaurantSingleton();

    public static RestaurantSingleton getInstance(){
        return instance;
    }

    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}
