package com.example.myfolk.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myfolk.R;
import com.example.myfolk.adapter.MyFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,ViewPager.OnPageChangeListener {

    private RadioGroup rg_tab_bar;
    private RadioButton rb_recommend;
    private RadioButton rb_classify;
    private RadioButton rb_search;
    private ViewPager vpager;
    private MyFragmentPagerAdapter mAdapter;

    //表明页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        initViews();
        rb_recommend.setChecked(true);
    }

    private void initViews() {
        rg_tab_bar =  findViewById(R.id.rg_tab_bar);
        rb_recommend = findViewById(R.id.rb_recommend);
        rb_classify = findViewById(R.id.rb_classify);
        rb_search = findViewById(R.id.rb_search);
        rg_tab_bar.setOnCheckedChangeListener(this);

        vpager = findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_recommend:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_classify:
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rb_search:
                vpager.setCurrentItem(PAGE_THREE);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    rb_recommend.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb_classify.setChecked(true);
                    break;
                case PAGE_THREE:
                    rb_search.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}