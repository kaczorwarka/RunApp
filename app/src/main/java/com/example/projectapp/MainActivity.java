package com.example.projectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.projectapp.Controller.CalendarViewController;
import com.example.projectapp.Controller.ListViewController;


public class MainActivity extends AppCompatActivity {


    private Toolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topAppBar = findViewById(R.id.topAppBar);
        
        setSupportActionBar(topAppBar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        topAppBar.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case (R.id.list):
                    replaceFragment(new ListViewController());
                    return true;
                case(R.id.calendar):
                    replaceFragment(new CalendarViewController());
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

}