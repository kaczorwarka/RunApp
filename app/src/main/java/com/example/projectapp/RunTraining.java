package com.example.projectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.KeyStore;
import java.util.Locale;


public class RunTraining extends AppCompatActivity{
    private TextView latitude;
    private TextView longitude;
    private TextView time;
    private boolean running = false;
    private FloatingActionButton play;
    private FloatingActionButton stop;
    private FusedLocationProviderClient fusedLocationClient;
    int seconds = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.activity_run_training);
        super.onCreate(savedInstanceState);

        time = findViewById(R.id.run_time);
        String timeS = "0:00:00";
        time.setText(timeS);

        play = findViewById(R.id.play_button);
        stop = findViewById(R.id.pause_button);
        stop.setVisibility(View.GONE);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playClicked();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopClicked();
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                        }
                    }
                });

        runTimer();

    }

    private void runTimer() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (running){
                    seconds ++;
                    int hours = seconds / 3600;
                    int minutes = (seconds % 3600) / 60;
                    int secs = seconds % 60;
                    String timeS = String
                            .format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                    time.setText(timeS);
                }
                handler.postDelayed(this,1000);
            }


        });
    }

    private void stopClicked() {
        stop.setVisibility(View.GONE);
        play.setVisibility(View.VISIBLE);
        running = !running;
    }

    private void playClicked() {
        play.setVisibility(View.GONE);
        stop.setVisibility(View.VISIBLE);
        running = !running;

    }


}