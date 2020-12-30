package com.example.nishnushimadmin.helpClasses;

import java.io.Serializable;
import java.util.List;

public class AreasForDeliveries implements Serializable {

    String areaName;
    int deliveryCost;
    int minToOrder;
    int timeOfDelivery;


    public AreasForDeliveries() {
    }


    public AreasForDeliveries(String areaName, int deliveryCost, int minToOrder, int timeOfDelivery) {
        this.areaName = areaName;
        this.deliveryCost = deliveryCost;
        this.minToOrder = minToOrder;
        this.timeOfDelivery = timeOfDelivery;
    }


    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public int getMinToOrder() {
        return minToOrder;
    }

    public void setMinToOrder(int minToOrder) {
        this.minToOrder = minToOrder;
    }

    public int getTimeOfDelivery() {
        return timeOfDelivery;
    }

    public void setTimeOfDelivery(int timeOfDelivery) {
        this.timeOfDelivery = timeOfDelivery;
    }
}
