package com.example.weatherapplication.classes;

import com.example.weatherapplication.MainActivity;
import com.example.weatherapplication.weather.Weather;

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

    public City(Weather weather) {
        this.name = weather.getPlace().getCity();
        this.country = weather.getPlace().getCountry();
        this.degrees = (int) Math.ceil(weather.getTemperature().getCurrantTemperature());
        this.minDegrees = (int) Math.ceil(weather.getTemperature().getMinTemp());
        this.maxDegrees = (int) Math.ceil(weather.getTemperature().getMaxTemp());
        this.status = MainActivity.formatStatus(weather.getStatus());
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

    @Override
    public String toString() {
        return name + ", " + country;
    }
}
