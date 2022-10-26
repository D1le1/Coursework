package com.example.weatherapplication.classes;

import com.example.weatherapplication.MainActivity;
import com.example.weatherapplication.weather.Weather;

import java.io.Serializable;

public class City implements Serializable {
    private String name;
    private String country;
    private int degrees;
    private int maxDegrees;
    private int minDegrees;
    private String status;
    private String icon;

    public City(String name, String country, int degrees, int maxDegrees, int minDegrees, String status, String icon) {
        this.name = name;
        this.country = country;
        this.degrees = degrees;
        this.maxDegrees = maxDegrees;
        this.minDegrees = minDegrees;
        this.status = status;
        this.icon = icon;
    }

    public City(Weather weather) {
        this.name = weather.getPlace().getCity();
        this.country = weather.getPlace().getCountry();
        this.degrees = (int) Math.ceil(weather.getTemperature().getCurrantTemperature());
        this.minDegrees = (int) Math.ceil(weather.getTemperature().getMinTemp());
        this.maxDegrees = (int) Math.ceil(weather.getTemperature().getMaxTemp());
        this.status = MainActivity.formatStatus(weather.getStatus());
        this.icon = weather.getIcon();
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

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return name + ", " + country;
    }
}
