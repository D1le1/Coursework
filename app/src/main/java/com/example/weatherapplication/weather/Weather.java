package com.example.weatherapplication.weather;

public class Weather {
    private Place place;
    private String iconData;
    private Temperature temperature;

    public Weather(Place place, String iconData, Temperature temperature) {
        this.place = place;
        this.iconData = iconData;
        this.temperature = temperature;
    }

    public Weather(Place place, String iconData) {
        this.place = place;
        this.iconData = iconData;
    }

    public Place getPlace() {
        return place;
    }

    public String getIconData() {
        return iconData;
    }

    public Temperature getTemperature() {
        return temperature;
    }
}
