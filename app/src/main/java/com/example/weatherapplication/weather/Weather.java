package com.example.weatherapplication.weather;

public class Weather {
    private Place place;
    private String status;
    private Temperature temperature;
    private String icon;

    public Weather(Place place, String status, Temperature temperature, String icon) {
        this.place = place;
        this.status = status;
        this.temperature = temperature;
        this.icon = icon;
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

    public String getIcon() {
        return icon;
    }
}
