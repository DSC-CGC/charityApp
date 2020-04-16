package com.example.dream;

public class NotificationResult {
    String Date;
    String NameN;
    String PhoneN;
    String Quantity;
    String Thing;
    Double Latitude;
    Double Longitude;
    String Time;

    public NotificationResult(String date, String nameN, String phoneN, String quantity, String thing, Double latitude, Double longitude, String time) {
        Date = date;
        NameN = nameN;
        PhoneN = phoneN;
        Quantity = quantity;
        Thing = thing;
        Latitude = latitude;
        Longitude = longitude;
        Time = time;
    }

    public NotificationResult() {

    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNameN() {
        return NameN;
    }

    public void setNameN(String nameN) {
        NameN = nameN;
    }

    public String getPhoneN() {
        return PhoneN;
    }

    public void setPhoneN(String phoneN) {
        PhoneN = phoneN;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getThing() {
        return Thing;
    }

    public void setThing(String thing) {
        Thing = thing;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
