package com.example.app;

public class DataModel {

    private final String country;
    private final String probablity;

    public DataModel(String country, String probablity) {
        this.country = country;
        this.probablity = probablity;
    }

    public String getCountry() {
        return country;
    }

    public String getProbablity() {
        return probablity;
    }

}
