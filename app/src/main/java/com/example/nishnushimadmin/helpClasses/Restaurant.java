package com.example.nishnushimadmin.helpClasses;

import android.net.Uri;

import java.io.Serializable;

public class Restaurant implements Serializable {

    String name;
    MyAddress myAddress;
    String phoneNumber;
    String openHour, closeHour;
    String dateOfAdd;
    String deliveryTime;
    Uri photoProfile;
    int amountOfDelivery;
    int minAmountToDeliver;
    Uri logoUri;
    Uri profileImageUri;


    public Restaurant() {
    }


    public Restaurant(String name, MyAddress myAddress, String phoneNumber, String openHour, String closeHour, String dateOfAdd, String deliveryTime, Uri photoProfile, int amountOfDelivery, int minAmountToDeliver, Uri logoUri, Uri profileImageUri) {
        this.name = name;
        this.myAddress = myAddress;
        this.phoneNumber = phoneNumber;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.dateOfAdd = dateOfAdd;
        this.deliveryTime = deliveryTime;
        this.photoProfile = photoProfile;
        this.amountOfDelivery = amountOfDelivery;
        this.minAmountToDeliver = minAmountToDeliver;
        this.logoUri = logoUri;
        this.profileImageUri = profileImageUri;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyAddress getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(MyAddress myAddress) {
        this.myAddress = myAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOpenHour() {
        return openHour;
    }

    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }

    public String getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(String closeHour) {
        this.closeHour = closeHour;
    }

    public String getDateOfAdd() {
        return dateOfAdd;
    }

    public void setDateOfAdd(String dateOfAdd) {
        this.dateOfAdd = dateOfAdd;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Uri getPhotoProfile() {
        return photoProfile;
    }

    public void setPhotoProfile(Uri photoProfile) {
        this.photoProfile = photoProfile;
    }

    public int getAmountOfDelivery() {
        return amountOfDelivery;
    }

    public void setAmountOfDelivery(int amountOfDelivery) {
        this.amountOfDelivery = amountOfDelivery;
    }

    public int getMinAmountToDeliver() {
        return minAmountToDeliver;
    }

    public void setMinAmountToDeliver(int minAmountToDeliver) {
        this.minAmountToDeliver = minAmountToDeliver;
    }

    public Uri getLogoUri() {
        return logoUri;
    }

    public void setLogoUri(Uri logoUri) {
        this.logoUri = logoUri;
    }

    public Uri getProfileImageUri() {
        return profileImageUri;
    }

    public void setProfileImageUri(Uri profileImageUri) {
        this.profileImageUri = profileImageUri;
    }
}
