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
        Duration duration = trainings.get(position).getTrainingDuration();
        String time = duration.toHours() + ":";
        long minutes = duration.toMinutes() - 60*duration.toHours();
        time += (minutes < 10 ? "0"+minutes : minutes) + " ";
        String speed = trainings.get(position).getSpeed() + " min/km";
        String temp = trainings.get(position).getTemperature() + " C";
        String date = trainings.get(position).getDate().toString();


        holder.getDistance().setText(distance);
        holder.getSpeed().setText(speed);
        holder.getTime().setText(time);
        holder.getTemp().setText(temp);
        holder.getDate().setText(date);
        holder.getParent().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(trainings.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }




}
