package com.example.projectapp.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectapp.Adapters.TrainingAdapter;
import com.example.projectapp.Objects.Training;
import com.example.projectapp.R;
import com.example.projectapp.Services.SqlService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ListViewController extends Fragment {

    private RecyclerView trainingListRec;

    private ArrayList<Training> trainings;

    public ListViewController(ArrayList<Training> trainings) {

        this.trainings = trainings;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_list_view_controller, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        trainingListRec = view.findViewById(R.id.trainingListRec);

        TrainingAdapter adapter = new TrainingAdapter(getContext());

        adapter.setTrainings(trainings);

        trainingListRec.setAdapter(adapter);
        trainingListRec.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Subscribe
    public void onSelect(Training training) {
        replaceFragment(new TrainInfoController(training));
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}