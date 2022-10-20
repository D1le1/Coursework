package com.example.weatherapplication.adapter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.R;
import com.example.weatherapplication.classes.City;
import com.example.weatherapplication.functionality.DateFunctions;

import java.util.ArrayList;

public class CityItem extends Fragment {
    private City city;

    public CityItem(City city)
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
        temperature.setText(city.getDegrees() + "°C");
        tempRange.setText(city.getMaxDegrees() + "°C / " + city.getMinDegrees() + "°C");
        status.setText(city.getStatus());
        date.setText(DateFunctions.getDate());
        return view;
    }

    public static final class CityViewHolder extends RecyclerView.ViewHolder {

        TextView name, temperature, tempRange, status, date;

        public CityViewHolder(@NonNull View view) {
            super(view);

            name = view.findViewById(R.id.city_name);
            temperature = view.findViewById(R.id.temperature);
            tempRange = view.findViewById(R.id.temp_range);
            status = view.findViewById(R.id.status);
            date = view.findViewById(R.id.date);
        }
    }


}
