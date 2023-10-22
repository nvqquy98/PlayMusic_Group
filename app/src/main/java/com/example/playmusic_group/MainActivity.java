package com.example.playmusic_group;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.playmusic_group.equalizer.DialogEqualizerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import context.DataMusic;
import danhsach.List;

public class MainActivity extends AppCompatActivity {
    DataMusic data = new DataMusic();
    ArrayList<BaiHat> arrayBaiHat = data.arrayBaiHat;
    TextView txtTitle, txtCasi, txtTimeStart, txtTimeEnd;
    SeekBar skBar;
    ImageButton btnPre, btnPlay, btnNext, btnTron, btnLap, btnSetting, btnList;
    ImageView music_compact;
    MediaPlayer mediaPlayer;
    Animation animation;
    int playId = 0;
    int lap = 0;
    int tron = 0;
    Random random = new Random();
    DialogEqualizerFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();

        Intent intent = getIntent();
        playId = intent.getIntExtra("playId", 0);
        if(playId > 0){
            Log.d("id", String.valueOf(playId));
        }
        else{
            playId = 1;
        }
        PlayMusic();
        mediaPlayer = new MediaPlayer();
        animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        settingFragment = DialogEqualizerFragment.newBuilder()
                .setAudioSessionId(mediaPlayer.getAudioSessionId())
                .themeColor(ContextCompat.getColor(this, R.color.primaryColor))
                .textColor(ContextCompat.getColor(this, R.color.textColor))
                .accentAlpha(ContextCompat.getColor(this, R.color.playingCardColor))
                .darkColor(ContextCompat.getColor(this, R.color.primaryDarkColor))
                .setAccentColor(ContextCompat.getColor(this, R.color.secondaryColor))
                .build();

        btnSetting.setOnClickListener(view -> {
            showSetting();
        });



        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tron == 1) {
                    playRandom();
                } else
                {
                    playNormal(false);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tron == 1) {
                    playRandom();
                } else
                {
                    playNormal(true);
                }
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                    music_compact.clearAnimation();
                } else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                    music_compact.startAnimation(animation);
                }
                mediaPlayer.setLooping(false);
                SetTimeEnd();
                UpdateTime();
            }
        });
        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lap == 0) {
                    lap = 1;
                    btnLap.setImageResource(R.drawable.lap_on);
                } else if (lap == 1) {
                    lap = 2;
                    btnLap.setImageResource(R.drawable.lap_mot);
                } else if (lap == 2) {
                    lap = 0;
                    btnLap.setImageResource(R.drawable.lap_off);
                }
            }
        });
        btnTron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tron == 0) {
                    tron = 1;
                    btnTron.setImageResource(R.drawable.tron_bai_on);
                } else if (tron == 1) {
                    tron = 0;
                    btnTron.setImageResource(R.drawable.tron_bai_off);
                }
            }
        });
        skBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skBar.getProgress());
            }
        });

        btnList.setOnClickListener(view -> {
            showList();
        });
    }

    private void UpdateTime() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat time = new SimpleDateFormat("mm:ss");
                txtTimeStart.setText(time.format(mediaPlayer.getCurrentPosition()));
                skBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (tron == 1) {
                            playRandom();
                        } else if (tron == 0) {
                            if (lap == 0) {
                                if (mediaPlayer.isPlaying()) {
                                    mediaPlayer.stop();
                                }
                                mediaPlayer.start();
                                btnPlay.setImageResource(R.drawable.play);
                                music_compact.clearAnimation();
                                mediaPlayer.stop();
                            } else if (lap == 1) {
                                playNormal(true);
                            }
                            else if (lap == 2) {
                                mediaPlayer.start();
                            }
                        }
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void SetTimeEnd() {
        SimpleDateFormat time = new SimpleDateFormat("mm:ss");
        txtTimeEnd.setText(time.format(mediaPlayer.getDuration()));
        skBar.setMax(mediaPlayer.getDuration());
    }

    private void PlayMusic() {
        if(getById(playId).getFile() > 0){
            mediaPlayer = MediaPlayer.create(MainActivity.this, getById(playId).getFile());
            txtTitle.setText(getById(playId).getTenBaiHat());
            txtCasi.setText(getById(playId).getTenCaSi());
        }
    }



    private void AnhXa() {
        txtTitle = (TextView) findViewById(R.id.textViewTitle);
        txtCasi = (TextView) findViewById(R.id.textViewSinger);
        txtTimeStart = (TextView) findViewById(R.id.textViewTimeStart);
        txtTimeEnd = (TextView) findViewById(R.id.textViewTimeEnd);
        skBar = (SeekBar) findViewById(R.id.seekBar);
        btnPre = (ImageButton) findViewById(R.id.buttonPre);
        btnPlay = (ImageButton) findViewById(R.id.buttonPlay);
        btnNext = (ImageButton) findViewById(R.id.buttonNext);
        btnTron = (ImageButton) findViewById(R.id.buttonNgauNhien);
        btnLap = (ImageButton) findViewById(R.id.buttonLap);
        music_compact = (ImageView) findViewById(R.id.musicCompact);
        btnSetting = (ImageButton) findViewById(R.id.buttonSetting);
        btnList = (ImageButton) findViewById(R.id.buttonList);
    }

    private void showSetting() {
        if (settingFragment != null) {
            settingFragment.show(getSupportFragmentManager(), "eq");
        }
    }

    private void showList() {
        Intent intentActiveList = new Intent(this, List.class);
        int id = playId;
        intentActiveList.putExtra("id",id);
        startActivity(intentActiveList);
    }

    private BaiHat getById(int id) {
        for (BaiHat baiHat : arrayBaiHat) {
            if (baiHat.getId() == id) {
                return baiHat;
            }
        }
        return null;
    }

    private int getRandomId() {
        Random random = new Random();
        int randomIndex = random.nextInt(arrayBaiHat.size());
        return arrayBaiHat.get(randomIndex).getId();
    }

    private  void  TronBai(){

    }


    private void  playRandom(){
        int idRandom = getRandomId();
        playId = idRandom;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        playStart();
    }

    private  void playNormal(boolean tien){
        if(tien == false){
            playId --;
            if(playId < 1){
                playId = getMaxId();
            }
        }
        else{
            playId ++;
            if(playId > getMaxId()){
                playId = 1;
            }
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        playStart();
    }

    public void playStart(){
        PlayMusic();
        mediaPlayer.start();
        btnPlay.setImageResource(R.drawable.pause);
        music_compact.startAnimation(animation);
        SetTimeEnd();
        UpdateTime();
    }

    private int getMaxId() {
        int maxId = 1;
        for (BaiHat baiHat : arrayBaiHat) {
            int currentId = baiHat.getId();
            if (currentId > maxId) {
                maxId = currentId;
            }
        }
        return maxId;
    }


}