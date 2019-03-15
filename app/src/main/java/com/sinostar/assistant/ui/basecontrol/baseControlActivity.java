package com.sinostar.assistant.ui.basecontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sinostar.assistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class baseControlActivity extends AppCompatActivity {
    @BindView( R.id.spinner )
    Spinner rSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_base_control );
        ButterKnife.bind( this );
        String[] ctype = new String[]{"全部", "游戏", "电影", "娱乐", "图书"};
        //创建一个数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
        //设置下拉列表框的下拉选项样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rSpinner.setAdapter(adapter);
    }
}
