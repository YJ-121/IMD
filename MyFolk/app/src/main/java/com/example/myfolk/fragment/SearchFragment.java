package com.example.myfolk.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfolk.R;
import com.example.myfolk.activity.DetailActivity;
import com.example.myfolk.activity.MainActivity;
import com.example.myfolk.adapter.SearchListAdapter;
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

public class SearchFragment extends Fragment {
    private View view;
    private SearchListAdapter adapter;
    private List<Folk> folks;
    private List<Folk> folkList;
    private String mUrl = "https://hello-cloudbase-1gvkvubxa63d3806-1305329240.ap-shanghai.app.tcloudbase.com";
    private int flag=0;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_search,container,false);
        final ListView listView = view.findViewById(R.id.search_list);
        final EditText editText = view.findViewById(R.id.edit_search);
        final ImageButton imageButton = view.findViewById(R.id.search);
        final TextView textView = view.findViewById(R.id.no_result);
        folkList = new ArrayList<>();
        folks = new ArrayList<>();
        imageButton.setOnClickListener(v -> {
            folks.clear();
            String key = editText.getText().toString();
            flag = 0;
            folks = getSearchList(key);
            while (flag==0){}
            if(folks.isEmpty()){
                textView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }else {
                textView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                adapter = new SearchListAdapter(view.getContext(),folks);
                listView.setAdapter(adapter);}
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Folk mFolk = folks.get(position);
                intent.putExtra("img",mFolk.img);
                intent.putExtra("title",mFolk.title);
                intent.putExtra("id",mFolk.id);
                startActivity(intent);
            }
        });
        return view;
    }

    public List<Folk> getSearchList(String key){

        List<Folk> folks=new ArrayList<>();
        Thread folkListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                folks.clear();
                OkHttpClient client = new OkHttpClient(); //创建http客户端
                Request request = new Request.Builder()
                        .url(mUrl+"/search?title="+key)  //后端请求接口的地址
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
                        }
                        folkList.clear();
                        folkList.addAll(folks);
                    }

                    flag=1;

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        folkListThread.start();
        while(folkListThread.getState().toString().equals("RUNNABLE")){}
        return folkList;
    }

    public void onDestoryView(){
        super.onDestroyView();
        if(null != view){
            ((ViewGroup)view.getParent()).removeView(view);
        }
    }
}
