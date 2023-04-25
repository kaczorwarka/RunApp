package com.example.projectapp.Objects;

import java.time.Duration;
import java.time.LocalDate;

public class Training {


    //lack of training id
    private double distance;
    private Duration trainingDuration;
    private double temperature;
    private double speed;
    private LocalDate date;

    public Training(double distance, Duration trainingDuration, double temperature, double speed, LocalDate date) {
        this.distance = distance;
        this.trainingDuration = trainingDuration;
        this.temperature = temperature;
        this.speed = speed;
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public Duration getTrainingDuration() {
        return trainingDuration;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getSpeed() {
        return speed;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Training{" +
                "distance=" + distance +
                ", trainingDuration=" + trainingDuration +
                ", temperature=" + temperature +
                ", speed=" + speed +
                ", date=" + date +
                '}';
    }
}
