package com.example.weatherapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherapplication.adapter.ViewPagerAdapter;
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
    private ArrayList<City> offlineCities;
    private ViewPager viewPager;
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
        offlineCities = new ArrayList<>();
        refresher = findViewById(R.id.refresher);
        netError = findViewById(R.id.net_error);
        city = findViewById(R.id.cities);

        try{
            loadData();
        }catch (Exception e) {}
        updateData();

        city.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.click_anim));

            Intent intent = new Intent(MainActivity.this, ManageActivity.class);
            intent.putExtra("cities", (Serializable) offlineCities);

            startActivityForResult(intent, 100);
        });

        setViewPager();

        refresher.setOnRefreshListener(this::updateData);
    }

    private void setViewPager() {
        viewPager.setAdapter(new ViewPagerAdapter(this, offlineCities, refresher));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(refresher != null && !refresher.isRefreshing())
                {
                    refresher.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            ArrayList<City> cities = (ArrayList<City>) data.getSerializableExtra("cities");

            offlineCities.clear();
            offlineCities.addAll(cities);

            viewPager.getAdapter().notifyDataSetChanged();

            try {
                saveData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateData()
    {
        refresher.setRefreshing(true);

        if (NetworkDetector.isConnected(this)) {
            if (!offlineCities.isEmpty()) {
                for (City city : offlineCities) {
                    renderWeatherData(city.getName());
                }
            }
            else
            {
                refresher.setRefreshing(false);
            }
        }
        else{
            refresher.setRefreshing(false);
            netError.animate().setDuration(500).alpha(1);
        }
    }

    private void saveData() throws IOException {
        FileOutputStream file = openFileOutput("data", MODE_PRIVATE);
        ObjectOutputStream object = new ObjectOutputStream(file);

        object.writeObject(offlineCities);

        object.close();
        file.close();
    }

    private void loadData() throws IOException, ClassNotFoundException {
        FileInputStream file = openFileInput("data");
        ObjectInputStream object = new ObjectInputStream(file);


        offlineCities = (ArrayList<City>) object.readObject();

        object.close();
        file.close();
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

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            try {
                getPostData(weather);

                netError.animate().setDuration(500).alpha(0);
            }catch (Exception e)
            {
                netError.animate().setDuration(500).alpha(1);

            }finally {
                refresher.setRefreshing(false);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getPostData(Weather weather)
    {
        offlineCities.stream().filter(x -> x.getName().equals(weather.getPlace().getCity())).forEach(x -> x.setData(weather));
        viewPager.getAdapter().notifyDataSetChanged();

        try {
            saveData();
        } catch (Exception e){}
    }

    public static String formatStatus(String status)
    {
        return status.substring(0, 1).toUpperCase(Locale.ROOT) + status.substring(1);
    }
}