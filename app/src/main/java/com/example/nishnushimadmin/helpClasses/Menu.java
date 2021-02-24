package com.example.nishnushimadmin.helpClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable {

    List<Classification> classifications = new ArrayList<>();

    public Menu() {
    }

    public Menu(List<Classification> classifications) {
        this.classifications = classifications;
    }

    public List<Classification> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<Classification> classifications) {
        this.classifications = classifications;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "classifications=" + classifications.toString() +
                '}';
    }
}
