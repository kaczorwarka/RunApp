package com.example.projectapp.Services.API;

import com.google.gson.annotations.SerializedName;

public class WeatherForecast {
    @SerializedName("current")
    private CurrentWeather currentWeather;

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }
}

