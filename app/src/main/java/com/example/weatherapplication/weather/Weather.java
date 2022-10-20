package com.example.weatherapplication.weather;

public class Weather {
    private Place place;
    private String status;
    private Temperature temperature;

    public Weather(Place place, String status, Temperature temperature) {
        this.place = place;
        this.status = status;
        this.temperature = temperature;
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
}
