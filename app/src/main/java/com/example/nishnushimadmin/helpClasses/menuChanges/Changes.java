package com.example.nishnushimadmin.helpClasses.menuChanges;

import com.google.firebase.database.GenericTypeIndicator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Changes implements Serializable {


    public enum ChangesTypesEnum {
        MULTIPLE,
        ONE_CHOICE,
        VOLUME,
        CLASSIFICATION_CHOICE,
        DISH_CHOICE,
        PIZZA,
        CHOICE_ALL
    }


    int id;
    String changeName;
    public int freeSelection = 0;
    ChangesTypesEnum changesTypesEnum;
    List<Object> changesByTypesList = new ArrayList<>();


    public Changes() {
    }


    public Changes(int id, String changeName, int freeSelection, ChangesTypesEnum changesTypesEnum, List<Object> changesByTypesList) {
        this.id = id;
        this.changeName = changeName;
        this.freeSelection = freeSelection;
        this.changesTypesEnum = changesTypesEnum;
        this.changesByTypesList = changesByTypesList;
    }


    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }

    public ChangesTypesEnum getChangesTypesEnum() {
        return changesTypesEnum;
    }

    public void setChangesTypesEnum(ChangesTypesEnum changesTypesEnum) {
        this.changesTypesEnum = changesTypesEnum;
    }


    public List<Object> getChangesByTypesList() {
        return changesByTypesList;
    }

    public void setChangesByTypesList(List<Object> changesByTypesList) {
        this.changesByTypesList = changesByTypesList;
    }

    public int getFreeSelection() {
        return freeSelection;
    }

    public void setFreeSelection(int freeSelection) {
        this.freeSelection = freeSelection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
