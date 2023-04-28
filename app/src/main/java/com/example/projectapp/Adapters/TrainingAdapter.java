package com.example.projectapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.Objects.Training;
import com.example.projectapp.R;

import org.greenrobot.eventbus.EventBus;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Locale;

public class TrainingAdapter extends RecyclerView.Adapter<ViewHolder> {



    //do wymiany na bazę danych
    private ArrayList<Training> trainings = new ArrayList<>();

    public TrainingAdapter(Context context) {

    }

    public void setTrainings(ArrayList<Training> trainings) {
        this.trainings = trainings;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trainings_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String distance = trainings.get(position).getDistance() + " km";

        int duration = trainings.get(position).getTrainingDuration();
        int hours = duration / 3600;
        int minutes = (duration % 3600) / 60;
        int secs = duration % 60;
        String time = String
                .format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

        double speedD = trainings.get(position).getSpeed();
        int speedMin = (int)speedD / 60;
        int speedSek = (int)speedD % 60;
        String speed = String
                .format(Locale.getDefault(), "%d:%02d", speedMin, speedSek);
        
        String temp = trainings.get(position).getTemperature() + " C";
        String date = trainings.get(position).getDate().toString();


        holder.getDistance().setText(distance);
        holder.getSpeed().setText(speed);
        holder.getTime().setText(time);
        holder.getTemp().setText(temp);
        holder.getDate().setText(date);
        holder.getParent().setOnClickListener(view -> EventBus.getDefault().post(trainings.get(position)));
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }




}
