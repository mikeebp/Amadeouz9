package com.friday.class9;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camara extends AppCompatActivity {

    private final static int TAKE_PICTURE = 0;
    private final static int SAVE_PICTURE = 1;
    private final static int TAKE_VIDEO = 2;

    private ImageView iv;
    private String lastPictureURI;
    private VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        iv = (ImageView)findViewById(R.id.imageView2);
        vv = (VideoView)findViewById(R.id.videoView);
    }

    public void takePicture(View v){

        Log.d("TAKE PICTURE", "TAKING");
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(pictureIntent, TAKE_PICTURE);
        }

    }

    public void savePicture(View v){

        Log.d("SAVE PICTURE", "SAVING");
        Intent savePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photo = null;
        try{

            String time = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
            String name = "IMAGE_" + time;
            File directory = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            photo = File.createTempFile(name, ".jpg", directory);
            lastPictureURI = photo.getAbsolutePath();

        } catch(Exception e){

            e.printStackTrace();
        }

        if(photo != null){

            savePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, lastPictureURI);
            startActivityForResult(savePicIntent, SAVE_PICTURE);
        }
    }

    public void takeVideo(View v){

        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(takeVideoIntent.resolveActivity(getPackageManager()) != null){

            startActivityForResult(takeVideoIntent, TAKE_VIDEO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){

            switch (requestCode){

                case TAKE_PICTURE:
                    Log.d("onActivityResult", "take picture");
                    Bundle extra = data.getExtras();
                    Bitmap imagen = (Bitmap)extra.get("data");
                    iv.setImageBitmap(imagen);
                    break;

                case SAVE_PICTURE:
                    Log.d("onActivityResult", "save picture");
                    Bitmap imagen2 = BitmapFactory.decodeFile(lastPictureURI);
                    iv.setImageBitmap(imagen2);
                    break;

                case TAKE_VIDEO:
                    Uri video = data.getData();
                    vv.setVideoURI(video);
                    vv.start();
                    break;
            }
        }
    }
}

