package com.example.weatherapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.R;
import com.example.weatherapplication.classes.City;
import com.example.weatherapplication.functionality.DateFunctions;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CityViewHolder> {

    private Context context;
    private ArrayList<City> cities;

    public RecyclerAdapter(Context context, ArrayList<City> cities) {
        this.context = context;
        this.cities = cities;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cityItems = LayoutInflater.from(context).inflate(R.layout.city_item, parent, false);
        return new CityViewHolder(cityItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.name.setText(cities.get(position).getName() + ", " + cities.get(position).getCountry());
        holder.temperature.setText(cities.get(position).getDegrees() + "°C");
        holder.tempRange.setText(cities.get(position).getMinDegrees() + "°C / " + cities.get(position).getMaxDegrees() + "°C");
        holder.status.setText(cities.get(position).getStatus());
        holder.date.setText(DateFunctions.getDate());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public static final class CityViewHolder extends RecyclerView.ViewHolder {

        TextView name, temperature, tempRange, status, date;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.city_name);
            temperature = itemView.findViewById(R.id.temperature);
            tempRange = itemView.findViewById(R.id.temp_range);
            status = itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.date);
        }
    }
}