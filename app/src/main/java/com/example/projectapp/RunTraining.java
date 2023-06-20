package com.example.projectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.projectapp.Objects.Loc;
import com.example.projectapp.Objects.Training;
import com.example.projectapp.Services.SqlService;
import com.example.projectapp.Services.WeatherConnection;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;


public class RunTraining extends AppCompatActivity {
    private TextView latitude;
    private TextView longitude;
    private TextView time;
    private TextView distanceView;
    private TextView speedView;
    private boolean running = false;
    private FloatingActionButton play;
    private FloatingActionButton stop;
    private Button save;
    private FusedLocationProviderClient fusedLocationClient;
    private int seconds = 0;
    private ArrayList<Loc> loc = new ArrayList<>();
    private int distance = 0;
    private double speed = 0;
    private double temp = 0;
    private ArrayList<Double> speeds = new ArrayList<>();
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private Location currentLocation;
    private final double converter = 111196.672;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_run_training);
        super.onCreate(savedInstanceState);

        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,
                1000).build();


        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);

        time = findViewById(R.id.run_time);
        String timeS = "0:00:00";
        time.setText(timeS);

        distanceView = findViewById(R.id.run_distance);
        String distanceS = "0.000 km";
        distanceView.setText(distanceS);

        speedView = findViewById(R.id.run_speed);
        String speedS = "0:00";
        speedView.setText(speedS);

        play = findViewById(R.id.play_button);
        stop = findViewById(R.id.pause_button);
        save = findViewById(R.id.save_button);
        stop.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        play.setOnClickListener(view -> playClicked());
        stop.setOnClickListener(view -> stopClicked());
        save.setOnClickListener(view -> saveClicked());

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    String latitudeS = String.valueOf(location.getLatitude());
                    String longitudeS = String.valueOf(location.getLongitude());

                    latitude.setText(latitudeS);
                    longitude.setText(longitudeS);
                    currentLocation = location;
                }
            }
        };
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        startLocationUpdates();
        runTimer();

    }

    private void runTimer() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (running) {
                    //seconds++;
                    int hours = seconds / 3600;
                    int minutes = (seconds % 3600) / 60;
                    int secs = seconds % 60;
                    String timeS = String
                            .format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                    time.setText(timeS);

                    if(currentLocation != null){
                        if(seconds == 0){
                            WeatherConnection weatherConnection = new WeatherConnection(
                                    currentLocation.getLatitude(),
                                    currentLocation.getLongitude());
                            Thread thread = new Thread(weatherConnection);
                            thread.start();
                            try {
                                thread.join();
                                temp = weatherConnection.getTemp();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if(seconds % 5 == 0){
                            loc.add(new Loc(currentLocation.getLatitude(),
                                    currentLocation.getLongitude(),
                                    seconds,
                                    Training.getId()));
                            updateSpeed();
                        }
                        seconds++;
                    }
                    int km = distance / 1000;
                    int m = distance % 1000;
                    String distanceS = String
                            .format(Locale.getDefault(), "%d.%03d", km, m);
                    distanceS = distanceS + " km";
                    distanceView.setText(distanceS);

                    int speedMin = (int)speed / 60;
                    int speedSek = (int)speed % 60;
                    String speedS = String
                            .format(Locale.getDefault(), "%d:%02d", speedMin, speedSek);
                    speedView.setText(speedS);
                    speeds.add(speed);
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void updateSpeed() {
        int distancePref = distance;
        updateDistance();
        if (seconds > 2){
            if ((distance - distancePref)/5.0 > 0.5){
                speed = (distance - distancePref)/5.0;
                speed /= 1000;
                speed = Math.pow(speed, -1);
            }else{
                speed = 0;
            }
        }
    }

    private void updateDistance() {
        if (seconds > 2){
            Loc location1 = loc.get(loc.size()-1);
            Loc location2 = loc.get(loc.size()-2);
            double distancePoint = calculateLength(location1, location2);
            distance += distancePoint * converter;
        }
    }

    private double calculateLength(Loc location1, Loc location2) {
        double Y = location1.getLatitude() - location2.getLatitude();
        double X = location1.getLongitude() - location2.getLongitude();
        return Math.sqrt(Y*Y + X*X);
    }

    private void stopClicked() {
        stop.setVisibility(View.GONE);
        play.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        running = !running;
    }

    private void playClicked() {
        play.setVisibility(View.GONE);
        stop.setVisibility(View.VISIBLE);
        //save.setVisibility(View.GONE);
        if (save.getVisibility()==View.VISIBLE){
            save.setVisibility(View.GONE);
        }
        running = !running;

    }

    private void saveClicked(){
        Training training = new Training((double)distance/1000, seconds, temp,
                meanSpeed(), LocalDate.now(), loc);
        SqlService sqlService = new SqlService(RunTraining.this);

        if(sqlService.addTraining(training)){
            finish();
        }else{
            Toast.makeText(this, "Didn't work", Toast.LENGTH_SHORT).show();
        }

    }

    private double meanSpeed(){
        double sum = 0;
        for (double speed: speeds) {
            sum += speed;
        }
        return sum/speeds.size();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
}