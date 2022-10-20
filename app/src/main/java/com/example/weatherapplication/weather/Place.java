package com.example.weatherapplication.weather;

public class Place {
    private String country;
    private String city;
    private long lastUpdate;

    public Place(String country, String city, long lastUpdate) {
        this.country = country;
        this.city = city;
        this.lastUpdate = lastUpdate;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }
}
