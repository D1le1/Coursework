package com.example.weatherapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherapplication.adapter.MyAdapter;
import com.example.weatherapplication.classes.City;
import com.example.weatherapplication.data.JSONWeatherParser;
import com.example.weatherapplication.data.WeatherHttpClient;
import com.example.weatherapplication.functionality.NetworkDetector;
import com.example.weatherapplication.util.FadeName;
import com.example.weatherapplication.weather.Weather;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    private ArrayList<City> cities;
    private ViewPager viewPager;
    private MyAdapter adapter;
    private TextView netError;
    private SwipeRefreshLayout refresher;
    private ImageView city;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        viewPager.setPageTransformer(false, new FadeName());
        cities = new ArrayList<>();
        refresher = findViewById(R.id.refresher);
        netError = findViewById(R.id.net_error);
        city = findViewById(R.id.cities);

        try{
            loadData();
        }catch (Exception e) {
        }
        updateData();

        city.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.click_anim));

            Intent intent = new Intent(MainActivity.this, ManageActivity.class);
            intent.putExtra("cities", (Serializable) cities);

            startActivity(intent);
        });

        refresher.setOnRefreshListener(() -> {
            updateData();
        });
    }

    public void updateData()
    {
        refresher.setRefreshing(true);

        if (NetworkDetector.isConnected(this)) {
            cities.clear();
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

    private void saveData() throws IOException {
        FileOutputStream file = openFileOutput("data", MODE_PRIVATE);
        ObjectOutputStream object = new ObjectOutputStream(file);

        object.writeObject(cities);

        object.close();
        file.close();
    }

    private void loadData() throws IOException, ClassNotFoundException {
        FileInputStream file = openFileInput("data");
        ObjectInputStream object = new ObjectInputStream(file);


        cities = (ArrayList<City>) object.readObject();

        object.close();
        file.close();

        adapter = new MyAdapter(this, cities);
        viewPager.setAdapter(adapter);
        viewPager.getAdapter().notifyDataSetChanged();
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

                adapter = new MyAdapter(MainActivity.this, cities);
                viewPager.setAdapter(adapter);
                viewPager.getAdapter().notifyDataSetChanged();

                netError.setVisibility(View.INVISIBLE);
                refresher.setRefreshing(false);
                try {
                    saveData();
                } catch (Exception e){}
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
        return status.substring(0, 1).toUpperCase(Locale.ROOT) + status.substring(1);
    }
}