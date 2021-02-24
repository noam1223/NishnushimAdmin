package com.example.nishnushimadmin.helpClasses.menuChanges;

import java.io.Serializable;


public class PizzaChange implements Serializable {

    int id;
    String name;
    int cost = 0;


    public PizzaChange() {
    }

    public PizzaChange(int id) {
        this.id = id;
    }

    public PizzaChange(int id, String name, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
