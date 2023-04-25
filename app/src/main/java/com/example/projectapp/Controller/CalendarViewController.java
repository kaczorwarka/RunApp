package com.example.projectapp.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.projectapp.Adapters.CalendarAdapter;
import com.example.projectapp.Objects.Training;
import com.example.projectapp.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;


public class CalendarViewController extends Fragment implements View.OnClickListener {

    private LocalDate date;
    private TextView title;
    private CalendarAdapter adapter;
    private final ArrayList<Training> trainings;

    public CalendarViewController(ArrayList<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar_view_controller, container, false);

        ImageButton back = view.findViewById(R.id.back);
        back.setOnClickListener(this);
        ImageButton forward = view.findViewById(R.id.forward);
        forward.setOnClickListener(this);

        RecyclerView calendarDays = view.findViewById(R.id.calendar_days);
        title = view.findViewById(R.id.month);

        date = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);

        String month = date.getMonth().toString() + " " + date.getYear();
        title.setText(month);

        adapter = new CalendarAdapter(getContext());

        adapter.setCalendar(monthInitializer());

        calendarDays.setAdapter(adapter);
        calendarDays.setLayoutManager(new GridLayoutManager(getContext(),7));

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case (R.id.back):
                if (date.getMonth().getValue() - 1 == 0){
                    date = LocalDate.of(date.getYear() - 1, 12, 1);
                }else{
                    date = LocalDate.of(date.getYear(), date.getMonth().getValue() - 1, 1);
                }
                break;
            case(R.id.forward):
                if (date.getMonth().getValue() + 1 == 13){
                    date = LocalDate.of(date.getYear() + 1, 1, 1);
                }else{
                    date = LocalDate.of(date.getYear(), date.getMonth().getValue() + 1, 1);
                }
                break;
            default:
                break;
        }
        String month = date.getMonth().toString() + " " + date.getYear();
        title.setText(month);

        adapter.setCalendar(monthInitializer());
        adapter.notifyDataSetChanged();
    }

    private ArrayList<ArrayList<String>> monthInitializer(){
        int firstDay = date.getDayOfWeek().getValue();
        boolean inSearch;

        ArrayList<String> days = new ArrayList<>();
        ArrayList<String> trainingsForCalendar = new ArrayList<>();
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        for(int day = 1; day - firstDay + 1 <= date.lengthOfMonth(); day++){
            if(day < firstDay){
                days.add("");
                trainingsForCalendar.add("");
            }
            else{
                days.add(String.valueOf(day - firstDay+1));
                inSearch = true;
                for (Training training : trainings) {
                    LocalDate trainingDate =  training.getDate();
                    if (trainingDate.getYear() == date.getYear()
                            && trainingDate.getMonth() == date.getMonth()
                            && trainingDate.getDayOfMonth() == day
                            && inSearch){
                        trainingsForCalendar.add(String.valueOf(training.getDistance()));
                        inSearch = false;
                    }
                }
                if(inSearch){
                    trainingsForCalendar.add("");
                }
            }
        }
        result.add(days);
        result.add(trainingsForCalendar);
        return result;
    }

}