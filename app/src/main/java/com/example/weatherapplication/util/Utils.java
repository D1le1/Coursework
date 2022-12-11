package com.example.weatherapplication.util;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    public static final String BASE_URL = "https://api.weatherapi.com/v1/forecast.json?aqi=no&days=1&q=";
//    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static final String API_KEY = "&key=f41f62e75edf49d9b24193133221112";
//    public static final String API_KEY = "&appid=ac5f13cd5d40ed19ec73979d1ef6932b";

    public static JSONObject getObject(String tagName, JSONObject object) throws JSONException
    {
        JSONObject jObj = object.getJSONObject(tagName);
        return jObj;
    }

    public static String getString(String tagName, JSONObject object) throws JSONException
    {
        return object.getString(tagName);
    }

    public static float getFloat(String tagName, JSONObject object) throws JSONException
    {
        return (float) object.getDouble(tagName);
    }

    public static int getInt(String tagName, JSONObject object) throws JSONException
    {
        return object.getInt(tagName);
    }
}
