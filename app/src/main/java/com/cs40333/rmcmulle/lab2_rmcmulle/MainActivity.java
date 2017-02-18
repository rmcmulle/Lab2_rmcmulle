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
        String imgs[] = {"ohio_state","florida_state","wake_forrest","boston_college",          // 0
                        "north_carolina_state","georgia_tech","virginia","chicago_state"};
        String teams[] = {"Ohio State", "Florida State", "Wake Forest", "Boston College",       // 1
                "North Carolina State", "Georgia Tech","North Virginia", "Chicago State"};
        String dates[] = {"Feb 11","Feb 14","Feb 18","Feb 26","March 1","March 4",              // 2
                "March 7","March 16"};
        String days[] = {"Saturday","Tuesday","Saturday","Friday","Monday","Thursday","Sunday", // 3
                "Wednesday"};
        String times[] = {"6:00 PM", "8:00 PM", "7:00 PM", "8:00 PM", "6:00 PM", "8:00 PM",     // 4
                "7:00 PM", "8:00 PM"};
        String names[] = {"Buckeyes","Seminoles","Demon Deacons","Eagles","Wolfpack",       // 5
                "Yellow Jackets","The Virginians","Cougars"};
        String records[] = {"(21-5)","(24-1)","(18-8)","(19-7)","(20-6)","(15-9)","(14-8)",     // 6
                "(20-6)"};
        String scores[] = {"80 - 78","72 - 84", "89 - 101", "68 - 75", "80 - 79", "67 - 80",    // 7
                "70 - 89","59 - 68"};
        String score_times[] = {"final","final","final","final","final","final","final",        // 8
                "final"};
        String locations[] = {"Purcell Pavillion at the Joyce Center, Notre Dame, Indiana",     // 9
                "Purcell Pavillion at the Joyce Center, Notre Dame, Indiana",
                "At Wake Forrest","Purcell Pavillion at the Joyce Center, Notre Dame, Indiana",
                "At North Carolina State",
                "Purcell Pavillion at the Joyce Center, Notre Dame, Indiana",
                "At Virginia",
                "Purcell Pavillion at the Joyce Center, Notre Dame, Indiana"};

        final ArrayList<String[]> team_info = new ArrayList<String[]>();
        for (int i = 0 ; i < imgs.length ; i++ ) { // place into string array
            String[] a = {imgs[i],teams[i],dates[i],days[i],times[i],names[i],records[i],
            scores[i],score_times[i],locations[i]};
            team_info.add(a);
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