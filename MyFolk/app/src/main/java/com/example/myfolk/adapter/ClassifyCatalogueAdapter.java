package com.example.myfolk.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myfolk.entityClass.Catalogue;
import com.example.myfolk.R;
import java.util.List;

public class ClassifyCatalogueAdapter extends BaseAdapter {
    private Context context;
    private List<Catalogue> list;
    private int mCurrentItem=0;
    public ClassifyCatalogueAdapter(Context context, List<Catalogue> list){
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
        View v = LayoutInflater.from(context).inflate(R.layout.classify_catalogue_item,null);
        TextView textView = v.findViewById(R.id.catalogue_content);
        textView.setText(list.get(position).name);
        if(mCurrentItem==position){
            textView.setTextColor(Color.RED);
        }else {
            textView.setTextColor(Color.BLACK);
        }
        return v;
    }

    public void setmCurrentItem(String id){
        mCurrentItem = Integer.parseInt(id);
    }
}
