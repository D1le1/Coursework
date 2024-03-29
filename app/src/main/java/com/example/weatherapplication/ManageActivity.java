package com.example.weatherapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.adapter.RecyclerAdapter;
import com.example.weatherapplication.classes.City;
import com.example.weatherapplication.data.JSONWeatherParser;
import com.example.weatherapplication.data.WeatherHttpClient;
import com.example.weatherapplication.functionality.NetworkDetector;
import com.example.weatherapplication.weather.Weather;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

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
    private ItemTouchHelper helper;
    private AlertDialog dialog;


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
        setDialog();

        add = findViewById(R.id.add);

        add.setOnClickListener(view -> {
            if(NetworkDetector.isConnected(this)) {
                dialog.show();
            }
            else
            {
                Toast.makeText(this, "Turn on Wi-F or Mobile Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("cities", cities);
        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }

    private void setDialog()
    {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_item,null);
        dialog = (new AlertDialog.Builder(this)).create();
        dialog.setView(view);

        EditText searchText = view.findViewById(R.id.search_text);
        Button searchButton = view.findViewById(R.id.search_button);

        searchButton.setOnClickListener(v -> {
            renderWeatherData(searchText.getText().toString());
        });
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
                int position = viewHolder.getAdapterPosition();
                City city = cities.get(position);
                cities.remove(position);
                recycler.getAdapter().notifyItemRemoved(position);

                Snackbar snackbar = Snackbar.make(recycler, "City Removed", Snackbar.LENGTH_LONG).
                        setAction("UNDO", view -> {
                            setCity(city, position);
                            recycler.getAdapter().notifyItemInserted(position);
                        });

                ViewGroup contentLay = (ViewGroup) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text).getParent();
                View view = ManageActivity.this.getLayoutInflater().inflate(R.layout.progressbar_item, null);
                TextView tv = view.findViewById(R.id.timer);
                new CountDownTimer(5100, 1000) {

                    public void onTick(long mills) {
                        tv.setText(mills/1000 + "");
                    }

                    public void onFinish() {
                    }

                }.start();
                contentLay.addView(view, 0);

                snackbar.setTextColor(getResources().getColor(R.color.white));
                snackbar.setActionTextColor(Color.parseColor("#FFFF8800"));
                snackbar.setBackgroundTint(getResources().getColor(R.color.card_bg));
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.setDuration(4600);
                snackbar.show();
            }
        });
        helper.attachToRecyclerView(recycler);
    }

    private void setCity(City city, int position)
    {
        ArrayList<City> newCities = new ArrayList<>();
        int i = 0;
        if(position == cities.size())
        {
            cities.add(city);
        }
        else {
            for (City c : cities) {
                if (i != position) {
                    newCities.add(c);
                } else {
                    newCities.add(city);
                    newCities.add(c);
                }
                i++;
            }
            cities.clear();
            cities.addAll(newCities);
        }
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
            }catch (NullPointerException e){
                Toast.makeText(ManageActivity.this, "Something went wrong. Check your internet connection or city name", Toast.LENGTH_SHORT).show();
            }catch (Exception e)
            {
                Toast.makeText(ManageActivity.this, "There is no such city", Toast.LENGTH_SHORT).show();
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
