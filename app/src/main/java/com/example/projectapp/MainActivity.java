package com.example.projectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.projectapp.Controller.CalendarViewController;
import com.example.projectapp.Controller.ListViewController;
import com.example.projectapp.Objects.Training;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private Toolbar topAppBar;

    private ArrayList<Training> trainings = new ArrayList<>();

    private FloatingActionButton runTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        Duration duration;
        LocalDate date;

        //ArrayList<Training> trainings = new ArrayList<>();

        duration = Duration.between(LocalTime.of(9,30), LocalTime.of(10,0));
        date = LocalDate.of(2023,2,15);
        trainings.add(new Training(22.1, duration, 22, 4, date));

        duration = Duration.between(LocalTime.of(8,0), LocalTime.of(10,0));
        date = LocalDate.of(2023,4,23);
        trainings.add(new Training(15, duration, 10, 5.3, date));

        //setting appbar
        topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);

        replaceFragment(new ListViewController(trainings));

        runTraining = findViewById(R.id.run_training);
        runTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRunTraining();
            }
        });

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

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void openRunTraining(){
        Intent intent = new Intent(this, RunTraining.class);
        startActivity(intent);

    }

}