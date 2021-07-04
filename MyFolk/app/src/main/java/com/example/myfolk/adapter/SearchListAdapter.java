package com.example.myfolk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myfolk.R;
import com.example.myfolk.entityClass.Folk;

import java.util.List;

public class SearchListAdapter extends BaseAdapter {
    private Context context;
    private List<Folk> list;
    public SearchListAdapter(Context context, List<Folk> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.search_list_item,null);
        ImageView imageView = v.findViewById(R.id.search_img);
        TextView textView = v.findViewById(R.id.search_title);
        Glide.with(v)
                .load(list.get(position).img)
                .into(imageView);
        textView.setText(list.get(position).title);
        return v;
    }
}
