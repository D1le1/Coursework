package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.widget.TextView;

import com.example.weatherapplication.adapter.CityAdapter;
import com.example.weatherapplication.adapter.SlidePagerAdapter;
import com.example.weatherapplication.adapter.fragments.CityItem;
import com.example.weatherapplication.classes.City;
import com.example.weatherapplication.data.JSONWeatherParser;
import com.example.weatherapplication.data.WeatherHttpClient;
import com.example.weatherapplication.weather.Weather;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private ArrayList<City> cities = new ArrayList<>();
    private TextView hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities.add(new City("Minsk", "Belarus", 10, 10, -1, "Mostly Cloudy"));
        cities.add(new City("Babruysk", "Belarus", 8, 13, 2, "Mostly Sunny"));
        cities.add(new City("Gomel", "Belarus", 3, 7, -4, "Sunny"));
        cities.add(new City("Washington D.C.", "USA", 15, 19, 10, "Rain"));
        cities.add(new City("Chicago", "USA", 17, 23, 15, "Partly Cloudy"));
        renderWeatherData("Minsk");

        ArrayList<Fragment>list = createFragments();
        ViewPager viewPager = findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), list);;
        viewPager.setAdapter(pagerAdapter);
    }

    public ArrayList<Fragment> createFragments()
    {
        ArrayList<Fragment> list = new ArrayList<>();
        for (City city : cities)
        {
            list.add(new CityItem(city));
        }
        return list;
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
                Weather weather = JSONWeatherParser.getWeather(data);
                return weather;
            } catch (JSONException e) {}

            return null;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
        }
    }
}