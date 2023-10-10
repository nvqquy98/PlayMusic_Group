package com.example.playmusic_group;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<BaiHat> arrayBaiHat;

    TextView txtTitle, txtCasi, txtTimeStart, txtTimeEnd;
    SeekBar skBar;
    ImageButton btnPre, btnPlay, btnNext;
    ImageView music_compact;
    MediaPlayer mediaPlayer;
    Animation animation;
    int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        AddBaiHat();
        PlayMusic();
        animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index --;
                if (index < 0){
                    index = arrayBaiHat.size() - 1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                PlayMusic();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                music_compact.startAnimation(animation);
                SetTimeEnd();
                UpdateTime();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index ++;
                if(index > arrayBaiHat.size() - 1){
                    index = 0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                PlayMusic();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                music_compact.startAnimation(animation);
                SetTimeEnd();
                UpdateTime();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                    music_compact.clearAnimation();
                }else{
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                    music_compact.startAnimation(animation);
                }
                SetTimeEnd();
                UpdateTime();
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
    }
    private void UpdateTime(){
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
                        index ++;
                        if(index > arrayBaiHat.size() - 1){
                            index = 0;
                        }
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        PlayMusic();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                        SetTimeEnd();
                        UpdateTime();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }
    private void SetTimeEnd(){
        SimpleDateFormat time = new SimpleDateFormat("mm:ss");
        txtTimeEnd.setText(time.format(mediaPlayer.getDuration()));
        skBar.setMax(mediaPlayer.getDuration());
    }

    private void PlayMusic() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, arrayBaiHat.get(index).getFile());
        txtTitle.setText(arrayBaiHat.get(index).getTenBaiHat());
        txtCasi.setText(arrayBaiHat.get(index).getTenCaSi());
    }

    private void AddBaiHat() {
        arrayBaiHat = new ArrayList<>();
        arrayBaiHat.add(new BaiHat("Việt Nam Những Chuyến Đi","Vicky Nhung",R.raw.viet_nam_va_nhung_chuyen_di));
        arrayBaiHat.add(new BaiHat("Anh Ba Khía Miền Tây","Đan Trường", R.raw.anh_ba_khia));
        arrayBaiHat.add(new BaiHat("Kiếp Ve Sầu","Đan Trường", R.raw.kiep_ve_sau));
        arrayBaiHat.add(new BaiHat("Mưa Trên Cuộc Tình","Đan Trường", R.raw.mua_tren_cuoc_tinh));
        arrayBaiHat.add(new BaiHat("Nếu Phôi Pha Ngày Mai","Đan Trường",R.raw.phoi_phai));
        arrayBaiHat.add(new BaiHat("Anh Đã Quen Với Cô Đơn","Sobun Hoàng Sơn",R.raw.anh_da_quen_voi_co_don));
        arrayBaiHat.add(new BaiHat("Buông Đôi Tay Nhau Ra","Sơn Tùng MTP",R.raw.buong_doi_tay_nhau_ra));
        arrayBaiHat.add(new BaiHat("Có Chàng Trai Viết Lên Cây","Phan Mạnh Quỳnh",R.raw.co_chang_trai_viet_len_cay));
        arrayBaiHat.add(new BaiHat("Gửi Anh Xa Nhớ","Bích Phương",R.raw.gui_anh_xa_nho));
        arrayBaiHat.add(new BaiHat("Như Phút Ban Đầu","Noo Phước Thịnh",R.raw.nhu_phut_ban_dau));
        arrayBaiHat.add(new BaiHat("Vợ Người Ta","Phan Mạnh Quỳnh",R.raw.vo_nguoi_ta));
        arrayBaiHat.add(new BaiHat("Yêu Một Người Vô Tâm","Bảo Anh",R.raw.yeu_mot_nguoi_vo_tam));
        arrayBaiHat.add(new BaiHat("Yêu Nhau Nửa Ngày","Phan Mạnh Quỳnh",R.raw.yeu_nhau_nua_ngay));
    }

    private void AnhXa() {
        txtTitle        = (TextView) findViewById(R.id.textViewTitle);
        txtCasi         = (TextView) findViewById(R.id.textViewSinger);
        txtTimeStart    = (TextView) findViewById(R.id.textViewTimeStart);
        txtTimeEnd      = (TextView) findViewById(R.id.textViewTimeEnd);
        skBar           = (SeekBar) findViewById(R.id.seekBar);
        btnPre          = (ImageButton) findViewById(R.id.buttonPre);
        btnPlay         = (ImageButton) findViewById(R.id.buttonPlay);
        btnNext         = (ImageButton) findViewById(R.id.buttonNext);
        music_compact   = (ImageView) findViewById(R.id.musicCompact);

    }
}