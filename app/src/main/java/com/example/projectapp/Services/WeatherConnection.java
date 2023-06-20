package com.example.projectapp.Services;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherConnection{

    private double latitude;
    private double longitude;
    private double temp;
    private HttpURLConnection urlConnection;
    private JSONObject weather;
    private String fragment = "http://api.weatherapi.com/v1/current.json?key=88fc50c7e7e34d85829142219232804&q=Warsaw&aqi=no";

    public WeatherConnection(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }




    private String setLink() {
        String location = latitude + "," + longitude;
        //return fragment + "&q=" + location + "&aqi=no";
        return fragment;
    }

    private void getConnection(String link){
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            URL url = new URL("https://api.weatherapi.com/v1/current.json?key=88fc50c7e7e34d85829142219232804&q=Warsaw&aqi=no");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");

            int status = urlConnection.getResponseCode();
            if(status < 300){
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
                weather = new JSONObject(responseContent.toString());
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        } finally {
            urlConnection.disconnect();
        }


    }


    public void run() {
        String link = setLink();
        getConnection("http://api.weatherapi.com/v1/current.json?key=88fc50c7e7e34d85829142219232804&q=Warsaw&aqi=no");
        JSONObject current = null;
        try {
            current = weather.getJSONObject("current");
            temp =  current.getDouble("temp_c");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public double getTemp() {
        return temp;
    }
}
