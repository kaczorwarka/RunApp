package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;

import com.example.projectapp.Controller.CalendarViewController;
import com.example.projectapp.Controller.ListViewController;
import com.example.projectapp.Objects.Training;
import com.example.projectapp.Services.SqlService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.config.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import android.Manifest;


public class MainActivity extends AppCompatActivity {

    private final int LOCATION_PERMISSION_CODE = 1;
    private final int INTERNET_PERMISSION_CODE = 2;

    int permissionCounter = 1;
    private Toolbar topAppBar;

    private ArrayList<Training> trainings = new ArrayList<>();

    private FloatingActionButton runTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        //setting appbar
        topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);

        SqlService sqlService = new SqlService(this);
        Training.setId(sqlService.setTrainingId()+1);
        runTraining = findViewById(R.id.run_training);
        setUpPermissions();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        topAppBar.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case (R.id.list):
                    replaceFragment(new ListViewController(trainings));
                    return true;
                case(R.id.calendar):
                    replaceFragment(new CalendarViewController(trainings));
                    return true;
                default:
                    return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalDate date;
        trainings.clear();
        SqlService sqlService = new SqlService(this);
        trainings.addAll(sqlService.getAll());
        date = LocalDate.of(2023,2,15);
        trainings.add(new Training(22.1, 5000, 22, 4, date));

        date = LocalDate.of(2023,4,23);
        trainings.add(new Training(15, 3500, 10, 5.3, date));

        replaceFragment(new ListViewController(trainings));

    }

    public void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void openRunTraining(){

        if(isInternetAvailable(getApplicationContext()) && isLocationEnabled(getApplicationContext())){
            Intent intent = new Intent(this, RunTraining.class);
            startActivity(intent);
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("No Internet or Location")
                    .setMessage("Make sure that you have internet connection and localization on")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }
    }

    public boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    public boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return locationManager.isLocationEnabled();
            }
        }
        return false;
    }

    private void setUpPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_CODE);
        }else{
            runTraining.setOnClickListener(view -> openRunTraining());
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == LOCATION_PERMISSION_CODE || requestCode == INTERNET_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                runTraining.setOnClickListener(view -> openRunTraining());
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}