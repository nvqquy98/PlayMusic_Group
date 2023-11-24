package com.example.playmusic_group.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.playmusic_group.MainActivity;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Tắt nhạc
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            if (action.equals("hengio")) {
                // Xử lý công việc khi nhận được hẹn giờ
                Log.d("AlarmReceiver", "Received alarm intent");
                MainActivity mediaPlayerActivity = (MainActivity) context;
//                MediaPlayer mediaPlayer = mediaPlayerActivity.getMediaPlayer();
//                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();
//                }

                // Hiển thị thông báo
                showNotification(context, "Hẹn giờ đã đến", "Nhạc đã bị tắt");
            }
        }

    }

    private void showNotification(Context context, String title, String message) {
        // Thêm mã hiển thị thông báo ở đây
    }
}