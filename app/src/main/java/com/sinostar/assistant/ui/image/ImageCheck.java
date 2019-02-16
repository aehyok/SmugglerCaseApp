package com.sinostar.assistant.ui.image;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.sinostar.assistant.R;


import java.util.ArrayList;
import java.util.List;

/**
 * 照片查看页面
 */
public class ImageCheck extends Activity {

    private int currentPosition;
    private ImageCheckAdapter adapter;
    private TextView mTvImageCount;
    private List<String> Urls;
    private ArrayList<String> imagePath = new ArrayList<>();
    private int i;
    private Activity context;
    private ViewPager mViewPager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=ImageCheck.this;
        setContentView(R.layout.image_check);
        QMUIStatusBarHelper.translucent(this);
        mViewPager = findViewById(R.id.image_check_viewpager);
        mTvImageCount =  findViewById(R.id.image_check_count);
        Intent intent=getIntent();
        Urls=intent.getStringArrayListExtra("imageUrl");
        String s= intent.getStringExtra("imagePosition");
        if(intent.getBooleanExtra("showCount",true)){
            mTvImageCount.setVisibility(View.GONE);
        }
        currentPosition=Integer.parseInt(s);

        initData();

    }

    private void initData() {
        adapter = new ImageCheckAdapter();
        adapter.getImageCheckData(Urls, context);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition, false);
        mTvImageCount.setText(" \t"+(currentPosition+1) + "/" + Urls.size()+" \t");
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                super.onPageSelected(position);
                currentPosition = position;
                i=position;
                mTvImageCount.setText(" \t"+(currentPosition+1 )+ "/" + Urls.size()+" \t");

            }
        });

    }

}
