package com.example.nishnushimadmin.helpClasses;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {

    String id;
    String name;
    String restaurantUserName;
    String restaurantUserPassword;
    MyAddress myAddress;
    List<AreasForDeliveries> AreasForDeliveries;
    String phoneNumber;
    List<String> openHour, closeHour;
    String dateOfAdd;


    Uri logoUri;
    Uri profileImageUri;
    List<RecommendationRestaurant> recommendationRestaurants = new ArrayList<>();
    boolean kosher, discount;
    List<Integer> classificationList = new ArrayList<>();
    Menu menu;

    //ADDED
    float recommendationAvg = 0;
    double distanceFromCurrentUser;

    int creditAmount = 0;

    public Restaurant() {
    }

    public Restaurant(String id, String name, String restaurantUserName, String restaurantUserPassword, MyAddress myAddress, List<AreasForDeliveries> areasForDeliveries, String phoneNumber, List<String> openHour, List<String> closeHour, String dateOfAdd, Uri logoUri, Uri profileImageUri, List<RecommendationRestaurant> recommendationRestaurants, boolean kosher, boolean discount, List<Integer> classificationList, Menu menu, float recommendationAvg, int creditAmount) {
        this.id = id;
        this.name = name;
        this.restaurantUserName = restaurantUserName;
        this.restaurantUserPassword = restaurantUserPassword;
        this.myAddress = myAddress;
        AreasForDeliveries = areasForDeliveries;
        this.phoneNumber = phoneNumber;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.dateOfAdd = dateOfAdd;
        this.logoUri = logoUri;
        this.profileImageUri = profileImageUri;
        this.recommendationRestaurants = recommendationRestaurants;
        this.kosher = kosher;
        this.discount = discount;
        this.classificationList = classificationList;
        this.menu = menu;
        this.recommendationAvg = recommendationAvg;
        this.creditAmount = creditAmount;
    }

    public Restaurant(String name, MyAddress myAddress, List<AreasForDeliveries> AreasForDeliveries, String phoneNumber, List<String> openHour, List<String> closeHour, String dateOfAdd, Uri logoUri, Uri profileImageUri, boolean kosher, boolean discount, List<Integer> classificationList, Menu menu, String restaurantUserName, String restaurantUserPassword) {
        this.name = name;
        this.myAddress = myAddress;
        this.AreasForDeliveries = AreasForDeliveries;
        this.phoneNumber = phoneNumber;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.dateOfAdd = dateOfAdd;
        this.logoUri = logoUri;
        this.profileImageUri = profileImageUri;
        this.kosher = kosher;
        this.discount = discount;
        this.classificationList = classificationList;
        this.menu = menu;
        this.restaurantUserName = restaurantUserName;
        this.restaurantUserPassword = restaurantUserPassword;
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

    public List<AreasForDeliveries> getAreasForDeliveries() {
        return AreasForDeliveries;
    }

    public void setAreasForDeliveries(List<AreasForDeliveries> areasForDeliveries) {
        this.AreasForDeliveries = areasForDeliveries;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getOpenHour() {
        return openHour;
    }

    public void setOpenHour(List<String> openHour) {
        this.openHour = openHour;
    }

    public List<String> getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(List<String> closeHour) {
        this.closeHour = closeHour;
    }

    public String getDateOfAdd() {
        return dateOfAdd;
    }

    public void setDateOfAdd(String dateOfAdd) {
        this.dateOfAdd = dateOfAdd;
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

    public List<RecommendationRestaurant> getRecommendationRestaurants() {
        return recommendationRestaurants;
    }

    public void setRecommendationRestaurants(List<RecommendationRestaurant> recommendationRestaurants) {
        this.recommendationRestaurants = recommendationRestaurants;
    }

    public boolean isKosher() {
        return kosher;
    }

    public void setKosher(boolean kosher) {
        this.kosher = kosher;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public List<Integer> getClassificationList() {
        return classificationList;
    }

    public void setClassificationList(List<Integer> classificationList) {
        this.classificationList = classificationList;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }



    public float getRecommendationAvg() {

        if (recommendationAvg == -1) {

            float avg = 0;

            for (int i = 0; i < recommendationRestaurants.size(); i++) {

                avg += recommendationRestaurants.get(i).getCreditStar();

            }

            recommendationAvg = (float) (avg/recommendationRestaurants.size());
        }

        return recommendationAvg;
    }


    public double getDistanceFromCurrentUser() {
        return distanceFromCurrentUser;
    }

    public void setDistanceFromCurrentUser(double distanceFromCurrentUser) {
        this.distanceFromCurrentUser = distanceFromCurrentUser;
    }

    public int getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(int creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getRestaurantUserName() {
        return restaurantUserName;
    }

    public void setRestaurantUserName(String restaurantUserName) {
        this.restaurantUserName = restaurantUserName;
    }

    public String getRestaurantUserPassword() {
        return restaurantUserPassword;
    }

    public void setRestaurantUserPassword(String restaurantUserPassword) {
        this.restaurantUserPassword = restaurantUserPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
