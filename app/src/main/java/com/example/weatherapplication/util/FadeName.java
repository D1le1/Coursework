package com.example.weatherapplication.util;

import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.weatherapplication.R;

public class FadeName implements ViewPager.PageTransformer {

    public void transformPage(View view, float position) {
        TextView city = view.findViewById(R.id.city_name);

        city.setTranslationX(view.getWidth() * -position);

        if(position <= -1.0F || position >= 1.0F) {
            city.setAlpha(0.0F);
            view.setAlpha(0.0F);
        } else if( position == 0.0F ) {
            city.setAlpha(1.0F);
            view.setAlpha(1.0F);
        } else {
            // position is between -1.0F & 0.0F OR 0.0F & 1.0F
            city.setAlpha(1.0F - Math.abs(position));
            view.setAlpha(1.0F - Math.abs(position));
        }
    }
}
