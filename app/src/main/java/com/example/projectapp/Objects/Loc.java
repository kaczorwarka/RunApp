package com.example.projectapp.Objects;

public class Loc {
    private int trainingId;
    private double latitude;
    private double longitude;
    private int number;

    public Loc(double latitude, double longitude, int number, int trainingId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.number = number;
        this.trainingId = trainingId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getNumber() {
        return number;
    }

    public int getTrainingId() {
        return trainingId;
    }
}
