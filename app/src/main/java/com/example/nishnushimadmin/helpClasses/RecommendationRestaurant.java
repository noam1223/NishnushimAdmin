package com.example.nishnushimadmin.helpClasses;

import java.io.Serializable;

public class RecommendationRestaurant implements Serializable {

    String name;
    String date;
    String creditLetter;
    int creditStar;


    public RecommendationRestaurant() {
    }


    public RecommendationRestaurant(String name, String date, String creditLetter, int creditStar) {
        this.name = name;
        this.date = date;
        this.creditLetter = creditLetter;
        this.creditStar = creditStar;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreditLetter() {
        return creditLetter;
    }

    public void setCreditLetter(String creditLetter) {
        this.creditLetter = creditLetter;
    }

    public int getCreditStar() {
        return creditStar;
    }

    public void setCreditStar(int creditStar) {
        this.creditStar = creditStar;
    }
}
