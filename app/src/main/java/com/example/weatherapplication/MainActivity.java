package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.weatherapplication.Clases.City;
import com.example.weatherapplication.Functions.Gestures;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private ArrayList<City> cities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities.add(new City("Minsk", "Belarus", 10, 10, -1, "Mostly Cloudy"));
        cities.add(new City("Babruysk", "Belarus", 8, 13, 2, "Mostly Sunny"));
        cities.add(new City("Gomel", "Belarus", 3, 7, -4, "Sunny"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        changeBySwipe(event);
        return super.onTouchEvent(event);
    }

    private void changeBySwipe(MotionEvent event)
    {
        try {
            City city = Gestures.Swipe.swipeCities(event, cities);
            ((TextView)findViewById(R.id.city_name)).setText(city.getName() + ", " + city.getCountry());
            ((TextView)findViewById(R.id.temperature)).setText(city.getDegrees() + "°C");
            ((TextView)findViewById(R.id.temp_range)).setText(city.getMaxDegrees() + "°C/" + city.getMinDegrees() + "°C");
            ((TextView)findViewById(R.id.status)).setText(city.getStatus());
        }catch (Exception e){}
    }
}