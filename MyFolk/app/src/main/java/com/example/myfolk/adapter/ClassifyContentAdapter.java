package com.example.myfolk.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfolk.R;
import com.example.myfolk.activity.DetailActivity;
import com.example.myfolk.entityClass.Folk;

import java.util.List;

public class ClassifyContentAdapter extends RecyclerView.Adapter <ClassifyContentAdapter.MyViewHolder> {
    private List<Folk> folkList;
//    private com.example.myfolk.adapter.ClassifyContentAdapter.OnItemClickListener onItemClickListener;
    private RecyclerView recyclerView;
    public ClassifyContentAdapter(List<Folk> folkList){
        this.folkList = folkList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_content_item,parent,false);
//        view.setOnClickListener(this);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       Glide.with(holder.itemView).load(folkList.get(position).img).into(holder.classify_img);
        holder.classify_title.setText(folkList.get(position).title);
    }

    @Override
    public int getItemCount() {
        return folkList.size();
    }
//
//    @Override
//    public void onClick(View v) {
//        int position = recyclerView.getChildAdapterPosition(v);
//        //程序执行到此，会去执行具体实现的onItemClick()方法
//        if (onItemClickListener!=null){
//            onItemClickListener.onItemClick(recyclerView,v,position,folkList.get(position).id);
//        }
//    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView classify_img;
        private TextView classify_title;
        public MyViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    Folk mFolk = folkList.get(getAdapterPosition());
                    intent.putExtra("img",mFolk.img);
                    intent.putExtra("title",mFolk.title);
                    intent.putExtra("id",mFolk.id);
                    itemView.getContext().startActivity(intent);
                }
            });
            classify_img = itemView.findViewById(R.id.classify_img);
            classify_title = itemView.findViewById(R.id.classify_title);

        }
    }

//    public interface OnItemClickListener{
//        //参数（父组件，当前单击的View,单击的View的位置，数据）
//        void onItemClick(RecyclerView parent,View view, int position, String data);
//    }
//    public void setOnItemClickListener(com.example.myfolk.adapter.ClassifyContentAdapter.OnItemClickListener onItemClickListener){
//        this.onItemClickListener = onItemClickListener;
//    }

//    /**
//     *   将RecycleView附加到Adapter上
//     */
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        this.recyclerView= recyclerView;
//    }
//    /**
//     *   将RecycleView从Adapter解除
//     */
//    @Override
//    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
//        super.onDetachedFromRecyclerView(recyclerView);
//        this.recyclerView = null;
//    }
}
