package com.example.weatherapplication.adapter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherapplication.R;
import com.example.weatherapplication.classes.City;
import com.example.weatherapplication.functionality.DateFunctions;

public class FragmentItem extends Fragment {
    private City city;

    public FragmentItem(City city)
    {
        this.city = city;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStated) {
        View view = inflater.inflate(R.layout.city_item, container, false);
        TextView name, temperature, tempRange, status, date;
        name = view.findViewById(R.id.city_name);
        temperature = view.findViewById(R.id.temperature);
        tempRange = view.findViewById(R.id.temp_range);
        status = view.findViewById(R.id.status);
        date = view.findViewById(R.id.date);
        name.setText(city.getName() + ", " + city.getCountry());
        temperature.setText(String.valueOf(city.getDegrees()));
        tempRange.setText(city.getMaxDegrees() + "°C / " + city.getMinDegrees() + "°C");
        status.setText(city.getStatus());
        date.setText(DateFunctions.getDate());
        return view;
    }
}
