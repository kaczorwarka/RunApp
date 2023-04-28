package com.example.projectapp.Services;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.projectapp.Objects.Loc;
import com.example.projectapp.Objects.Training;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SqlService extends SQLiteOpenHelper {

    public static final String training_table = "training_table";
    public static final String location_table = "location_table";
    public static final String training_id = "training_id";
    public static final String distance = "distance";
    public static final String time = "time";
    public static final String temp = "temperature";
    public static final String speed = "speed";
    public static final String date = "date";
    public static final String latitude = "latitude";
    public static final String longitude = "longitude";
    public static final String number = "number";

    private static final String DATABASE_NAME = "Runs.db";
    private static final int DATABASE_VERSION = 1;
    public SqlService(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTrainingTable = "CREATE TABLE " +
                training_table + " (" +
                training_id + " INTAGER PRIMARY KEY, " +
                distance + " REAL, " +
                time + " INTAGER, " +
                temp + " REAL, " +
                speed + " REAL, " +
                date + " TEXT)";
        String createLocationTable = "CREATE TABLE " +
                location_table + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                latitude + " REAL, " +
                longitude + " REAL, " +
                number + " INTAGER, " +
                training_id + " INTAGER)";

        sqLiteDatabase.execSQL(createTrainingTable);
        sqLiteDatabase.execSQL(createLocationTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addTraining(Training training){

        ArrayList<Long> answers = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvTraining = new ContentValues();

        cvTraining.put(training_id,training.getTrainingId());
        cvTraining.put(time,training.getTrainingDuration());
        cvTraining.put(distance, training.getDistance());
        cvTraining.put(temp, training.getTemperature());
        cvTraining.put(speed, training.getSpeed());
        cvTraining.put(date, training.getDate().toString());

        long insert =  db.insert(training_table, null, cvTraining);
        answers.add(insert);
        for (Loc location : training.getLocations()) {
            ContentValues cvLocation = new ContentValues();
            cvLocation.put(latitude, location.getLatitude());
            cvLocation.put(longitude, location.getLongitude());
            cvLocation.put(number, location.getNumber());
            cvLocation.put(training_id, location.getTrainingId());
            insert = db.insert(location_table, null, cvLocation);
            answers.add(insert);
        }
        for(long answer : answers){
            if (answer == -1){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Training> getAll(){
        ArrayList<Training> trainings = new ArrayList<>();

        String selectAll = "SELECT * FROM "+ training_table +
                " ORDER BY " + training_id + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAll, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                double distance = cursor.getDouble(1);
                int time = cursor.getInt(2);
                double temp = cursor.getDouble(3);
                double speed = cursor.getDouble(4);
                LocalDate date = LocalDate.parse(cursor.getString(5));
                trainings.add(new Training(id, distance, time, temp, speed, date));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return trainings;
    }

    public int setTrainingId(){
        String getLastId = "SELECT MAX(" + training_id + ") FROM "+ training_table;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getLastId, null);
        if(cursor.moveToFirst()){
            int answer = cursor.getInt(0);
            cursor.close();
            return answer;
        }else{
            cursor.close();
            return 0;
        }
    }


    public int[] deleteTraining(Training training){

        int[] answer = {-1, -1};

        int trainingID;
        try{
            trainingID = training.getTrainingId();
        }catch (NullPointerException e){
            return answer;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = training_id + "=?";
        String[] whereArgs = new String[1];
        whereArgs[0] = trainingID + "";
        answer[0] = db.delete(location_table,whereClause,whereArgs);
        answer[1] = db.delete(training_table,whereClause,whereArgs);

        return answer;
    }
}
