package com.sinostar.assistant.ui.basecontrol;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.siberiadante.customdialoglib.CustomDialog;
import com.sinostar.assistant.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sinostar.assistant.base.ApplicationUtil.getContext;

public class baseControlActivity extends AppCompatActivity {
    @BindView( R.id.spinner )
    Spinner rSpinner;
    @BindView(R.id.button)
    Button rButton;


    int TAG1401 = 1000;
    int TAG1402 = 2000;
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

    @OnClick({R.id.button,R.id.buttonDialog,R.id.loginDialogButton,R.id.login2DialogButton})
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
            case R.id.loginDialogButton:
                loginDialogButtonClick();
                break;
            case R.id.login2DialogButton:
                login2DialogButtonClick();
                break;
        }
    }

    public void loginDialogButtonClick()
    {
        final EditText editText=new EditText( this );
        AlertDialog.Builder editDialog = new AlertDialog.Builder(this);
        editDialog.setTitle("弹出登录页面");
        editDialog.setIcon(R.mipmap.ic_launcher);

        //设置dialog布局
        editDialog.setView(editText);

        //设置按钮
        editDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(baseControlActivity.this,
                                editText.getText().toString().trim(),Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

        editDialog.create().show();
    }


    public void login2DialogButtonClick()
    {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();


        final RelativeLayout relativeLayout222=new RelativeLayout( this );
        relativeLayout222.setMinimumWidth( width );
        relativeLayout222.setPadding( 40,40,40,40 );
        final RelativeLayout relativeLayout=new RelativeLayout( this );
        relativeLayout.setMinimumWidth( width );
        //relativeLayout.setGravity( Gravity.FILL_VERTICAL );


        final LinearLayout linearLayout=new LinearLayout( this );
        linearLayout.setMinimumWidth(width);
        final TextView textView=new TextView(this);
        textView.setText("用户名:");
        final EditText editText=new EditText( this );
        editText.setId(TAG1401 );
        editText.setWidth( 650 );
        linearLayout.addView( textView );
        linearLayout.addView( editText );
        relativeLayout.addView( linearLayout );

        final RelativeLayout relativeLayout1=new RelativeLayout( this );
        relativeLayout1.setMinimumWidth( width );
        relativeLayout1.setPadding( 0,40,0,0 );
        final LinearLayout linearLayout1=new LinearLayout( this );
        linearLayout1.setMinimumWidth(width);
        final TextView textView1=new TextView(this);
        textView1.setText("密码:");
        final EditText editText1=new EditText( this );
        editText1.setId(TAG1402 );
        editText1.setWidth( 650 );
        linearLayout1.addView( textView1 );
        linearLayout1.addView( editText1 );
        relativeLayout1.addView( linearLayout1 );
//        final EditText editText2=new EditText( this );
//        linearLayout.addView( editText );
//        linearLayout.addView( editText2 );
        AlertDialog.Builder editDialog = new AlertDialog.Builder(this);
        editDialog.setTitle("弹出登录页面");
        editDialog.setIcon(R.mipmap.ic_launcher);
        relativeLayout222.addView( relativeLayout );
        relativeLayout222.addView( relativeLayout1 );
        //设置dialog布局
        editDialog.setView(relativeLayout222);
        //设置按钮
        editDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(baseControlActivity.this,
                        editText.getText().toString().trim(),Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        editDialog.create().show();
    }
}
