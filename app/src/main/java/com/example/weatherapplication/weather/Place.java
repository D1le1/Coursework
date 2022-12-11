package com.example.weatherapplication.weather;

public class Place {
    private String country;
    private String city;

    public Place(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
