package com.example.weatherapplication.data;

import com.example.weatherapplication.util.Utils;
import com.example.weatherapplication.weather.Place;
import com.example.weatherapplication.weather.Temperature;
import com.example.weatherapplication.weather.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JSONWeatherParser {
    public static Weather getWeather(String data) throws JSONException {
        try {
            JSONObject object = new JSONObject(data);

            JSONObject locObj = object.getJSONObject("location");
            Place place = new Place(locObj.getString("country"), locObj.getString("name"));

            JSONObject curObj = object.getJSONObject("current");
            JSONObject condObj = curObj.getJSONObject("condition");
            JSONObject fcastObj = object.getJSONObject("forecast");
            JSONObject dayObj = fcastObj.getJSONArray("forecastday").getJSONObject(0).getJSONObject("day");
            Temperature temperature = new Temperature((float) dayObj.getDouble("mintemp_c"),
                    (float) dayObj.getDouble("maxtemp_c"), (float) curObj.getDouble("temp_c"));

            JSONArray castArr = fcastObj.getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour");

            ArrayList<Integer> castTemp = new ArrayList<>();
            ArrayList<String> castTime = new ArrayList<>();
            ArrayList<Integer> castIcon = new ArrayList<>();
            for (int i=0; i<castArr.length(); i++)
            {
                castTemp.add((int) Math.ceil(castArr.getJSONObject(i).getDouble("temp_c")));
                castTime.add(castArr.getJSONObject(i).getString("time").substring(10));
                castIcon.add(castArr.getJSONObject(i).getJSONObject("condition").getInt("code"));
            }

            Weather weather = new Weather(place, condObj.getString("text"), temperature, condObj.getInt("code"),
                    castTemp, castTime, castIcon);

            return weather;

        } catch (Exception e){}

        return null;
    }
}
