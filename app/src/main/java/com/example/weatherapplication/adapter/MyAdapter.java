package com.example.weatherapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
    private SwipeRefreshLayout refresher;

    public MyAdapter(Context context, ArrayList<City> cities, SwipeRefreshLayout refresher) {
        this.context = context;
        this.cities = cities;
        this.refresher = refresher;

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
        RecyclerView castRecycler;

        name = view.findViewById(R.id.city_name);
        temperature = view.findViewById(R.id.temperature);
        tempRange = view.findViewById(R.id.temp_range);
        status = view.findViewById(R.id.status);
        date = view.findViewById(R.id.date);
        icon = view.findViewById(R.id.weather_icon);
        castRecycler = view.findViewById(R.id.cast);

        name.setText(cities.get(position).getName() + ", " + cities.get(position).getCountry());
        temperature.setText(String.valueOf(cities.get(position).getDegrees()));
        tempRange.setText(cities.get(position).getMaxDegrees() + "°C / " + cities.get(position).getMinDegrees() + "°C");
        status.setText(cities.get(position).getStatus());
        date.setText(DateFunctions.getDate());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        castRecycler.setLayoutManager(layoutManager);

        CastRecyclerAdapter adapter = new CastRecyclerAdapter(view.getContext(), cities.get(position).getCastTime(),
                cities.get(position).getCastTemp(), cities.get(position).getCastIcon());
        castRecycler.setAdapter(adapter);
        castRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(refresher != null && !refresher.isRefreshing())
                {
                    refresher.setEnabled(newState == RecyclerView.SCROLL_STATE_IDLE);
                }
            }
        });

        int cityIcon = cities.get(position).getIcon();

        icon.setImageResource(getIcon(cityIcon));

        container.addView(view, 0);

        return view;
    }

    private static boolean isHere(int cityIcon, int... params)
    {
        for (int param : params)
        {
            if(cityIcon == param)
                return true;
        }
        return false;
    }

    public static int getIcon(int cityIcon)
    {
        int iconValue = 0;
        if(isHere(cityIcon, 1000))
        {
            iconValue = R.drawable.cs;
        }
        else if(isHere(cityIcon, 1003))
        {
            iconValue = R.drawable.fc;
        }
        else if(isHere(cityIcon, 1006))
        {
            iconValue = R.drawable.sc;
        }
        else if(isHere(cityIcon, 1009))
        {
            iconValue = R.drawable.bc;
        }
        else if(isHere(cityIcon, 1030, 1135, 1147))
        {
            iconValue = R.drawable.m;
        }
        else if(isHere(cityIcon, 1063, 1069, 1072, 1150, 1153, 1168, 1171, 1180, 1183, 1198, 1204))
        {
            iconValue = R.drawable.r;
        }
        else if(isHere(cityIcon, 1186, 1189, 1192, 1195, 1201, 1207, 1240, 1243, 1246, 1249, 1252))
        {
            iconValue = R.drawable.sr;
        }
        else if(isHere(cityIcon, 1087, 1273, 1276, 1279, 1282))
        {
            iconValue = R.drawable.thu;
        }
        else
        {
            iconValue = R.drawable.sn;
        }
        return iconValue;
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
