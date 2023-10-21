package danhsach;

import static com.example.playmusic_group.R.id.listViewPlaying;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.playmusic_group.BaiHat;
import com.example.playmusic_group.R;

import java.util.ArrayList;

import context.DataMusic;

public class List extends AppCompatActivity {
    private DataMusic data = new DataMusic();
    private ListView _listView;
    private ListView _listViewPlaying;
    ImageButton btnBackHome;
    ArrayList<BaiHat> arrayBaiHat = data.arrayBaiHat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        _listView = (ListView)findViewById(R.id.listView);
        ListBaiHatAdapter adapter = new ListBaiHatAdapter(this, arrayBaiHat);
        _listView.setAdapter(adapter);

        _listViewPlaying = (ListView)findViewById(R.id.listViewPlaying);
        ListBaiHatAdapter adapter_playing = new ListBaiHatAdapter(this, arrayBaiHat);
        _listViewPlaying.setAdapter(adapter_playing);
        anhXa();
        btnBackHome.setOnClickListener(view -> {
            backHome();
        });
    }

    private void backHome() {
//        Intent intentActiveList = new Intent(this, Activity.class);
//        startActivity(intentActiveList);
        finish();
    }

    private void anhXa(){
        btnBackHome = (ImageButton) findViewById(R.id.buttonBackHome);
    }

}