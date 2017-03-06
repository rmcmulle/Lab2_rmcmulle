package com.cs40333.rmcmulle.lab2_rmcmulle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
        final int CAMERA_REQUEST = 1337;

        // Click Listener
        final View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Specify Intent
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivity(cameraIntent);

                // LAB CODE
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File PictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureName = getPictureName();
                File imageFile = new File(PictureDirectory, pictureName);
                Uri pictureUri = Uri.fromFile(imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, "picture"); // send extra as 'picture'
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
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
        };

        // Attach listener
        Button cameraBtn = (Button) findViewById(R.id.camera);
        cameraBtn.setOnClickListener(click);

        // SET VALUES

        // Text Values
        Team stringInfo = (Team) getIntent().getSerializableExtra("team");

//        for (int i = 0 ; i < stringInfo.length ; i++) {
//            System.out.println(stringInfo[i]);
//        }

        String game_time = stringInfo.getTeamDate() + ", " + stringInfo.getTeamDay() + ", " + stringInfo.getTeamScoreTime();
        venue_info.setText(game_time);
        location_info.setText(stringInfo.getTeamLocation());
        team1_school.setText(stringInfo.getTeamName());
        team1_name.setText(stringInfo.getTeamMascot());
        team1_record.setText(stringInfo.getTeamRecord());
        score.setText(stringInfo.getTeamScore());
        score_time.setText(stringInfo.getTeamScoreTime());

        // Team img
        String mDrawableName = stringInfo.getTeamLogo();
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        team_img.setImageResource(resID);

        // Notre Dame Img
        resID = getResources().getIdentifier("notre_dame" , "drawable", getPackageName());
        nd_img.setImageResource(resID);

    }
}
