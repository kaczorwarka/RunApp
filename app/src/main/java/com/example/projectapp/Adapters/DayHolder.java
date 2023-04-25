package com.example.projectapp.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.R;

public class DayHolder extends RecyclerView.ViewHolder {

    private final TextView day;
    private final TextView training;

    public DayHolder(@NonNull View itemView) {
        super(itemView);

        day=itemView.findViewById(R.id.day);
        training = itemView.findViewById(R.id.training);

    }

    public TextView getTraining() {
        return training;
    }
    public TextView getDay() {
        return day;
    }

}
