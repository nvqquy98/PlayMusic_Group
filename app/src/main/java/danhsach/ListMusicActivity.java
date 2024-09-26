package danhsach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.playmusic_group.BaiHat;
import com.example.playmusic_group.R;
import com.example.playmusic_group.data.AudioFileHandler;

import java.util.ArrayList;
import java.util.List;

import context.DataMusic;

public class ListMusicActivity extends AppCompatActivity {
    private DataMusic data = new DataMusic();
    private ListView _listView;
    private ListView _listViewPlaying;
    ImageButton btnBackHome;
    ArrayList<BaiHat> arrayBaiHat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        anhXa();
        initData();
        _listView = (ListView) findViewById(R.id.listView);
        ListBaiHatAdapter adapter = new ListBaiHatAdapter(this, arrayBaiHat);
        _listView.setAdapter(adapter);


        Intent intent = getIntent();
        int baiHatId = intent.getIntExtra("id", 0);
        _listViewPlaying = (ListView) findViewById(R.id.listViewPlaying);

        ArrayList<BaiHat> arrayTemp = new ArrayList<>();
        for (BaiHat baiHat : arrayBaiHat) {
            if (baiHat.getId() == baiHatId) {
                arrayTemp.add(baiHat);
            }
        }
        ListBaiHatAdapter adapter_playing = new ListBaiHatAdapter(this, arrayTemp);
        _listViewPlaying.setAdapter(adapter_playing);
        btnBackHome.setOnClickListener(view -> {
            backHome();
        });
    }

    private void backHome() {
        finish();
    }

    private void initData() {
        AudioFileHandler audioFileHandler = new AudioFileHandler();
        List<String> audioFiles = audioFileHandler.getAllAudioFiles(getApplicationContext());
        for (int i = 0; i < audioFiles.size(); i++) {
            BaiHat musicInfo = audioFileHandler.retrieveMetadata(audioFiles.get(i), i + 1);
            if (musicInfo != null) {
                arrayBaiHat.add(musicInfo);
            }
        }
    }

    private void anhXa() {
        btnBackHome = (ImageButton) findViewById(R.id.buttonBackHome);
    }

}