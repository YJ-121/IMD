package com.example.myfolk.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfolk.R;
import com.example.myfolk.activity.DetailActivity;
import com.example.myfolk.adapter.RecommendListAdapter;
import com.example.myfolk.entityClass.Folk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecommendFragment extends Fragment {
    private View view;
    private RecommendListAdapter adapter;
    private RecyclerView recommendView;
    private String mUrl = "https://hello-cloudbase-1gvkvubxa63d3806-1305329240.ap-shanghai.app.tcloudbase.com";
    private int flag = 0;
    private List<Folk> folkList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
        view=inflater.inflate(R.layout.fragment_recommend,container,false);
        recommendView = view.findViewById(R.id.recommend_recycler);
        folkList = getFolkList();
        adapter = new RecommendListAdapter(folkList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        recommendView.setLayoutManager(layoutManager);
        recommendView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        adapter.setOnItemClickListener(new RecommendListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerView parent, View view, int position, String data) {
//                Intent intent = new Intent(getActivity(), DetailActivity.class);
//                Folk mFolk = folkList.get(position);
//                intent.putExtra("img",mFolk.img);
//                intent.putExtra("title",mFolk.title);
//                intent.putExtra("id",mFolk.id);
//                startActivity(intent);
//            }
//        });
        }
        return view;
    }

    public List<Folk> getFolkList(){
        ArrayList<Folk> folks = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient(); //??????http?????????
                Request request = new Request.Builder()
                        .url(mUrl+"/getRecommendList")  //???????????????????????????
                        .get().build(); //??????http??????
                Response response = null; //??????????????????
                try {
                    response = client.newCall(request).execute();
                    //????????????????????????????????????
                    String responseData = response.body().string(); //?????????????????????????????????JSON???????????????
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(jsonArray.length()>0){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject now = jsonArray.getJSONObject(i);
                            folks.add(new Folk(now.optString("_id"),now.optString("img"),now.optString("title")));
                        }

                    }
                    flag=1;

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        while (flag==0){}
        return folks;
    }

}
