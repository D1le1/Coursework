package com.example.weatherapplication.functionality;

import android.view.MotionEvent;

import com.example.weatherapplication.classes.City;

import java.util.ArrayList;

public class Gestures {
    public static class Swipe {
        private static float x1, x2;
        private static int minDistance = 150;
        private static int index = 0;

        public static City swipeCities(MotionEvent event, ArrayList<City> cities)
        {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    x1 = event.getX();
                    break;
                case MotionEvent.ACTION_UP:
                    x2 = event.getX();
                    float deltaX = x2 - x1;

                    if (Math.abs(deltaX) > minDistance)
                    {
                        if(x2 > x1)
                        {
                            index++;
                            if (index > cities.size() - 1)
                                index = 0;
                        }
                        else
                        {
                            index--;
                            if (index < 0)
                                index = cities.size() - 1;
                        }
                        return cities.get(index);
                    }
                    break;
            }
            return null;
        }
    }
}
