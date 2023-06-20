package com.example.projectapp.Services.API;

import com.google.gson.annotations.SerializedName;

public class CurrentWeather {
    @SerializedName("temp_c")
    private double temperature;

    public double getTemperature() {
        return temperature;
    }
}
