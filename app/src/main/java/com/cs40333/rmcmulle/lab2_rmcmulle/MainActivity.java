package com.cs40333.rmcmulle.lab2_rmcmulle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String teams[] = {"Ohio State", "Florida State", "Wake Forest", "Boston College",
                "North Carolina State", "Georgia Tech","North Virginia", "Chicago Sate"};
        String dates[] = {"Feb 11","Feb 14","Feb 18","Feb 26","March 1","March 4",
                "March 7","March 16"};
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this, teams);
        ListView scheduleListView = (ListView) findViewById(R.id.scheduleListView);
        scheduleListView.setAdapter(scheduleAdapter);
    }

}