package com.example.shoesecommerceapp.fetchOrderHistoryRecyclerview;

public class Orderresponemodel {
    String price, trackingid, status, timestamp;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTrackingid() {
        return trackingid;
    }

    public void setTrackingid(String trackingid) {
        this.trackingid = trackingid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Orderresponemodel(String price, String trackingid, String status, String timestamp) {
        this.price = price;
        this.trackingid = trackingid;
        this.status = status;
        this.timestamp = timestamp;
    }
    public Orderresponemodel(){

    }
}
