package com.example.projectapp.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.R;

public class ViewHolder extends RecyclerView.ViewHolder{
    private final TextView distance;
    private final TextView speed;
    private final TextView time;
    private final TextView temp;
    private final TextView date;
    private CardView parent;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        distance = itemView.findViewById(R.id.distance);
        speed = itemView.findViewById(R.id.speed);
        time = itemView.findViewById(R.id.time);
        temp = itemView.findViewById(R.id.temp);
        date = itemView.findViewById(R.id.date);
        parent = itemView.findViewById(R.id.parent);
    }
    public TextView getDistance() {
        return distance;
    }

    public TextView getSpeed() {
        return speed;
    }

    public TextView getTime() {
        return time;
    }

    public TextView getTemp() {
        return temp;
    }

    public TextView getDate() {
        return date;
    }

    public CardView getParent() {
        return parent;
    }
}
