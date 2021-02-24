package com.example.nishnushimadmin.helpClasses.menuChanges;

import java.io.Serializable;

public class RegularChange implements Serializable {

    String change;
    int price;

    public RegularChange() {
    }

    public RegularChange(String change, int price) {
        this.change = change;
        this.price = price;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

