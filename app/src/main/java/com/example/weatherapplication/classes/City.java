package com.example.weatherapplication.classes;

public class City {
    private String name;
    private String country;
    private int degrees;
    private int maxDegrees;
    private int minDegrees;
    private String status;

    public City(String name, String country, int degrees, int maxDegrees, int minDegrees, String status) {
        this.name = name;
        this.country = country;
        this.degrees = degrees;
        this.maxDegrees = maxDegrees;
        this.minDegrees = minDegrees;
        this.status = status;
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

    public int getMaxDegrees() {
        return maxDegrees;
    }

    public int getMinDegrees() {
        return minDegrees;
    }

    public String getStatus() {
        return status;
    }
}
