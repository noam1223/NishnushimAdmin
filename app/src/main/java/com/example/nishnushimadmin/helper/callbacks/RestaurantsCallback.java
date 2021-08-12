package com.example.nishnushimadmin.helper.callbacks;

import com.example.nishnushimadmin.model.Restaurant;

import java.util.List;

public interface RestaurantsCallback {
    void onRestaurantsListCallback(List<Restaurant> restaurants, String error);
}
