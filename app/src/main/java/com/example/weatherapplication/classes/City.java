package com.example.weatherapplication.classes;

import com.example.weatherapplication.MainActivity;
import com.example.weatherapplication.weather.Weather;

import java.io.Serializable;
import java.util.ArrayList;

public class City implements Serializable {
    private String name;
    private String country;
    private int degrees;
    private int maxDegrees;
    private int minDegrees;
    private String status;
    private int icon;
    private ArrayList<Integer> castTemp;
    private ArrayList<String> castTime;
    private ArrayList<Integer> castIcon;

    public City(String name, String country, int degrees, int maxDegrees, int minDegrees, String status, int icon) {
        this.name = name;
        this.country = country;
        this.degrees = degrees;
        this.maxDegrees = maxDegrees;
        this.minDegrees = minDegrees;
        this.status = status;
        this.icon = icon;
    }

    public City(String name)
    {
        this.name = name;
    }

    public City(Weather weather) {
        this.name = weather.getPlace().getCity();
        this.country = weather.getPlace().getCountry();
        this.degrees = (int) Math.ceil(weather.getTemperature().getCurrantTemperature());
        this.minDegrees = (int) Math.ceil(weather.getTemperature().getMinTemp());
        this.maxDegrees = (int) Math.ceil(weather.getTemperature().getMaxTemp());
        this.status = MainActivity.formatStatus(weather.getStatus());
        this.icon = weather.getIcon();
        this.castIcon = weather.getCastIcon();
        this.castTemp = weather.getCastTemp();
        this.castTime = weather.getCastTime();
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

    public int getIcon() {
        return icon;
    }

    public ArrayList<Integer> getCastTemp() {
        return castTemp;
    }

    public ArrayList<String> getCastTime() {
        return castTime;
    }

    public ArrayList<Integer> getCastIcon() {
        return castIcon;
    }

    public void setData(Weather weather)
    {
        this.name = weather.getPlace().getCity();
        this.country = weather.getPlace().getCountry();
        this.degrees = (int) Math.ceil(weather.getTemperature().getCurrantTemperature());
        this.minDegrees = (int) Math.ceil(weather.getTemperature().getMinTemp());
        this.maxDegrees = (int) Math.ceil(weather.getTemperature().getMaxTemp());
        this.status = MainActivity.formatStatus(weather.getStatus());
        this.icon = weather.getIcon();
        this.castIcon = weather.getCastIcon();
        this.castTemp = weather.getCastTemp();
        this.castTime = weather.getCastTime();
    }

    @Override
    public String toString() {
        return name + ", " + country;
    }
}
