package com.example.projectapp.Services.API;

import android.content.Context;
import android.net.Uri;

import com.example.projectapp.R;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIConnection implements Runnable{

    private double temp;
    private final ApiService apiService;
    private final String apiKey;
    private final String location;
    private final String aqi;

    public APIConnection(double latitude, double longitude, Context context) {
        String url = context.getString(R.string.base_url);
        aqi = context.getString(R.string.aqi);
        apiKey = context.getString(R.string.api_key);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
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
