package com.sinostar.assistant.ui.basecontrol;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.sinostar.assistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class baseControlActivity extends AppCompatActivity {
    @BindView( R.id.spinner )
    Spinner rSpinner;
    @BindView(R.id.button)
    Button rButton;
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
    @OnClick({R.id.button,R.id.buttonDialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                Toast.makeText(this,"弹出框信息", Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonDialog:
                final android.app.AlertDialog alertDialog1 = new android.app.AlertDialog.Builder(this)
                        .setTitle("提示标题")
                        .setMessage("提示内容")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton( "确定按钮", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(baseControlActivity.this,"进入确认按钮事件", Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        } )
                        .setNegativeButton( "取消按钮", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText( baseControlActivity.this, "进入取消按钮事件", Toast.LENGTH_LONG ).show();
                                dialog.cancel();
                            }
                        })
                        .setNeutralButton( "第三个按钮", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText( baseControlActivity.this, "进入第三个按钮事件", Toast.LENGTH_SHORT ).show();
                                dialog.cancel();
                            }
                        } )
                        .create();
                alertDialog1.show();
                break;
        }
    }
}
