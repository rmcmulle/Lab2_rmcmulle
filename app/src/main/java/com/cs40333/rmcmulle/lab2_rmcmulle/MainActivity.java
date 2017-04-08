package com.cs40333.rmcmulle.lab2_rmcmulle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.widget.SimpleCursorAdapter;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declare Variables
    DBHelper dbHelper;
    final ArrayList<Team> team_info = new ArrayList<Team>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DATABASE CREATION
        dbHelper = new DBHelper(getApplicationContext()); // create DB
        dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1, 2); // ensure blank DB

        // VIEWS
        ListView scheduleListView = (ListView) findViewById(R.id.scheduleListView);

        // Create Action Bar
        Toolbar myToolBar = (Toolbar) findViewById(R.id.toolbar);
        myToolBar.setTitle("ND Athletics");
        setSupportActionBar(myToolBar);

//        // Declare List of Teams
//        final ArrayList<Team> team_info = new ArrayList<Team>();

        // Read csv and get teams
        MyCsvFileReader reader = new MyCsvFileReader(getApplicationContext());
        ArrayList<String[]> all_teams = reader.readCsvFile(R.raw.schedule);

        // Convert csv to Database
        for (int i = 0; i < all_teams.size(); i++) {
            // ADD TO DATABASE
            fn_insert("Team",new String[]{all_teams.get(i)[0], all_teams.get(i)[1], all_teams.get(i)[2], all_teams.get(i)[3],
                    all_teams.get(i)[4], all_teams.get(i)[5], all_teams.get(i)[6], all_teams.get(i)[7], all_teams.get(i)[8],
                    all_teams.get(i)[9]});
        }

        // PRINT DATABASE TEST
//        System.out.println(getTableAsString(dbHelper,"Team"));
        // END TEST

//        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this, team_info);
//        scheduleListView.setAdapter(scheduleAdapter);

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create the intent to start DetailActivity
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                //create a bundle object using the following:

//                String[] cols = new String[]{"_id"};
                // TEST OUTPUT
//                Log.d("POSITION: ", Integer.toString(position));
//                Cursor temp = dbHelper.getSelectEntries("Team",cols,"team_name = ",null);
//                Log.d("_ID",temp.getString(temp.getColumnIndex("_id")));

//                intent.putExtra("team", dbHelper.getSelectEntries("Team",cols,"1",null).getString(position)); // where al is your ArrayList holding team information.
                intent.putExtra("team", position + 1); // where al is your ArrayList holding team information.

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

//        TO BE IMPLEMENTED LATER
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


    private void populateListView() {
        //Get names of all the fields of the Team table
        String[] fields = {"_id","team_logo","team_name","team_date"};
//        String[] fields = dbHelper.getTableFields("Team");
//        for (int i = 0 ; i < fields.length ; i++) {
//            Log.d("FIELDS: ",fields[i]);
//        }


        //Get all the team entries from the Team table
        Cursor cursor = dbHelper.getAllEntries("Team", fields);
//        Log.d("CURSOR: ",DatabaseUtils.dumpCursorToString(cursor));

        //Get ids of all the widgets in the custom layout for the listview
//        int[] item_ids = new int[] {-1, R.id.team_name};
        int[] item_ids = new int[] {-1, R.id.team_img, R.id.team_name, R.id.team_date};

        //Create the cursor that is going to feed information to the listview
        SimpleCursorAdapter teamCursorAdapter;

        //The adapter for the listview gets information and attaches it to appropriate elements
        teamCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.schedule_item, cursor, fields, item_ids,0);

        // Set ViewBinder for images
        teamCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.team_img) {
                    ImageView IV=(ImageView) view;
                    int resID = getApplicationContext().getResources().getIdentifier(cursor.getString(columnIndex), "drawable",  getApplicationContext().getPackageName());
                    IV.setImageResource(resID);
                    return true;
                }
                return false;
            }
        });

        ListView listView = (ListView) findViewById(R.id.scheduleListView);
        listView.setAdapter(teamCursorAdapter);
    }

    public void fn_insert(String db, String[] info) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("team_logo", info[0]);
        contentValues.put("team_name", info[1]);
        contentValues.put("team_date", info[2]);
        contentValues.put("team_day", info[3]);
        contentValues.put("team_time", info[4]);
        contentValues.put("team_mascot", info[5]);
        contentValues.put("team_record", info[6]);
        contentValues.put("team_score", info[7]);
        contentValues.put("team_score_time", info[8]);
        contentValues.put("team_location", info[9]);
        dbHelper.insertData(db, contentValues);
        populateListView();
    }


//    public void fn_delete(View view) {
//        String bookid = String.valueOf(book_name.getText());
//        dbHelper.deleteData("Book", " _id = ?", Integer.parseInt(bookid));
//        //populateListView();
//    }


    public String getTableAsString(SQLiteOpenHelper db, String tableName) {
        Log.d("dbHelper", "getTableAsString called");
        SQLiteDatabase myDB = db.getWritableDatabase();
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = myDB.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        return tableString;
    }

}