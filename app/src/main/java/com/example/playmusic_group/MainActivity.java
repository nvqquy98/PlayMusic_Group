package com.example.playmusic_group;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<BaiHat> arrayBaiHat;
    
    TextView txtTitle, txtTimeStart, txtTimeEnd;
    SeekBar skBar;
    ImageButton btnPre, btnStop, btnPlay, btnNext;
    MediaPlayer mediaPlayer;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        AddBaiHat();
        PlayMusic();
        btnPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                }else{
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    btnPlay.setImageResource(R.drawable.pause);
                    index += 1;
                    PlayMusic();
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.play);
                }else {
                    index += 1;

                    PlayMusic();
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.play);
                }
            }
        });
    }

    private void PlayMusic() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, arrayBaiHat.get(index).getFile());
        txtTitle.setText(arrayBaiHat.get(index).getTenBaiHat());
    }

    private void AddBaiHat() {
        arrayBaiHat = new ArrayList<>();
        arrayBaiHat.add(new BaiHat("Anh Ba Khía Miền Tây", R.raw.anh_ba_khia));
        arrayBaiHat.add(new BaiHat("Kiếp Ve Sầu", R.raw.khiep_ve_sau));
        arrayBaiHat.add(new BaiHat("Mối Tình Đầu", R.raw.moi_tinh));
        arrayBaiHat.add(new BaiHat("Môt Tình Yêu", R.raw.mot_tinh_yeu));
        arrayBaiHat.add(new BaiHat("Mưa Cuộc Tình", R.raw.mua_cuoc_tinh));
        arrayBaiHat.add(new BaiHat("Phôi Phai",R.raw.phoi_phai));
        arrayBaiHat.add(new BaiHat("Tình Khúc", R.raw.tinh_khuc));
    }

    private void AnhXa() {
        txtTitle        = (TextView) findViewById(R.id.textViewTitle);
        txtTimeStart    = (TextView) findViewById(R.id.textViewTimeStart);
        txtTimeEnd      = (TextView) findViewById(R.id.textViewTimeEnd);
        skBar           = (SeekBar) findViewById(R.id.seekBar);
        btnPre          = (ImageButton) findViewById(R.id.buttonPre);
        btnStop         = (ImageButton) findViewById(R.id.buttonStop);
        btnPlay         = (ImageButton) findViewById(R.id.buttonPlay);
        btnNext         = (ImageButton) findViewById(R.id.buttonNext);
    }
}