package com.example.playmusic_group;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;

import com.example.playmusic_group.Alarm.AlarmReceiver;

import context.app_activity.LoginActivity;

import com.example.playmusic_group.data.AudioFileHandler;
import com.example.playmusic_group.equalizer.DialogEqualizerFragment;
import com.example.playmusic_group.play_music_background.MusicConfig;
import com.example.playmusic_group.play_music_background.MusicService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import danhsach.ListMusicActivity;

public class MainActivity extends AppCompatActivity {

    public static final String ITEM_SELECTED_INDEX_KEY = "ITEM_SELECTED_INDEX_KEY";
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1234;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    ArrayList<BaiHat> arrayBaiHat = new ArrayList<>();
    TextView txtTitle, txtCasi, txtTimeStart, txtTimeEnd;
    SeekBar skBar;
    ImageButton btnPre, btnPlay, btnNext, btnTron, btnLap, btnSetting, btnList;
    ImageView music_compact;
    MediaPlayer mediaPlayer;
    Animation animation;
    private Button btnScheduleAlarm;
    private int currentMusicIndex;
    int lap = 0;
    int tron = 0;
    private int selectedHour;
    private int selectedMinute;

    Random random = new Random();
    DialogEqualizerFragment.Builder settingFragment;
    private static final int ALARM_REQUEST_CODE = 123;
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case MusicConfig.Actions.PLAY_ACTION, MusicConfig.Actions.PAUSE_ACTION -> playMusic();
                    case MusicConfig.Actions.PREV_ACTION -> onPreClicked();
                    case MusicConfig.Actions.NEXT_ACTION -> onNextClicked();
                    case MusicConfig.Actions.CANCEL_ACTION -> {
                        mediaPlayer.stop();
                        startMusicService(action);
                    }
                }
            }
        }
    };

    private void logoutUser() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();
        Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
    }

    void auth() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", "");

        if (!savedUsername.isEmpty()) {
            Toast.makeText(MainActivity.this, "Đã đăng nhập vào tài khoản: " + savedUsername, Toast.LENGTH_SHORT).show();
        } else {
            showLogin();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int indexNew = intent.getIntExtra(ITEM_SELECTED_INDEX_KEY, 0);
        if (indexNew >= 0 && indexNew < arrayBaiHat.size()) {
            currentMusicIndex = indexNew;
            startMusic();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);
        auth();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            initData();
        }
        AnhXa();
        settingFragment = DialogEqualizerFragment.newBuilder()
                .themeColor(ContextCompat.getColor(this, R.color.primaryColor))
                .textColor(ContextCompat.getColor(this, R.color.textColor))
                .accentAlpha(ContextCompat.getColor(this, R.color.playingCardColor))
                .darkColor(ContextCompat.getColor(this, R.color.primaryDarkColor))
                .setAccentColor(ContextCompat.getColor(this, R.color.secondaryColor))
        ;
        Intent intent = getIntent();
        currentMusicIndex = intent.getIntExtra(ITEM_SELECTED_INDEX_KEY, 0);
        if (currentMusicIndex == -1) {
            currentMusicIndex = 0;
        }

