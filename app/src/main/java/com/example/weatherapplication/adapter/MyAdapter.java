package com.example.weatherapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.weatherapplication.MainActivity;
import com.example.weatherapplication.R;
import com.example.weatherapplication.classes.City;
import com.example.weatherapplication.functionality.DateFunctions;
import com.example.weatherapplication.functionality.NetworkDetector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<City> cities;
    private Map<String, Integer> icons ;

    public MyAdapter(Context context, ArrayList<City> cities) {
        this.context = context;
        this.cities = cities;

        icons = new HashMap<>();
        icons.put("Clear sky", R.drawable.fc);
        icons.put("Few clouds", R.drawable.fc);
        icons.put("Scattered clouds", R.drawable.sc);
        icons.put("Broken clouds", R.drawable.bc);
        icons.put("Shower rain", R.drawable.sr);
        icons.put("Rain", R.drawable.r);
        icons.put("Thunderstorm", R.drawable.thu);
        icons.put("Snow", R.drawable.sn);
        icons.put("Mist", R.drawable.m);
        icons.put("Overcast clouds", R.drawable.bc);
    }


    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.city_item, container, false);

        TextView name, temperature, tempRange, status, date;
        ImageView icon;

        name = view.findViewById(R.id.city_name);
        temperature = view.findViewById(R.id.temperature);
        tempRange = view.findViewById(R.id.temp_range);
        status = view.findViewById(R.id.status);
        date = view.findViewById(R.id.date);
        icon = view.findViewById(R.id.weather_icon);

        name.setText(cities.get(position).getName() + ", " + cities.get(position).getCountry());
        temperature.setText(String.valueOf(cities.get(position).getDegrees()));
        tempRange.setText(cities.get(position).getMaxDegrees() + "°C / " + cities.get(position).getMinDegrees() + "°C");
        status.setText(cities.get(position).getStatus());
        date.setText(DateFunctions.getDate());
        icon.setImageResource(icons.get(cities.get(position).getStatus()));

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
