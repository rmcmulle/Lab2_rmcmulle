package com.cs40333.rmcmulle.lab2_rmcmulle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ryan on 4/22/2017.
 */

public class GalleryActivity extends AppCompatActivity {

    DBHelper dbHelper;
    int team_id;
    GridView gridview;
    String timeStamp;
    private static final int CAMERA_REQUEST = 1;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_gallery);

        dbHelper = new DBHelper(this);
        Intent i = getIntent();
        team_id = i.getExtras().getInt("id");

        gridview = (GridView) findViewById(R.id.gridview);
        populateGridView();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Send intent to ImageActivity
                Intent i = new Intent(GalleryActivity.this, DetailActivity.class);
                i.putExtra("id", position);
                startActivity(i);
            }
        });
    }

    public void cameraClick(View v) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        //REMOVE IF USING image RETURNED BY THE camera intent in onActivityResult method
        File storageDirectory = new File(getApplicationContext().getFilesDir(), "images");
        if(!storageDirectory.exists()) storageDirectory.mkdir();
        setTimeStamp();
        File imageFile = new File(storageDirectory, getPictureName());
        Uri pictureUri = FileProvider.getUriForFile(getApplicationContext(),
                "com.cs40333.rmcmulle.lab2_rmcmulle",
                imageFile);
        System.out.println(pictureUri);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        cameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //=====================================================

        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    private void setTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        timeStamp = sdf.format(new Date());
    }

    private String getPictureName() {
        String imageName = "Img" + timeStamp + ".jpg";
        return imageName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Hello 2222");
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {

                //USE if image RETURNED BY THE camera intent 'data'
//                Bundle extras = data.getExtras();
//                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), imageBitmap, getPictureName(), null);
//                Uri imageUri = Uri.parse(path);


                //REMOVE IF USING image RETURNED BY THE camera intent 'data'
                File imagePath = new File(getApplicationContext().getFilesDir(), "images");
                File imageFile = new File( imagePath, getPictureName());
                Uri imageUri = FileProvider.getUriForFile(this, getPackageName(), imageFile);
                byte[]  byteArray = getBytes(imageUri, 150);
                //==============================================================

                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                String iDate = sdf.format(new Date()).toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBHelper.COL_TEAM_ID, team_id);
                contentValues.put(DBHelper.COL_IMG, byteArray);
                contentValues.put(DBHelper.COL_IMG_DATE, iDate);
                contentValues.put(DBHelper.COL_URI, imageUri.toString());
                dbHelper.insertData(DBHelper.TABLE_IMG, contentValues);
                System.out.println("image saved");

                populateGridView(); // add new image to the gridview
            }
        }
    }

    private byte[] getBytes(Uri imageUri, int maxSize) {
        System.out.println("image saved" + imageUri);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        Bitmap imageBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        return stream.toByteArray();
    }

    private void populateGridView() {
        String[] fields = new String[]{DBHelper.COL_ID, DBHelper.COL_IMG, DBHelper.COL_IMG_DATE};
        String where = " team_id = ?";
        String[] args = new String[]{Integer.toString(team_id)};
        int[] items = new int[] {R.id.col_id, R.id.book_image, R.id.date_tv};

        Cursor cursor = dbHelper.getSelectEntries(DBHelper.TABLE_IMG, fields, where, args);
        SimpleCursorAdapter galleryCursorAdapter = new SimpleCursorAdapter (this, R.layout.image_layout, cursor, fields, items, 0);;

        galleryCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue (View view, Cursor cursor, int columnIndex){
                if (view.getId() == R.id.book_image) {
                    ImageView imageView=(ImageView) view;
                    byte[] imageArray = cursor.getBlob(1);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageArray,0,imageArray.length);
                    imageView.setImageBitmap(bitmap);
                    return true;
                }
                return false;
            }});

        gridview.setAdapter(galleryCursorAdapter);
    }
}
