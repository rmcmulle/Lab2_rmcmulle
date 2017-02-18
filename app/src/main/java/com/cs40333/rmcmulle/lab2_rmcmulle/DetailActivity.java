package com.cs40333.rmcmulle.lab2_rmcmulle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import static java.security.AccessController.getContext;

/**
 * Created by Ryan on 2/16/2017.
 */

public class DetailActivity extends Activity {

    @Override
    public void onCreate (Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_detail);

        // INITIALIZE WIDGETS

        TextView venue_info = (TextView) findViewById(R.id.venue_info);
        TextView location_info = (TextView) findViewById(R.id.location_info);
        TextView team1_school = (TextView) findViewById(R.id.team1_school);
        TextView team1_name = (TextView) findViewById(R.id.team1_name);
        TextView team1_record = (TextView) findViewById(R.id.team1_record);
        TextView score = (TextView) findViewById(R.id.score);
        TextView score_time = (TextView) findViewById(R.id.score_time);
        ImageView team_img = (ImageView) findViewById(R.id.team_1_img);
        ImageView nd_img = (ImageView) findViewById(R.id.imageView3);

        // Click Listener
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Specify Intent
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(cameraIntent);
            }
        };

        // Attach listener
        Button cameraBtn = (Button) findViewById(R.id.camera);
        cameraBtn.setOnClickListener(click);

        // SET VALUES

        // Text Values
        String[] stringInfo = getIntent().getStringArrayExtra("team");
        String game_time = stringInfo[3] + ", " + stringInfo[2] + ", " + stringInfo[4];
        venue_info.setText(game_time);
        location_info.setText(stringInfo[9]);
        team1_school.setText(stringInfo[1]);
        team1_name.setText(stringInfo[5]);
        team1_record.setText(stringInfo[6]);
        score.setText(stringInfo[7]);
        score_time.setText(stringInfo[8]);

        // Team img
        String mDrawableName = stringInfo[0];
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        team_img.setImageResource(resID);

        // Notre Dame Img
        resID = getResources().getIdentifier("notre_dame" , "drawable", getPackageName());
        nd_img.setImageResource(resID);
    }
}
