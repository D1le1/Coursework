package com.example.weatherapplication.weather;

import java.util.ArrayList;

public class Weather {
    private Place place;
    private String status;
    private Temperature temperature;
    private int icon;
    private ArrayList<Integer> castTemp;
    private ArrayList<String> castTime;
    private ArrayList<Integer> castIcon;

    public Weather(Place place, String status, Temperature temperature, int icon, ArrayList<Integer> castTemp,
                   ArrayList<String> castTime, ArrayList<Integer> castIcon) {
        this.place = place;
        this.status = status;
        this.temperature = temperature;
        this.icon = icon;
        this.castIcon = castIcon;
        this.castTemp = castTemp;
        this.castTime = castTime;
    }

    public Place getPlace() {
        return place;
    }

    public String getStatus() {
        return status;
    }

    public Temperature getTemperature() {
        return temperature;
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
}
