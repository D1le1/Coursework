package com.example.weatherapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherapplication.adapter.SlidePagerAdapter;
import com.example.weatherapplication.adapter.fragments.CityItem;
import com.example.weatherapplication.classes.City;
import com.example.weatherapplication.data.JSONWeatherParser;
import com.example.weatherapplication.data.WeatherHttpClient;
import com.example.weatherapplication.functionality.NetworkDetector;
import com.example.weatherapplication.weather.Weather;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    private ArrayList<Fragment> cities = new ArrayList<>();
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        swipeContainer = findViewById(R.id.ref);
        loadInfo();
//        ref.setOnRefreshListener(() -> {
//            Log.v("Data: ", "Refreshed");
//            ref.setRefreshing(false);
//        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0)
        {
            loadInfo();
        }
        return super.onTouchEvent(event);
    }

    public void loadInfo()
    {
        if (NetworkDetector.isConnected(this)) {
            cities.clear();
            renderWeatherData("Minsk");
            renderWeatherData("Babruysk");
            renderWeatherData("Gomel");
            renderWeatherData("Zhlobin");
            renderWeatherData("Chicago");
            ((TextView)findViewById(R.id.net_error)).setVisibility(View.INVISIBLE);
            ((ViewPager)findViewById(R.id.pager)).setVisibility(View.VISIBLE);
        }
        else
        {
//            cities.clear();
//            cities.add(new CityItem(new City("Minsk", "BY", 10, 10, -1, "Mostly Cloudy")));
//            cities.add(new CityItem(new City("Babruysk", "BY", 8, 13, 2, "Mostly Sunny")));
//            cities.add(new CityItem(new City("Gomel", "BY", 3, 7, -4, "Sunny")));
//            cities.add(new CityItem(new City("Washington D.C.", "USA", 15, 19, 10, "Rain")));
//            cities.add(new CityItem(new City("Chicago", "USA", 17, 23, 15, "Partly Cloudy")));
//            ViewPager viewPager = findViewById(R.id.pager);
//            PagerAdapter pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), cities);
//            viewPager.setAdapter(pagerAdapter);
            ((TextView)findViewById(R.id.net_error)).setVisibility(View.VISIBLE);
            ((ViewPager)findViewById(R.id.pager)).setVisibility(View.INVISIBLE);
        }
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
            City city = (new City(weather.getPlace().getCity(), weather.getPlace().getCountry(),
                    (int) Math.ceil(weather.getTemperature().getCurrantTemperature()), (int) Math.ceil(weather.getTemperature().getMaxTemp()),
                    (int) Math.ceil(weather.getTemperature().getMinTemp()), formatStatus(weather.getStatus())));
            cities.add(new CityItem(city));
            ViewPager viewPager = findViewById(R.id.pager);
            PagerAdapter pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), cities);
            viewPager.setAdapter(pagerAdapter);
        }
    }

    public String formatStatus(String status)
    {
        String[] temp = status.split(" ");
        if (temp.length > 1)
            return temp[0].substring(0,1).toUpperCase(Locale.ROOT) + temp[0].substring(1) + " " + temp[1];
        else
            return temp[0].substring(0,1).toUpperCase(Locale.ROOT);
    }
}