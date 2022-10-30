package com.example.fuelqueuemanager;

//station owner class
public class StationOwner {

    String address;
    String email;

    public StationOwner(String address, String email) {
        this.address = address;
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
