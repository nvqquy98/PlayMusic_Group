package com.example.playmusic_group;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by suheb on 4/3/16.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    public static   MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {
//        mp=MediaPlayer.create(context, R.raw.alarm);
//        mp.start();
        Log.i("alrmaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaa");
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
    }
}