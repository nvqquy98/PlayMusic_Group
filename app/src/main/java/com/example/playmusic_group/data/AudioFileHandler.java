package com.example.playmusic_group.data;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import com.example.playmusic_group.BaiHat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AudioFileHandler {

    public List<String> getAllAudioFiles(Context context) {
        ArrayList<String> result = new ArrayList<>();
        result.addAll(getMusicFromMusicFolder(context));
        result.addAll(getMusicFilesFromDownloads());
        return result;
    }

    public List<String> getMusicFromMusicFolder(Context context) {
        List<String> audioFilesList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();
        Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.Audio.Media.DATA};

        Cursor cursor = contentResolver.query(audioUri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                audioFilesList.add(filePath);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return audioFilesList;
    }

    public List<String> getMusicFilesFromDownloads() {
        List<String> musicFiles = new ArrayList<>();

        // Get the path to the "Downloads" directory
        File downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // List all files in the "Downloads" directory
        if (downloadDirectory != null && downloadDirectory.exists() && downloadDirectory.isDirectory()) {
            File[] files = downloadDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Check if the file is a music file (e.g., .mp3)
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                        musicFiles.add(file.getAbsolutePath());
                    }
                }
            }
        }

        return musicFiles;
    }

    @SuppressLint("DefaultLocale")
    @Nullable
    public BaiHat retrieveMetadata(String filePath, int id) {
        try {
            MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
            metadataRetriever.setDataSource(filePath);

            String title = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String artist = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String album = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String duration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            // Convert duration to human-readable format (milliseconds to minutes:seconds)
            long durationMillis = Long.parseLong(duration);
            String durationFormatted = String.format("%02d:%02d",
                    (durationMillis / 1000) / 60, (durationMillis / 1000) % 60);

            metadataRetriever.release();
            metadataRetriever.close();
            return new BaiHat(id, title, artist, filePath);
        } catch (Exception e) {
            return null;
        }
    }
}
