package com.example.myfolk.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfolk.R;
import com.example.myfolk.activity.DetailActivity;
import com.example.myfolk.adapter.ClassifyCatalogueAdapter;
import com.example.myfolk.adapter.ClassifyContentAdapter;
import com.example.myfolk.entityClass.Catalogue;
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

public class ClassifyFragment extends Fragment {
    private View view;
    private ClassifyContentAdapter contentAdapter;
    private ClassifyCatalogueAdapter catalogueAdapter;
    private List<Folk> folkList;
    private String mUrl = "https://hello-cloudbase-1gvkvubxa63d3806-1305329240.ap-shanghai.app.tcloudbase.com";
    private List<Catalogue> catalogues;
    private int catalogueFlag = 0;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_classify,container,false);
        RecyclerView classifyView = view.findViewById(R.id.classify_content);
        ListView catalogueView = view.findViewById(R.id.classify_catalogue);
        folkList = getFolklist();
        catalogues = new ArrayList<>();
        contentAdapter = new ClassifyContentAdapter(folkList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 5);
        classifyView.setLayoutManager(layoutManager);
        classifyView.setAdapter(contentAdapter);
//        contentAdapter.setOnItemClickListener(new ClassifyContentAdapter.OnItemClickListener() {
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
        catalogueAdapter = new ClassifyCatalogueAdapter(view.getContext(),getCatalogueList());
        catalogueView.setAdapter(catalogueAdapter);
        catalogueView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                catalogueAdapter.setmCurrentItem(position+"");
                catalogueAdapter.notifyDataSetChanged();
                folkList.clear();
                folkList.addAll(changeFolklist(position+""));
                contentAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }
    public List<Catalogue> getCatalogueList() {
        catalogueFlag = 0;
        catalogues.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient(); //创建http客户端
                Request request = new Request.Builder()
                        .url(mUrl+"/getClassifyList")  //后端请求接口的地址
                        .get().build(); //创建http请求
                Response response = null; //执行发送指令
                try {
                    response = client.newCall(request).execute();
                    //获取后端回复过来的返回值
                    String responseData = response.body().string(); //获取后端接口返回过来的JSON格式的结果
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        catalogues.add(new Catalogue(jsonArray.getJSONObject(i).getString("_id"),jsonArray.getJSONObject(i).getString("name")));
                    }
                    catalogueFlag = 1;

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        while (catalogueFlag==0){}
        return catalogues;
    }

    public List<Folk> getFolklist(){
        List<Folk> folks=new ArrayList<>();
        Thread folkListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                folks.clear();
                OkHttpClient client = new OkHttpClient(); //创建http客户端
                Request request = new Request.Builder()
                        .url(mUrl+"/getFolkList?type=0")  //后端请求接口的地址
                        .get().build(); //创建http请求
                Response response = null; //执行发送指令
                try {
                    response = client.newCall(request).execute();
                    //获取后端回复过来的返回值
                    String responseData = response.body().string(); //获取后端接口返回过来的JSON格式的结果
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(jsonArray.length()>0){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject now = jsonArray.getJSONObject(i);
                            folks.add(new Folk(now.optString("_id"),now.optString("img"),now.optString("title")));

                        }}

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        folkListThread.start();
        while(folkListThread.getState().toString().equals("RUNNABLE")){}
        return folks;
    }

    public List<Folk> changeFolklist(String id) {
        List<Folk> folks=new ArrayList<>();
        Thread folkListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                folks.clear();
                OkHttpClient client = new OkHttpClient(); //创建http客户端
                Request request = new Request.Builder()
                        .url(mUrl+"/getFolkList?type="+id)  //后端请求接口的地址
                        .get().build(); //创建http请求
                Response response = null; //执行发送指令
                try {
                    response = client.newCall(request).execute();
                    //获取后端回复过来的返回值
                    String responseData = response.body().string(); //获取后端接口返回过来的JSON格式的结果
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(jsonArray.length()>0){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject now = jsonArray.getJSONObject(i);
                            folks.add(new Folk(now.optString("_id"),now.optString("img"),now.optString("title")));
                        }}


                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        folkListThread.start();
        while(folkListThread.getState().toString().equals("RUNNABLE")){}
        return folks;
    }

    public void onDestoryView(){
        super.onDestroyView();
        if(null != view){
            ((ViewGroup)view.getParent()).removeView(view);
        }
    }
}
