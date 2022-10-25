package com.example.weatherapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.adapter.RecyclerAdapter;
import com.example.weatherapplication.classes.City;

import java.util.ArrayList;

public class ManageActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_activity);

        back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(ManageActivity.this, R.anim.click_anim));
            ManageActivity.this.finish();
        });

        recycler = findViewById(R.id.recycler);
        setRecycler();
    }

    public void setRecycler()
    {
        ArrayList<City> offlineCities = new ArrayList<>();
        offlineCities.add(new City("Minsk", "BY", 10, 10, -1, "Overcast clouds", "04"));
        offlineCities.add(new City("Babruysk", "BY", 8, 13, 2, "Clear sky", "01"));
        offlineCities.add(new City("Gomel", "BY", 3, 7, -4, "Rain", "10"));
        offlineCities.add(new City("Washington D.C.", "USA", 15, 19, 10, "Mist", "50"));
        offlineCities.add(new City("Chicago", "USA", 17, 23, 15, "Thunderstorm", "11"));
        offlineCities.add(new City("Chicago", "USA", 17, 23, 15, "Thunderstorm", "11"));
        offlineCities.add(new City("Chicago", "USA", 17, 23, 15, "Thunderstorm", "11"));
        offlineCities.add(new City("Chicago", "USA", 17, 23, 15, "Thunderstorm", "11"));
        offlineCities.add(new City("Chicago", "USA", 17, 23, 15, "Thunderstorm", "11"));
        offlineCities.add(new City("Chicago", "USA", 17, 23, 15, "Thunderstorm", "11"));
        offlineCities.add(new City("Chicago", "USA", 17, 23, 15, "Thunderstorm", "11"));
        offlineCities.add(new City("Chicago", "USA", 17, 23, 15, "Thunderstorm", "11"));
        offlineCities.add(new City("Chicago", "USA", 17, 23, 15, "Thunderstorm", "11"));
        offlineCities.add(new City("Chicago", "USA", 17, 23, 15, "Thunderstorm", "11"));


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);

        RecyclerAdapter adapter = new RecyclerAdapter(this, offlineCities);
        recycler.setAdapter(adapter);
    }
}
