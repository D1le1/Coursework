package com.example.weatherapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.adapter.MyAdapter;
import com.example.weatherapplication.adapter.RecyclerAdapter;
import com.example.weatherapplication.classes.City;
import com.example.weatherapplication.data.JSONWeatherParser;
import com.example.weatherapplication.data.WeatherHttpClient;
import com.example.weatherapplication.weather.Weather;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ManageActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private ImageView back;
    private FloatingActionButton add;
    private ArrayList<City> cities;
    private EditText et;
    private ItemTouchHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_activity);

        cities = (ArrayList<City>) getIntent().getSerializableExtra("cities");

        back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(ManageActivity.this, R.anim.click_anim));

            onBackPressed();
        });

        recycler = findViewById(R.id.recycler);
        setRecycler();
        setHelper();

        add = findViewById(R.id.add);
        add.setOnClickListener((View view) -> {
            et = findViewById(R.id.et);
            renderWeatherData(et.getText().toString());
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("cities", (Serializable) cities);
        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }

    public void setHelper()
    {
        helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder drag, @NonNull RecyclerView.ViewHolder drop) {
                int dragPosition = drag.getAdapterPosition();
                int dropPosition = drop.getAdapterPosition();

                Collections.swap(cities, dragPosition, dropPosition);

                recycler.getAdapter().notifyItemMoved(dragPosition, dropPosition);

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                cities.remove(viewHolder.getAdapterPosition());
                recycler.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        helper.attachToRecyclerView(recycler);
    }

    public void setRecycler()
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);

        RecyclerAdapter adapter = new RecyclerAdapter(this, cities);
        recycler.setAdapter(adapter);
    }

    public void renderWeatherData(String city)
    {
        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(city);
    }

    private class WeatherTask extends AsyncTask<String, Void, Weather>
    {
        @Override
        protected Weather doInBackground(String... params) {
            try {
                String data = new WeatherHttpClient().getWeatherData(params[0]);
                return JSONWeatherParser.getWeather(data);
            } catch (JSONException e) {} catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            try {
                City city = new City(weather);
                if(!containsCity(city)) {
                    cities.add(city);
                    recycler.getAdapter().notifyItemInserted(cities.size() - 1);
                }
                else
                {
                    Toast.makeText(ManageActivity.this, "This city already exists", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Toast.makeText(ManageActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean containsCity(City city)
    {
        for (City c : cities)
        {
            if(c.getName().equals(city.getName()))
                return true;
        }
        return false;
    }
}
