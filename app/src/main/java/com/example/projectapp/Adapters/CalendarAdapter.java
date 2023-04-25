package com.example.projectapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.R;

import java.util.ArrayList;


public class CalendarAdapter extends RecyclerView.Adapter<DayHolder>{

    private ArrayList<String> days;
    private ArrayList<String> trainings;

    public CalendarAdapter(Context context) {

    }


    public void setCalendar(ArrayList<ArrayList<String>> calendar) {
        this.days = calendar.get(0);
        trainings = calendar.get(1);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_item, parent, false);
        return new DayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayHolder holder, int position) {
        String day = days.get(position);
        String training = trainings.get(position).equals("") ? "" : trainings.get(position) + " km";
        holder.getDay().setText(day);
        holder.getTraining().setText(training);
    }

    @Override
    public int getItemCount() {
        return days.size();
    }
}
