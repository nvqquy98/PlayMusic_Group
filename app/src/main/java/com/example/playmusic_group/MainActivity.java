package com.example.playmusic_group;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

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
    int index = 0;
    int lap = 0;
    int tron = 0;
    Random random = new Random();
    DialogEqualizerFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        PlayMusic();
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
                    index = random.nextInt(arrayBaiHat.size());
                    if (index < 0) {
                        index = arrayBaiHat.size() - 1;
                    }
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    PlayMusic();
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                    music_compact.startAnimation(animation);
                    SetTimeEnd();
                    UpdateTime();
                } else {
                    index--;
                    if (index < 0) {
                        index = arrayBaiHat.size() - 1;
                    }
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    PlayMusic();
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                    music_compact.startAnimation(animation);
                    SetTimeEnd();
                    UpdateTime();
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tron == 1) {
                    index = random.nextInt(arrayBaiHat.size());
                    if (index > arrayBaiHat.size() - 1) {
                        index = 0;
                    }
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    PlayMusic();
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                    music_compact.startAnimation(animation);
                    SetTimeEnd();
                    UpdateTime();
                } else {
                    index++;
                    if (index > arrayBaiHat.size() - 1) {
                        index = 0;
                    }
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    PlayMusic();
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                    music_compact.startAnimation(animation);
                    SetTimeEnd();
                    UpdateTime();
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
                            index = random.nextInt(arrayBaiHat.size());
                            index++;
                            if (index > arrayBaiHat.size() - 1) {
                                index = 0;
                            }
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.stop();
                            }
                            PlayMusic();
                            mediaPlayer.start();
                            btnPlay.setImageResource(R.drawable.pause);
                            SetTimeEnd();
                            UpdateTime();
                        } else if (tron == 0) {
                            if (lap == 0) {
                                mediaPlayer.start();
                                btnPlay.setImageResource(R.drawable.play);
                                music_compact.clearAnimation();
                                mediaPlayer.stop();
                            } else if (lap == 1) {
                                index++;
                                if (index > arrayBaiHat.size() - 1) {
                                    index = 0;
                                }
                                if (mediaPlayer.isPlaying()) {
                                    mediaPlayer.stop();
                                }
                                PlayMusic();
                                mediaPlayer.start();
                                btnPlay.setImageResource(R.drawable.pause);
                                SetTimeEnd();
                                UpdateTime();
                            } else if (lap == 2) {
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
        mediaPlayer = MediaPlayer.create(MainActivity.this, arrayBaiHat.get(index).getFile());
        txtTitle.setText(arrayBaiHat.get(index).getTenBaiHat());
        txtCasi.setText(arrayBaiHat.get(index).getTenCaSi());
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
        int id = this.arrayBaiHat.get(index).getId();
        BaiHat baiHat = this.arrayBaiHat.get(index);
        intentActiveList.putExtra("id",id);
        intentActiveList.putExtra("baiHat",baiHat);
        startActivity(intentActiveList);
    }




}