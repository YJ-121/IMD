package com.example.myfolk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myfolk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {
    private String id;
    private String img;
    private String title;
    private String data;
    private String anotherName;
    private String introduction;
    private ArrayList<String> name;
    private ArrayList<String> content;
    private String custom="";
    private ImageView imageView;
    private TextView titleView;
    private TextView dataView;
    private TextView anotherNameView;
    private TextView introductionView;
    private TextView customView;
    private String mUrl = "https://hello-cloudbase-1gvkvubxa63d3806-1305329240.ap-shanghai.app.tcloudbase.com";
    private int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.detail);
        flag=0;
        initData();
        while (flag==0){}
        initView();
    }

    public Thread getData = new Thread(new Runnable() {
        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient(); //创建http客户端
            Request request = new Request.Builder()
                    .url(mUrl+"/detail?id="+id)  //后端请求接口的地址
                    .get().build(); //创建http请求
            Response response = null; //执行发送指令
            try {
                response = client.newCall(request).execute();
                //获取后端回复过来的返回值
                String responseData = response.body().string(); //获取后端接口返回过来的JSON格式的结果
                JSONObject jsonObject = new JSONObject(responseData);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if(jsonArray.length()>0){
                    JSONObject now = jsonArray.getJSONObject(0);
                    JSONArray now_custom = now.optJSONArray("custom");
                    for(int j=0;j<now_custom.length();j++){
                        custom=custom+"<strong>· "+now_custom.getJSONObject(j).optString("name")+"</strong>"+"<br>"+
                                now_custom.getJSONObject(j).getString("content")+"<br><br>";
                    }
                    data = now.optString("data");
                    anotherName = now.optString("anotherName");
                    introduction = now.optString("introduction");

                }
                flag=1;


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    });

    private void initData(){
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        img = intent.getStringExtra("img");
        title = intent.getStringExtra("title");
        getData.start();
        while (getData.getState().toString().equals("RUNNABLE")){}

    }

    private void initView(){
        imageView = findViewById(R.id.detail_img);
        titleView = findViewById(R.id.detail_title);
        dataView = findViewById(R.id.detail_data);
        anotherNameView = findViewById(R.id.detail_anotherName);
        introductionView = findViewById(R.id.detail_introduction);
        customView = findViewById(R.id.detail_custom);
        Glide.with(this)
                .load(img)
                .into(imageView);
        titleView.setText(title);
        dataView.setText(data);
        anotherNameView.setText(anotherName);
        introductionView.setText(Html.fromHtml(introduction));
        customView.setText(Html.fromHtml(custom));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getData.interrupt();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getData.interrupt();
        finish();
    }
}
