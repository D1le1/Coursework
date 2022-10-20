package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.weatherapplication.adapter.SlidePagerAdapter;
import com.example.weatherapplication.adapter.fragments.CityItem;
import com.example.weatherapplication.classes.City;
import com.example.weatherapplication.data.JSONWeatherParser;
import com.example.weatherapplication.data.WeatherHttpClient;
import com.example.weatherapplication.weather.Weather;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private ArrayList<Fragment> cities = new ArrayList<>();
//    private TextView hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        cities.add(new City("Minsk", "Belarus", 10, 10, -1, "Mostly Cloudy"));
//        cities.add(new City("Babruysk", "Belarus", 8, 13, 2, "Mostly Sunny"));
//        cities.add(new City("Gomel", "Belarus", 3, 7, -4, "Sunny"));
//        cities.add(new City("Washington D.C.", "USA", 15, 19, 10, "Rain"));
//        cities.add(new City("Chicago", "USA", 17, 23, 15, "Partly Cloudy"));
        renderWeatherData("Minsk");
        renderWeatherData("Babruysk");
        renderWeatherData("Gomel");
        renderWeatherData("Chicago");
//        hello = findViewById(R.id.hello);
//        ArrayList<Fragment>list = createFragments();
    }

//    public ArrayList<Fragment> createFragments()
//    {
//        ArrayList<Fragment> list = new ArrayList<>();
//        for (City city : cities)
//        {
//            list.add(new CityItem(city));
//        }
//        return list;
//    }

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

            City city = (new City(weather.getPlace().getCity(), weather.getPlace().getCountry(),
                    (int)Math.ceil(weather.getTemperature().getCurrantTemperature()), (int)Math.ceil(weather.getTemperature().getMaxTemp()),
                    (int)Math.ceil(weather.getTemperature().getMinTemp()), weather.getStatus()));
            cities.add(new CityItem(city));
            ViewPager viewPager = findViewById(R.id.pager);
            PagerAdapter pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), cities);;
            viewPager.setAdapter(pagerAdapter);
        }
    }
}