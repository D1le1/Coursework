package com.example.weatherapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

            onBackPressed();
        });

        recycler = findViewById(R.id.recycler);
        setRecycler();

        add = findViewById(R.id.add);
        add.setOnClickListener((View view) -> {
            et = findViewById(R.id.et);
            if (containsCity(cities, et.getText().toString()))
            {
                Toast.makeText(ManageActivity.this, "This city is already on the list", Toast.LENGTH_SHORT).show();
            }else {
                renderWeatherData(et.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("cities", (Serializable) cities);
        setResult(RESULT_OK, intent);

        super.onBackPressed();
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
        weatherTask.execute(new String[]{city + "&units=metric"});
    }

    private class WeatherTask extends AsyncTask<String, Void, Weather>
    {
        @Override
        protected Weather doInBackground(String... params) {
            String data = new WeatherHttpClient().getWeatherData(params[0]);
            try {
                return JSONWeatherParser.getWeather(data);
            } catch (JSONException e) {}

            return null;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            try {
                City city = new City(weather);
                cities.add(city);
                recycler.getAdapter().notifyDataSetChanged();
            }catch (Exception e){
                Toast.makeText(ManageActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean containsCity(ArrayList<City> cities, String city)
    {
        for (City c : cities)
        {
            if (c.getName().equals(city))
            {
                return true;
            }
        }
        return false;
    }
}
