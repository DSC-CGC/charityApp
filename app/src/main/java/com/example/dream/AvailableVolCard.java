package com.example.dream;

public class AvailableVolCard {
    String uId, Name, Address, FromTime, ToTime, WorkingDays, Phone;

    public AvailableVolCard() {

    }

    public AvailableVolCard(String uId, String name, String address, String fromTime, String toTime, String workingDays, String phone) {
        this.uId = uId;
        this.Name = name;
        this.Address = address;
        this.FromTime = fromTime;
        this.ToTime = toTime;
        this.WorkingDays = workingDays;
        this.Phone = phone;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getFromTime() {
        return FromTime;
    }

    public void setFromTime(String fromTime) {
        FromTime = fromTime;
    }

    public String getToTime() {
        return ToTime;
    }

    public void setToTime(String toTime) {
        ToTime = toTime;
    }

    public String getWorkingDays() {
        return WorkingDays;
    }

    public void setWorkingDays(String workingDays) {
        WorkingDays = workingDays;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
