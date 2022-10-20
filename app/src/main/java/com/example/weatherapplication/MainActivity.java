package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.weatherapplication.adapter.CityAdapter;
import com.example.weatherapplication.adapter.SlidePagerAdapter;
import com.example.weatherapplication.adapter.fragments.PageFragment;
import com.example.weatherapplication.adapter.fragments.PageFragment1;
import com.example.weatherapplication.classes.City;
import com.example.weatherapplication.functionality.DateFunctions;
import com.example.weatherapplication.functionality.Gestures;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private CityAdapter cityAdapter;
    private RecyclerView cityRecycler;
    private ArrayList<City> cities = new ArrayList<>();
    private ViewPager vp;
    private PagerAdapter pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities.add(new City("Minsk", "Belarus", 10, 10, -1, "Mostly Cloudy"));
        cities.add(new City("Babruysk", "Belarus", 8, 13, 2, "Mostly Sunny"));
        cities.add(new City("Gomel", "Belarus", 3, 7, -4, "Sunny"));
        cities.add(new City("Washington D.C.", "USA", 15, 19, 10, "Rain"));
//        setCitiesRecycler(cities);

        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new PageFragment());
        list.add(new PageFragment1());

        vp = findViewById(R.id.pager);
        pa = new SlidePagerAdapter(getSupportFragmentManager(), list);

        vp.setAdapter(pa);

    }
    public void setCitiesRecycler(ArrayList<City> cities)
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        cityRecycler = findViewById(R.id.citiesRecycler);
        cityRecycler.setLayoutManager(layoutManager);

        cityAdapter = new CityAdapter(this, cities);
        cityRecycler.setAdapter(cityAdapter);
    }
}