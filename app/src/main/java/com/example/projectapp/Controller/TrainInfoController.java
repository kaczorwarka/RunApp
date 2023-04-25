package com.example.projectapp.Controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectapp.Objects.Training;
import com.example.projectapp.R;

import java.time.Duration;


public class TrainInfoController extends Fragment {

    private TextView distance;
    private TextView speed;
    private TextView time;
    private TextView temp;
    private TextView date;


    private Training training;

    public TrainInfoController(Training training) {
        // Required empty public constructor
        this.training = training;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_train_info_controller, container, false);

        distance = view.findViewById(R.id.distance_text);
        speed = view.findViewById(R.id.speed_text);
        time = view.findViewById(R.id.time_text);
        temp = view.findViewById(R.id.temp_text);
        date = view.findViewById(R.id.date_text);

        String distanceS = training.getDistance() + " km";
        Duration duration = training.getTrainingDuration();
        String timeS = duration.toHours() + ":";
        long minutes = duration.toMinutes() - 60*duration.toHours();
        timeS += (minutes < 10 ? "0"+minutes : minutes) + " ";
        String speedS = training.getSpeed() + " min/km";
        String tempS = training.getTemperature() + " C";
        String dateS = training.getDate().toString();


        distance.setText(distanceS);
        speed.setText(speedS);
        time.setText(timeS);
        temp.setText(tempS);
        date.setText(dateS);

        return view;
    }
}