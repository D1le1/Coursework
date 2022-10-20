package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.weatherapplication.adapter.CityAdapter;
import com.example.weatherapplication.classes.City;
import com.example.weatherapplication.functionality.DateFunctions;
import com.example.weatherapplication.functionality.Gestures;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    CityAdapter cityAdapter;
    RecyclerView cityRecycler;
    private ArrayList<City> cities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ((TextView)findViewById(R.id.date)).setText(DateFunctions.getDate());
        cities.add(new City("Minsk", "Belarus", 10, 10, -1, "Mostly Cloudy"));
        cities.add(new City("Babruysk", "Belarus", 8, 13, 2, "Mostly Sunny"));
        cities.add(new City("Gomel", "Belarus", 3, 7, -4, "Sunny"));
        cities.add(new City("Washington D.C.", "USA", 15, 19, 10, "Rain"));
        setCitiesRecycler(cities);

    }
    public void setCitiesRecycler(ArrayList<City> cities)
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        cityRecycler = findViewById(R.id.citiesRecycler);
        cityRecycler.setLayoutManager(layoutManager);

        cityAdapter = new CityAdapter(this, cities);
        cityRecycler.setAdapter(cityAdapter);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event)
//    {
//        changeBySwipe(event);
//        return super.onTouchEvent(event);
//    }
//
//    private void changeBySwipe(MotionEvent event)
//    {
//        try {
//            City city = Gestures.Swipe.swipeCities(event, cities);
//            ((TextView)findViewById(R.id.city_name)).setText(city.getName() + ", " + city.getCountry());
//            ((TextView)findViewById(R.id.temperature)).setText(city.getDegrees() + "°C");
//            ((TextView)findViewById(R.id.temp_range)).setText(city.getMaxDegrees() + "°C / " + city.getMinDegrees() + "°C");
//            ((TextView)findViewById(R.id.status)).setText(city.getStatus());
//        }catch (Exception e){}
//    }
}