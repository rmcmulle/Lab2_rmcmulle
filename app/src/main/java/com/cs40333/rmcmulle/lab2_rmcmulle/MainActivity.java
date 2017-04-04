package com.cs40333.rmcmulle.lab2_rmcmulle;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declare List of Teams
    final ArrayList<Team> team_info = new ArrayList<Team>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create Action Bar
        Toolbar myToolBar = (Toolbar) findViewById(R.id.toolbar);
        myToolBar.setTitle("ND Athletics");
        setSupportActionBar(myToolBar);

//        // Declare List of Teams
//        final ArrayList<Team> team_info = new ArrayList<Team>();

        // Read csv and get teams
        MyCsvFileReader reader = new MyCsvFileReader(getApplicationContext());
        ArrayList<String[]> all_teams = reader.readCsvFile(R.raw.schedule);

        // Convert csv to ArrayList<Team>
        for (int i = 0; i < all_teams.size(); i++) {
            // declare new team with info from csv
            Team temp = new Team(all_teams.get(i)[0], all_teams.get(i)[1], all_teams.get(i)[2], all_teams.get(i)[3],
                    all_teams.get(i)[4], all_teams.get(i)[5], all_teams.get(i)[6], all_teams.get(i)[7], all_teams.get(i)[8], all_teams.get(i)[9]);

            // add team to list
            team_info.add(temp);
        }

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this, team_info);
        ListView scheduleListView = (ListView) findViewById(R.id.scheduleListView);
        scheduleListView.setAdapter(scheduleAdapter);

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create the intent to start DetailActivity
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                //create a bundle object using the following:
                intent.putExtra("team", team_info.get(position)); // where al is your ArrayList holding team information.

                //start the activity using the intent with the bundle you just created.
                startActivity(intent);
            }
        };

        // attach listeners to each item in ListView
        scheduleListView.setOnItemClickListener(clickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public String gameSchedule() {
        StringBuilder sb = new StringBuilder();
        String add = "";
        for (int i = 0; i < team_info.size(); i++) { // add all team info to stringbuilder
            Team curr = team_info.get(i);
            add = curr.getTeamName() + " at " + curr.getTeamLocation() + ", " + curr.getTeamTime() + " on " + curr.getTeamDay();
            sb.append(add);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();

        if (res_id == R.id.share) {

            // Get schedule and create intent to send to friend
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "BasketBall Matches");

            // test stringify
            //System.out.println("SCHEDULE: " + gameSchedule());

            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, gameSchedule());
            startActivity(Intent.createChooser(shareIntent, "Share via"));

        } else if (res_id == R.id.sync) {
            // Snackbar with Try Again action
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Sync is not yet implemented", Snackbar.LENGTH_LONG);

            snackbar.setAction("Try Again", new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Wait for the next few labs. Thank you for your patience", Snackbar.LENGTH_LONG).show();
                }
            });
            snackbar.show();
        }
        else if (res_id == R.id.settings) {
            // Open context menu
            View v = (View) findViewById(R.id.scheduleListView); // THIS IS NOT RIGHT
            //System.out.println("VIEW: " + v);
            registerForContextMenu(v);
            openContextMenu(v);
        }
        return true;
    }

    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        // inflate floating_contextual_menu xml file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual_menu, menu);

    }

    @Override

    public boolean onContextItemSelected(MenuItem item) {

        int item_id = item.getItemId();

        /* TO BE IMPLEMENTED LATER */
//        if (item_id == R.id.women) {
//
//        } else if (item_id == R.id.men) {
//
//        } else if (item_id == R.id.on_campus) {
//
//        } else if (item_id == R.id.off_campus) {
//
//        }
        return false;
    }

}