package com.example.weatherapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.adapter.RecyclerAdapter;
import com.example.weatherapplication.classes.City;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class ManageActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private ImageView back;
    private FloatingActionButton add;
    private ArrayList<City> cities;
    private EditText et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_activity);

        cities = (ArrayList<City>) getIntent().getSerializableExtra("cities");

        back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(ManageActivity.this, R.anim.click_anim));

            Intent intent = new Intent();
            intent.putExtra("cities", (Serializable) cities);
            setResult(RESULT_OK, intent);

            ManageActivity.this.finish();
        });

        recycler = findViewById(R.id.recycler);
        setRecycler();

        add = findViewById(R.id.add);
        add.setOnClickListener((View view) -> {
            et = findViewById(R.id.et);

            cities.add(new City(et.getText().toString(), "BY", 10, 10, 5, "Sunny", "01"));

            setRecycler();
        });
    }

    public void setRecycler()
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);

        RecyclerAdapter adapter = new RecyclerAdapter(this, cities);
        recycler.setAdapter(adapter);
    }
}
