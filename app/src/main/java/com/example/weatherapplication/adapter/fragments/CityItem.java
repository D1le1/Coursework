package com.example.weatherapplication.adapter.fragments;

import com.example.weatherapplication.classes.City;

public class CityItem {
    private City city;

    public CityItem(City city)
    {
        this.city = city;
    }

    public City getCity() {
        return city;
    }
}
