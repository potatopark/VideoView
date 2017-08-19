package com.example.user.a11_videoviewexam;

import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import android.Manifest;

public class MainActivity extends AppCompatActivity {
    VideoView videoView;
    MediaController mc;
    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;
    boolean permissionCheck=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_main);

        videoView = (VideoView) findViewById(R.id.videoView);
        mc = new MediaController(this);
        videoView.setMediaController(mc);
        permissionCheck();
        if(permissionCheck) startVideo();

    }
    protected void startVideo(){
        File sdcard = Environment.getExternalStorageDirectory();
        String video_path = sdcard.getAbsolutePath() + "/BigBuck.mp4";
        videoView.setVideoPath(video_path);

        videoView.start();
    }

    public void permissionCheck() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        } else {
            permissionCheck = true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE :
                if(grantResults.length>0
                        && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startVideo();
                }else{
                    Toast.makeText(this,"동영상을 실행할 수가 없습니다.",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
