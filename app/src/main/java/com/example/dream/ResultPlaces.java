package com.example.dream;

public class ResultPlaces {
    String Name;
    String Phone;
    String Quantity;
    String ThingRequired;
    String UploadDate;

    public ResultPlaces(String name, String phone, String quantity, String thingRequired, String uploadDate) {
        Name = name;
        Phone = phone;
        Quantity = quantity;
        ThingRequired = thingRequired;
        UploadDate = uploadDate;
    }
    public ResultPlaces()
    {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getThingRequired() {
        return ThingRequired;
    }

    public void setThingRequired(String thingRequired) {
        ThingRequired = thingRequired;
    }

    public String getUploadDate() {
        return UploadDate;
    }

    public void setUploadDate(String uploadDate) {
        UploadDate = uploadDate;
    }


}
