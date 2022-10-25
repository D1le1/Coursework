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
        icons.put("01", R.drawable.cs);
        icons.put("02", R.drawable.fc);
        icons.put("03", R.drawable.sc);
        icons.put("04", R.drawable.bc);
        icons.put("50", R.drawable.m);
        icons.put("13", R.drawable.sn);
        icons.put("10", R.drawable.r);
        icons.put("09", R.drawable.sr);
        icons.put("11", R.drawable.thu);
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
//        Toast.makeText(context, cities.get(position).getIcon().substring(0,2), Toast.LENGTH_SHORT).show();
        icon.setImageResource(icons.get(cities.get(position).getIcon().substring(0,2)));

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
