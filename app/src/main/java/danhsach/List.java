package danhsach;

import static com.example.playmusic_group.R.id.listViewPlaying;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.playmusic_group.BaiHat;
import com.example.playmusic_group.MainActivity;
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
        anhXa();

        _listView = (ListView)findViewById(R.id.listView);
        ListBaiHatAdapter adapter = new ListBaiHatAdapter(this, arrayBaiHat);
        _listView.setAdapter(adapter);


        Intent intent = getIntent();
        int baiHatId = intent.getIntExtra("id", 0);
        _listViewPlaying = (ListView)findViewById(R.id.listViewPlaying);

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

    private void anhXa(){
        btnBackHome = (ImageButton) findViewById(R.id.buttonBackHome);
    }

}