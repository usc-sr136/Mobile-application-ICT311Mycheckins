package com.example.mycheckins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DemoAdapter extends BaseAdapter{

    private List<Item> dataset = new ArrayList<Item>();
    private Context activity;
    private static LayoutInflater inflater=null;

    public DemoAdapter(Context activity) {
        DatabaseHelper dbHandler = new DatabaseHelper(activity);
        ArrayList<Item> list1 = (ArrayList<Item>) dbHandler.getAllItems();
        for(int i=0;i<list1.size();i++)
        {
            dataset.add(list1.get(i));
        }
        this.activity = activity;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.row, null);
        Item cun = dataset.get(position);
        TextView txt1 =(TextView)v.findViewById(R.id.txt_title);
        txt1.setText(cun.getTitle());
        TextView txt2=(TextView)v.findViewById(R.id.txt_place);
        txt2.setText(cun.getPlace());
        TextView txt3=(TextView)v.findViewById(R.id.txt_date);
        txt3.setText(cun.getDate());
        return v;
    }

}
