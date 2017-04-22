package com.cs40333.rmcmulle.lab2_rmcmulle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.security.AccessController.getContext;

/**
 * Created by Ryan on 2/16/2017.
 */

public class DetailActivity extends Activity {

    final int CAMERA_REQUEST = 1337;
//    int id = getIntent().getIntExtra("team", 1);

    @Override
    public void onCreate (Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_detail);

        // REINSTANTIATE DATABASE
        DBHelper dbHelper= new DBHelper(getApplicationContext());

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
        final View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LAB CODE
                Intent galleryIntent = new Intent(DetailActivity.this, GalleryActivity.class);

                //File PictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                //String pictureName = getPictureName();
                //File imageFile = new File(PictureDirectory, pictureName);
                //Uri pictureUri = Uri.fromFile(imageFile);
                //galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, "picture"); // send extra as 'picture'
                //startActivityForResult(cameraIntent, CAMERA_REQUEST);

                // Get team id to add as extra
                int id = getIntent().getIntExtra("team", 1);
                galleryIntent.putExtra("id", id); // add team id to intent

                // Check
                Log.d("TEST: ", "Oh so close");

                startActivity(galleryIntent); // begin gallery activity
            }

//            private String getPictureName() {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
//                String timestamp = sdf.format(new Date());
//                return "BestMoments" + timestamp + ".jpg";
//            }

//            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//                if (resultCode == RESULT_OK) {
//                    if (requestCode == CAMERA_REQUEST) {
//                        Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
//                        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//
//                        String pictureDirectoryPath = pictureDirectory.getPath();
//                        Uri imageUri = Uri.parse(pictureDirectoryPath);
//                        InputStream inputStream;
//                        try {
//                            inputStream = getContentResolver().openInputStream(imageUri);
//                            Bitmap image = BitmapFactory.decodeStream(inputStream);
//                            ImageView imgView = (ImageView) findViewById(R.id.photo_taken);
//                            imgView.setImageBitmap(image);
//
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//            }
        };

        // Attach listener
        Button cameraBtn = (Button) findViewById(R.id.camera);
        cameraBtn.setOnClickListener(click);

        // SET VALUES

        // Text Values
        int id = getIntent().getIntExtra("team", 1);

//        for (int i = 0 ; i < stringInfo.length ; i++) {
//            System.out.println(stringInfo[i]);
//        }

        // QUERY VALUES FROM DATABASE
        Cursor team_cursor = dbHelper.getSelectEntries("Team",new String[]{"*"},
                "_id = " + Integer.toString(id),null);

        String team_location = team_cursor.getString(team_cursor.getColumnIndex("team_location"));
        String team_name = team_cursor.getString(team_cursor.getColumnIndex("team_name"));
        String team_mascot = team_cursor.getString(team_cursor.getColumnIndex("team_mascot"));
        String team_record = team_cursor.getString(team_cursor.getColumnIndex("team_record"));
        String team_score = team_cursor.getString(team_cursor.getColumnIndex("team_score"));
        String team_score_time = team_cursor.getString(team_cursor.getColumnIndex("team_score_time"));
        String team_date = team_cursor.getString(team_cursor.getColumnIndex("team_date"));
        String team_day = team_cursor.getString(team_cursor.getColumnIndex("team_day"));
        String team_time = team_cursor.getString(team_cursor.getColumnIndex("team_time"));
        String team_logo = team_cursor.getString(team_cursor.getColumnIndex("team_logo"));

        // SET DETAIL ACTIVITY VALUES
        location_info.setText(team_location);
        team1_school.setText(team_name);
        team1_name.setText(team_mascot);
        team1_record.setText(team_record);
        score.setText(team_score);
        score_time.setText(team_score_time);

        // TEST OUTPUTS
//        Log.d("GAME INFO: ",team_name + ", " + team_mascot + ", " + team_score + ", " + team_record);

        // VENUE INFO
        String game_time = team_date + ", " + team_day + ", " + team_time;
        venue_info.setText(game_time);

        // Team img
        String mDrawableName = team_logo;
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        team_img.setImageResource(resID);

        // Notre Dame Img
        resID = getResources().getIdentifier("notre_dame" , "drawable", getPackageName());
        nd_img.setImageResource(resID);

    }
    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "BestMoments" + timestamp + ".jpg";
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                String pictureDirectoryPath = pictureDirectory.getPath();
                Uri imageUri = Uri.parse(pictureDirectoryPath);
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    ImageView imgView = (ImageView) findViewById(R.id.photo_taken);
                    imgView.setImageBitmap(image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
