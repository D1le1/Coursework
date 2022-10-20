package com.example.weatherapplication.data;

import com.example.weatherapplication.util.Utils;
import com.example.weatherapplication.weather.Place;
import com.example.weatherapplication.weather.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWeatherParser {
    public static Weather getWeather(String data) throws JSONException {
        try {
            JSONObject object = new JSONObject(data);

            JSONObject sysObj = Utils.getObject("sys", object);
            Place place = new Place(Utils.getString("country", sysObj), Utils.getString("name", object), Utils.getInt("dt", object));

            JSONArray jsonArray = object.getJSONArray("weather");
            JSONObject weatherObj = jsonArray.getJSONObject(0);
            Weather weather = new Weather(place, Utils.getString("icon", weatherObj));

            return weather;

        }
        catch (JSONException e){}

        return null;
    }
}
