package com.example.projectapp.Services.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/v1/current.json")
    Call<WeatherForecast> getCurrentWeather(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("aqi") String aqi
    );
}
