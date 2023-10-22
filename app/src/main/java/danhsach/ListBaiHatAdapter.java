package danhsach;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.Intent;
import com.example.playmusic_group.BaiHat;
import com.example.playmusic_group.MainActivity;
import com.example.playmusic_group.R;

import java.util.ArrayList;

import context.DataMusic;

public class ListBaiHatAdapter extends BaseAdapter {
    private ArrayList<BaiHat> list;
    private Activity activity;

    private Context context;
    public ListBaiHatAdapter( Activity activity, ArrayList<BaiHat> list){
        this.list = list;
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.item_name, null);
        TextView textView = (TextView) view.findViewById(R.id.list_item_View_name);
        TextView caSi = (TextView) view.findViewById(R.id.list_item_view_ns);

        // Lưu ID của bài hát trong tag của view
        view.setTag(list.get(i).getId());

        textView.setText(list.get(i).getTenBaiHat());
        caSi.setText(list.get(i).getTenCaSi());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int baiHatId = (int) v.getTag(); // Lấy ID của bài hát từ tag của view
                PlayMucicAndBackHome(baiHatId);
            }
        });

        return view;
    }


    private void PlayMucicAndBackHome(int id) {
        Intent intentActiveHome = new Intent(activity, MainActivity.class);
        intentActiveHome.putExtra("playId",id);
        activity.startActivity(intentActiveHome);
    }
}
