package com.example.weatherapplication.weather;

public class Temperature {
    private float minTemp;
    private float maxTemp;
    private float temperature;

    public Temperature(float minTemp, float maxTemp, float temperature) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.temperature = temperature;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public float getTemperature() {
        return temperature;
    }
}
