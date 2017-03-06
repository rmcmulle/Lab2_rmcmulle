package com.cs40333.rmcmulle.lab2_rmcmulle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declare List of Teams
        final ArrayList<Team> team_info = new ArrayList<Team>();

        // Read csv and get teams
        MyCsvFileReader reader = new MyCsvFileReader(getApplicationContext());
        ArrayList<String[]> all_teams = reader.readCsvFile(R.raw.schedule);

        // Convert csv to ArrayList<Team>
        for (int i = 0 ; i < all_teams.size() ; i++) {
            // declare new team with info from csv
            Team temp = new Team(all_teams.get(i)[0],all_teams.get(i)[1],all_teams.get(i)[2],all_teams.get(i)[3],
                    all_teams.get(i)[4],all_teams.get(i)[5],all_teams.get(i)[6],all_teams.get(i)[7],all_teams.get(i)[8],all_teams.get(i)[9]);

            // add team to list
            team_info.add(temp);
        }

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this, team_info);
        ListView scheduleListView = (ListView) findViewById(R.id.scheduleListView);
        scheduleListView.setAdapter(scheduleAdapter);

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create the intent to start DetailActivity
                Intent intent = new Intent (MainActivity.this, DetailActivity.class);

                //create a bundle object using the following:
                intent.putExtra("team", team_info.get(position)); // where al is your ArrayList holding team information.

                //start the activity using the intent with the bundle you just created.
                startActivity(intent);
            }
        };

        // attach listeners to each item in ListView
        scheduleListView.setOnItemClickListener (clickListener);
    }

}