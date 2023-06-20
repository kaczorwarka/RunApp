package com.example.projectapp.Services.API;

import androidx.annotation.NonNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIConnection implements Runnable{

    private double temp;
    private final ApiService apiService;
    private String apiKey = "88fc50c7e7e34d85829142219232804";
    private String location;
    private String aqi = "no";

    public APIConnection(double latitude, double longitude) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        location = latitude + "," + longitude;
        apiService = retrofit.create(ApiService.class);
    }
    @Override
    public void run() {
        Call<WeatherForecast> result = apiService.getCurrentWeather(apiKey, location, aqi);

        try{
            Response<WeatherForecast> response = result.execute();
            if(response.isSuccessful()){
                WeatherForecast weatherForecast = response.body();
                if(weatherForecast != null){
                    temp = weatherForecast.getCurrentWeather().getTemperature();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public double getTemp() {
        return temp;
    }
}
