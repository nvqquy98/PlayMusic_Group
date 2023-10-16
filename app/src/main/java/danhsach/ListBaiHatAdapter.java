package danhsach;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.playmusic_group.BaiHat;
import com.example.playmusic_group.R;

import java.util.ArrayList;

import context.DataMusic;

public class ListBaiHatAdapter extends BaseAdapter {

    private ArrayList<BaiHat> list;
    private  Activity activity;
    public ListBaiHatAdapter(Activity activity, ArrayList<BaiHat> list){
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
        LayoutInflater intent = activity.getLayoutInflater();
        view = intent.inflate(R.layout.item_name, null);
        TextView textView = (TextView)view.findViewById(R.id.list_item_View_name);
        textView.setText(list.get(i).getTenBaiHat());
        TextView caSi = (TextView)view.findViewById(R.id.list_item_view_ns);
        caSi.setText(list.get(i).getTenCaSi());
        return view;
    }
}
