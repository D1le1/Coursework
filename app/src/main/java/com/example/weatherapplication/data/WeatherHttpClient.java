package com.example.weatherapplication.data;

import android.util.Log;
import android.widget.Toast;

import com.example.weatherapplication.MainActivity;
import com.example.weatherapplication.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class WeatherHttpClient {
//    public String getWeatherData(String place)
//    {
//        HttpURLConnection connection = null;
//        InputStream inputStream = null;
//
//        try {
//            connection = (HttpURLConnection) (new URL(Utils.BASE_URL + place + Utils.API_KEY)).openConnection();
//            connection.setRequestMethod("GET");
//            connection.setDoInput(true);
//            connection.setDoInput(true);
//            connection.connect();
//            StringBuffer stringBuffer = new StringBuffer();
//            inputStream = connection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String line = null;
//
//            while ((line = bufferedReader.readLine()) != null)
//            {
//                stringBuffer.append(line + "\r\n");
//            }
//
//            inputStream.close();
//            connection.disconnect();
//
//            return stringBuffer.toString();
//        }catch (IOException e){}
//
//        return null;
//    }

    public String readAll(Reader rd) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1){
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public String getWeatherData(String place) throws IOException, JSONException {
        InputStream inputStream = new URL(Utils.BASE_URL + place + Utils.API_KEY).openStream();
        try{
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json.toString();
        }finally {
            inputStream.close();
        }
    }
}
