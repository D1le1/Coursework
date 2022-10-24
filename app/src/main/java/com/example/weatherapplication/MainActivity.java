package com.example.weatherapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherapplication.adapter.MyAdapter;
import com.example.weatherapplication.classes.City;
import com.example.weatherapplication.data.JSONWeatherParser;
import com.example.weatherapplication.data.WeatherHttpClient;
import com.example.weatherapplication.functionality.NetworkDetector;
import com.example.weatherapplication.weather.Weather;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    private ArrayList<City> offlineCities;
    private ArrayList<City> onlineCities;
    private ViewPager viewPager;
    private MyAdapter adapter;
    private TextView netError;
    SwipeRefreshLayout refresher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        offlineCities = new ArrayList<>();
        onlineCities = new ArrayList<>();
        refresher = findViewById(R.id.refresher);
        netError = findViewById(R.id.net_error);

        loadCard();
        loadInfo();

        refresher.setOnRefreshListener(() -> {
            loadInfo();
        });
    }

    private void loadCard() {
        offlineCities.add(new City("Minsk", "BY", 10, 10, -1, "Mostly Cloudy"));
        offlineCities.add(new City("Babruysk", "BY", 8, 13, 2, "Mostly Sunny"));
        offlineCities.add(new City("Gomel", "BY", 3, 7, -4, "Sunny"));
        offlineCities.add(new City("Washington D.C.", "USA", 15, 19, 10, "Rain"));
        offlineCities.add(new City("Chicago", "USA", 17, 23, 15, "Partly Cloudy"));

        adapter = new MyAdapter(this, offlineCities);
        viewPager.setAdapter(adapter);
        viewPager.getAdapter().notifyDataSetChanged();
    }

    public void loadInfo()
    {
        refresher.setRefreshing(true);

        if (NetworkDetector.isConnected(this)) {
            renderWeatherData("Minsk");
            renderWeatherData("Babruysk");
            renderWeatherData("Gomel");
            renderWeatherData("Zhlobin");
            renderWeatherData("Chicago");
        }
        else{
            refresher.setRefreshing(false);
            netError.setVisibility(View.VISIBLE);
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
            try {
                City city = new City(weather);
                onlineCities.add(city);

                adapter = new MyAdapter(MainActivity.this, onlineCities);
                viewPager.setAdapter(adapter);
                viewPager.getAdapter().notifyDataSetChanged();

                netError.setVisibility(View.INVISIBLE);
                refresher.setRefreshing(false);
            }catch (Exception e)
            {
                refresher.setRefreshing(false);
                netError.setVisibility(View.VISIBLE);

//                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                    @Override
//                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                    }
//
//                    @Override
//                    public void onPageSelected(int position) {
//
//                    }
//
//                    @Override
//                    public void onPageScrollStateChanged(int state) {
//                        toggleRefreshing(state == ViewPager.SCROLL_STATE_IDLE);
//                    }
//
//                    private void toggleRefreshing(boolean b) {
//                        if(refresher != null)
//                        {
//                            refresher.setEnabled(b);
//                        }
//                    }
//                });
            }
        }
    }

    public static String formatStatus(String status)
    {
        String[] temp = status.split(" ");
        if (temp.length > 1)
            return temp[0].substring(0,1).toUpperCase(Locale.ROOT) + temp[0].substring(1) + " " + temp[1];
        else
            return temp[0].substring(0,1).toUpperCase(Locale.ROOT);
    }
}