package com.example.weatherapplication.adapter.fragments;

import android.content.Context;
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
import com.example.weatherapplication.adapter.CityAdapter;
import com.example.weatherapplication.functionality.DateFunctions;

public class PageFragment extends Fragment {
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStated) {
        TextView tv = container.findViewById(R.id.city_name);
        tv.setText("Alaska");
        ViewGroup cityItems = (ViewGroup) inflater.inflate(R.layout.city_item, container, false);
        return cityItems;
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