//        checkExistingAlarm();
        btnSetting.setOnClickListener(view -> {
            showSetting();
        });
        btnScheduleAlarm.setOnClickListener(view -> {
            showTimePickerDialog();
        });


        btnPre.setOnClickListener(v -> {
            onPreClicked();
            startMusicService(MusicConfig.Actions.PREV_ACTION);
        });

        btnNext.setOnClickListener(v -> {
            onNextClicked();
            startMusicService(MusicConfig.Actions.NEXT_ACTION);
        });

        btnPlay.setOnClickListener(view -> {
            playMusic();
        });

        btnLap.setOnClickListener(v -> {
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
        });

        btnTron.setOnClickListener(v -> {
            if (tron == 0) {
                tron = 1;
                btnTron.setImageResource(R.drawable.tron_bai_on);
            } else if (tron == 1) {
                tron = 0;
                btnTron.setImageResource(R.drawable.tron_bai_off);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initData();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.stop();
        startMusicService(MusicConfig.Actions.CANCEL_ACTION);
        unRegisterReceiver();
        super.onDestroy();
    }

    private void playMusic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            startMusicService(MusicConfig.Actions.PAUSE_ACTION);
            btnPlay.setImageResource(R.drawable.play);
            music_compact.clearAnimation();
        } else {
            startMusicService(MusicConfig.Actions.PLAY_ACTION);
            mediaPlayer.start();
            btnPlay.setImageResource(R.drawable.pause);
            music_compact.startAnimation(animation);
        }
        mediaPlayer.setLooping(false);
        SetTimeEnd();
        UpdateTime();
    }

    private void initData() {
        AudioFileHandler audioFileHandler = new AudioFileHandler();
        List<String> audioFiles = audioFileHandler.getMusicFilesFromDownloads();
        for (int i = 0; i < audioFiles.size(); i++) {
            BaiHat musicInfo = audioFileHandler.retrieveMetadata(audioFiles.get(i), i + 1);
            if (musicInfo != null) {
                arrayBaiHat.add(musicInfo);
            }
        }
        bindingMusicToView();
        animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        if (mediaPlayer == null) return;

    }

    private void onPreClicked() {
        if (tron == 1) {
            playRandom();
        } else {
            playNormal(false);
        }
    }

    private void onNextClicked() {
        if (tron == 1) {
            playRandom();
        } else {
            playNormal(true);
        }
    }

    private int generateUniqueCode() {
        return (int) System.currentTimeMillis(); // Sử dụng thời gian hiện tại làm mã duy nhất
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
                                btnPlay.setImageResource(R.drawable.play);
                                music_compact.clearAnimation();
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                bindingMusicToView();
                            } else if (lap == 1) {
                                playNormal(true);
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

    private void bindingMusicToView() {
        if (arrayBaiHat.isEmpty()) return;
        BaiHat currentMusicSelected = arrayBaiHat.get(currentMusicIndex);
        if (currentMusicSelected != null && currentMusicSelected.getFilePath() != null) {
            mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.fromFile(new File(currentMusicSelected.getFilePath())));
            if (currentMusicSelected.getTenBaiHat() != null) {
                txtTitle.setText(currentMusicSelected.getTenBaiHat());
            }
            if (currentMusicSelected.getTenBaiHat() != null) {
                txtCasi.setText(currentMusicSelected.getTenCaSi());
            }
        }
    }

    private void AnhXa() {
        txtTitle = findViewById(R.id.textViewTitle);
        txtCasi = findViewById(R.id.textViewSinger);
        txtTimeStart = findViewById(R.id.textViewTimeStart);
        txtTimeEnd = findViewById(R.id.textViewTimeEnd);
        skBar = findViewById(R.id.seekBar);
        btnPre = findViewById(R.id.buttonPre);
        btnPlay = findViewById(R.id.buttonPlay);
        btnNext = findViewById(R.id.buttonNext);
        btnTron = findViewById(R.id.buttonNgauNhien);
        btnLap = findViewById(R.id.buttonLap);
        music_compact = findViewById(R.id.musicCompact);
        btnSetting = findViewById(R.id.buttonSetting);
        btnList = findViewById(R.id.buttonList);
        btnScheduleAlarm = findViewById(R.id.btnScheduleAlarm);

    }

    private void showSetting() {
        if (settingFragment != null && mediaPlayer != null) {
            settingFragment.setAudioSessionId(mediaPlayer.getAudioSessionId()).build().show(getSupportFragmentManager(), "eq");
        }
    }

    private void showList() {
        Intent intentActiveList = new Intent(this, ListMusicActivity.class);
        int id = arrayBaiHat.get(currentMusicIndex).getId();
        intentActiveList.putExtra("id", id);
        startActivity(intentActiveList);
    }

    private void showLogin() {
        Intent intentActiveLogin = new Intent(this, LoginActivity.class);
        startActivity(intentActiveLogin);
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

    private void TronBai() {
    }

    private void playRandom() {
        currentMusicIndex = (new Random()).nextInt(arrayBaiHat.size());
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        playStart();
    }

    private void playNormal(boolean tien) {
        if (!tien) {
            currentMusicIndex--;
            if (currentMusicIndex < 0) {
                currentMusicIndex = arrayBaiHat.size() - 1;
            }
        } else {
            currentMusicIndex++;
            if (currentMusicIndex > arrayBaiHat.size() - 1) {
                currentMusicIndex = 0;
            }
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        playStart();
    }

    public void playStart() {
        bindingMusicToView();
        mediaPlayer.start();
        btnPlay.setImageResource(R.drawable.pause);
        music_compact.startAnimation(animation);
        startMusicService(MusicConfig.Actions.PLAY_ACTION);
        SetTimeEnd();
        UpdateTime();
    }

    private void showTimePickerDialog() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Handle the selected time (e.g., schedule alarm)
                        handleSelectedTime(hourOfDay, minute);
                    }
                }, hour, minute, false);

        // Show the dialog
        timePickerDialog.show();
    }


    private void checkExistingAlarm(int hourOfDay, int minute) {
        // Check if there's an existing alarm
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        boolean alarmUp = (PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE) != null);

        if (!alarmUp) {
            // If no existing alarm, schedule a new one with the specified time
            scheduleAlarm(hourOfDay, minute);
        }
    }


    private void scheduleAlarm(int hourOfDay, int minute) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Lấy thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Kiểm tra xem thời gian đã chọn có phải là thời điểm trong quá khứ hay không
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            // Nếu là thời điểm trong quá khứ, thêm 1 ngày để đặt hẹn giờ cho ngày mai
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        Log.d("Giờ hẹn ", "Scheduled for: " + calendar.getTime());

        // Tạo một intent để phát sóng khi hẹn giờ đến
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        // Create an intent to be broadcast when the alarm triggers
        int uniqueCode = generateUniqueCode();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueCode, alarmIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        // Đặt hẹn giờ
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Sử dụng hàm setExactAndAllowWhileIdle trên Android 6.0 trở lên
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    // Sửa lại hàm handleSelectedTime để nhận thời gian từ TimePickerDialog
    private void handleSelectedTime(int hourOfDay, int minute) {
        // Handle the selected time (e.g., schedule alarm)
        long selectedTimeMillis = getTimeInMillis(hourOfDay, minute);


        String time = String.format("%02d:%02d", hourOfDay, minute);
        Toast.makeText(this, "Giờ bạn đã hẹn: " + time, Toast.LENGTH_SHORT).show();

        scheduleAlarm(hourOfDay, minute);

    }

    private long getTimeInMillis(int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }


    private Intent getMusicService(String musicAction) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(musicAction);
        intent.putExtra(MusicConfig.MUSIC_TITLE_KEY, arrayBaiHat.get(currentMusicIndex).getTenBaiHat());
        intent.putExtra(MusicConfig.MUSIC_ARTISTS_KEY, arrayBaiHat.get(currentMusicIndex).getTenCaSi());
        return intent;
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicConfig.Actions.PLAY_ACTION);
        intentFilter.addAction(MusicConfig.Actions.PAUSE_ACTION);
        intentFilter.addAction(MusicConfig.Actions.PREV_ACTION);
        intentFilter.addAction(MusicConfig.Actions.NEXT_ACTION);
        intentFilter.addAction(MusicConfig.Actions.CANCEL_ACTION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(broadcastReceiver, intentFilter, RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    private void unRegisterReceiver() {
        unregisterReceiver(broadcastReceiver);
    }

    private void startMusicService(String musicAction) {
        startService(getMusicService(musicAction));
    }

    private void stopMusicService() {
        stopService(new Intent(this, MusicService.class));
    }

    private void startMusic() {
        mediaPlayer.stop();
        bindingMusicToView();
        playStart();
    }
}
