package com.example.nishnushimadmin.helpClasses;

import com.example.nishnushimadmin.helpClasses.menuChanges.Changes;
import com.example.nishnushimadmin.helpClasses.menuChanges.RegularChange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Classification implements Serializable {

    String classificationName;
    List<Dish> dishList = new ArrayList<>();
    List<Changes> changesList;


    public Classification() {
    }

    public Classification(String classificationName, List<Dish> dishList, List<Changes> changesList) {
        this.classificationName = classificationName;
        this.dishList = dishList;
        this.changesList = changesList;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }

    public List<Changes> getChangesList() {
        return changesList;
    }

    public void setChangesList(List<Changes> changesList) {
        this.changesList = changesList;
    }

    @Override
    public String toString() {
        return "Classification{" +
                "classificationName='" + classificationName + '\'' +
                ", dishList=" + dishList +
                ", changesList=" + changesList +
                '}';
    }
}

