package com.example.projectapp.Objects;

import android.location.Location;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class Training {


    //lack of training id
    private static int id = 0;
    private int trainingId;
    private double distance;
    private int trainingDuration;
    private double temperature;
    private double speed;
    private LocalDate date;
    private ArrayList<Loc> locations = new ArrayList<>();


    //test adding no db
    public Training(double distance, int trainingDuration, double temperature,
                    double speed, LocalDate date) {
        this.distance = distance;
        this.trainingDuration = trainingDuration;
        this.temperature = temperature;
        this.speed = speed;
        this.date = date;
    }

    //adding to db
    public Training(double distance, int trainingDuration, double temperature,
                    double speed, LocalDate date, ArrayList<Loc> locations) {
        this.distance = distance;
        this.trainingDuration = trainingDuration;
        this.temperature = temperature;
        this.speed = speed;
        this.date = date;
        this.locations = locations;
        this.trainingId = id;
        id++;
    }

    //getting from db
    public Training(int trainingId, double distance, int trainingDuration, double temperature,
                    double speed, LocalDate date) {
        this.trainingId = trainingId;
        this.distance = distance;
        this.trainingDuration = trainingDuration;
        this.temperature = temperature;
        this.speed = speed;
        this.date = date;
        if(id < trainingId){
            id = trainingId + 1;
        }
    }

    public double getDistance() {
        return distance;
    }

    public int getTrainingDuration() {
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

    public int getTrainingId() {
        return trainingId;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Training.id = id;
    }

    public ArrayList<Loc> getLocations() {
        return locations;
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
