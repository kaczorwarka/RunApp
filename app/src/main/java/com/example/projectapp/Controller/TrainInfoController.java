package com.example.projectapp.Controller;

import android.opengl.Visibility;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapp.MainActivity;
import com.example.projectapp.Objects.Training;
import com.example.projectapp.R;
import com.example.projectapp.Services.SqlService;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Locale;


public class TrainInfoController extends Fragment {

    private TextView distance;
    private TextView speed;
    private TextView time;
    private TextView temp;
    private TextView date;
    private Button delete;
    private Training training;
    private MapView map;

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

        map = view.findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(16.0);
        GeoPoint startPoint = new GeoPoint(52.17686603, 21.0544879);
        mapController.setCenter(startPoint);


        distance = view.findViewById(R.id.distance_text);
        speed = view.findViewById(R.id.speed_text);
        time = view.findViewById(R.id.time_text);
        temp = view.findViewById(R.id.temp_text);
        date = view.findViewById(R.id.date_text);
        delete = view.findViewById(R.id.delete_button);

        delete.setOnClickListener(view1 -> deleteRecord());

        String distanceS = training.getDistance() + " km";
        int duration = training.getTrainingDuration();
        int hours = duration / 3600;
        int minutes = (duration % 3600) / 60;
        int secs = duration % 60;
        String timeS = String
                .format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

        double speedD = training.getSpeed();
        int speedMin = (int)speedD / 60;
        int speedSek = (int)speedD % 60;
        String speedS = String
                .format(Locale.getDefault(), "%d:%02d", speedMin, speedSek);
        String tempS = training.getTemperature() + " C";
        String dateS = training.getDate().toString();


        distance.setText(distanceS);
        speed.setText(speedS);
        time.setText(timeS);
        temp.setText(tempS);
        date.setText(dateS);

        return view;
    }

    private void deleteRecord(){
        SqlService sqlService = new SqlService(getContext());
        int answer[] = sqlService.deleteTraining(training);
        if(answer[0] < 1 && answer[1] < 1){
            Toast.makeText(getContext(), "Both tables didn't delete", Toast.LENGTH_SHORT).show();
        }else if(answer[0] < 1){
            Toast.makeText(getContext(), "Location table didn't delete", Toast.LENGTH_SHORT).show();
        }else if(answer[1] < 1){
            Toast.makeText(getContext(), "Training table didn't delete", Toast.LENGTH_SHORT).show();
        }else{
            ((MainActivity)getActivity()).finish();
            ((MainActivity)getActivity()).startActivity(((MainActivity)getActivity()).getIntent());
        }
    }
}