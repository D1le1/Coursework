package com.example.weatherapplication.data;

import com.example.weatherapplication.util.Utils;
import com.example.weatherapplication.weather.Place;
import com.example.weatherapplication.weather.Temperature;
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

            JSONObject mainObj = Utils.getObject("main", object);
            Temperature temperature = new Temperature(Utils.getFloat("temp_min", mainObj), Utils.getFloat("temp_max", mainObj), Utils.getFloat("temp", mainObj));

            JSONArray jsonArray = object.getJSONArray("weather");
            JSONObject weatherObj = jsonArray.getJSONObject(0);
            Weather weather = new Weather(place, Utils.getString("main", weatherObj), temperature);

            return weather;

        }
        catch (JSONException e){}
        catch (Exception e)
        {

        }

        return null;
    }
}
