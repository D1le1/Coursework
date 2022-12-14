package com.example.weatherapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.R;

import java.util.ArrayList;

public class CastRecyclerAdapter extends RecyclerView.Adapter<CastRecyclerAdapter.CityViewHolder> {

    private Context context;
    private ArrayList<String> castTime;
    private ArrayList<Integer> castTemp;
    private ArrayList<Integer> castIcon;

    public CastRecyclerAdapter(Context context, ArrayList<String> castTime, ArrayList<Integer> castTemp, ArrayList<Integer> castIcon) {
        this.context = context;
        this.castIcon = castIcon;
        this.castTemp = castTemp;
        this.castTime = castTime;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cityItems = LayoutInflater.from(context).inflate(R.layout.cast_item, parent, false);
        return new CityViewHolder(cityItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.time.setText(castTime.get(position));
        holder.temperature.setText(castTemp.get(position).toString() + "°C");
        holder.icon.setImageResource(ViewPagerAdapter.getIcon(castIcon.get(position)));
    }

    @Override
    public int getItemCount() {
        return castTime.size();
    }

    public static final class CityViewHolder extends RecyclerView.ViewHolder {

        TextView time, temperature;
        ImageView icon;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time);
            temperature = itemView.findViewById(R.id.temp);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}