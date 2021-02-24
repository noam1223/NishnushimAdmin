package com.example.nishnushimadmin.helpClasses;

import java.io.Serializable;

public class CreditCard implements Serializable {

    String number;
    String date;
    String cvv;
    String personIdNumber;

    public CreditCard() {
        this.number = "5555 5555 5555 5555";
        this.date = "12/21";
        this.cvv = "555";
        this.personIdNumber = "555555555";
    }


    public CreditCard(String number, String date, String cvv, String personIdNumber) {
        this.number = number;
        this.date = date;
        this.cvv = cvv;
        this.personIdNumber = personIdNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getPersonIdNumber() {
        return personIdNumber;
    }

    public void setPersonIdNumber(String personIdNumber) {
        this.personIdNumber = personIdNumber;
    }
}
