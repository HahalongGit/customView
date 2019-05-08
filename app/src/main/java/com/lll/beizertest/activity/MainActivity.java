package com.lll.beizertest.activity;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;
import com.lll.beizertest.view.MessageBubbleView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MessageBubbleView.attachView(findViewById(R.id.iv_showImage));

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, Uri.parse(""));
//        mediaPlayer.setAudioAttributes(AudioAttributes);
//        mediaPlayer.setAudioStreamType();
//        mediaPlayer.setDataSource();
        try {
            mediaPlayer.prepare();//For files, it is OK to call prepare(),
            mediaPlayer.prepareAsync();//For streams, you should call prepareAsync(),
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }

}
