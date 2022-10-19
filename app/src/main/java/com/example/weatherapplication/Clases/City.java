package com.example.weatherapplication.Clases;

import java.util.Date;

public class City {
    private String name;
    private String country;
    private int degrees;
    private Date date = new Date();
    private int maxDegrees;
    private int minDegrees;
    private String status;
//    private int index;

    public City(String name, String country, int degrees, int maxDegrees, int minDegrees, String status) {
        this.name = name;
        this.country = country;
        this.degrees = degrees;
        this.maxDegrees = maxDegrees;
        this.minDegrees = minDegrees;
        this.status = status;
//        this.index = index;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getDegrees() {
        return degrees;
    }

    public Date getDate() {
        return date;
    }

    public int getMaxDegrees() {
        return maxDegrees;
    }

    public int getMinDegrees() {
        return minDegrees;
    }

    public String getStatus() {
        return status;
    }

//    public int getIndex(){
//        return index;
//    }
}
