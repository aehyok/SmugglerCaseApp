package com.sinostar.assistant.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.sinostar.assistant.R;
import com.sinostar.assistant.Utils.BottomNewBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewMainActivity extends AppCompatActivity {
    @BindView(R.id.bottom_bar_image1)
    ImageView bottomBarImage1;
    @BindView(R.id.bottom_bar_text1)
    TextView bottomBarText1;
    @BindView(R.id.bottom_bar_image2)
    ImageView bottomBarImage2;
    @BindView(R.id.bottom_bar_text2)
    TextView bottomBarText2;
    @BindView(R.id.bottom_bar_image3)
    ImageView bottomBarImage3;
    @BindView(R.id.bottom_bar_text3)
    TextView bottomBarText3;
    @BindView(R.id.bottom_bar_image4)
    ImageView bottomBarImage4;
    @BindView(R.id.bottom_bar_text4)
    TextView bottomBarText4;

    private int image[] = {R.drawable.home, R.drawable.home_news, R.drawable.home_order, R.drawable.home_mine};
    private int imageSelect[] = {R.drawable.home_select, R.drawable.home_news_select, R.drawable.home_order_select, R.drawable.home_mine_select};
    private ImageView imageviews[];
    private TextView textViews[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_new_main );
        ButterKnife.bind(this);
        QMUIStatusBarHelper.translucent(this);
        imageviews = new ImageView[]{bottomBarImage1, bottomBarImage2, bottomBarImage3, bottomBarImage4};
        textViews = new TextView[]{bottomBarText1, bottomBarText2, bottomBarText3, bottomBarText4};
        bottomBarImage1.setBackgroundResource(R.drawable.home_select);
        bottomBarText1.setTextColor(getResources().getColor(R.color.login_button));
    }

    @OnClick({R.id.bottom_bar_first_layout, R.id.bottom_bar_second_layout, R.id.bottom_bar_third_layout, R.id.bottom_bar_fourth_laout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bottom_bar_first_layout:
                //fragmentUtil.switchFragment(homeFragment);
                BottomNewBarUtil.setImage(this, 0, image, imageSelect, imageviews);
                BottomNewBarUtil.setTextColor(this, 0, textViews);
                break;
            case R.id.bottom_bar_second_layout:
                //fragmentUtil.switchFragment(newsFragment);
                BottomNewBarUtil.setImage(this, 1, image, imageSelect, imageviews);
                BottomNewBarUtil.setTextColor(this, 1, textViews);
                break;
            case R.id.bottom_bar_third_layout:
                //fragmentUtil.switchFragment(orderFragment);
                BottomNewBarUtil.setImage(this, 2, image, imageSelect, imageviews);
                BottomNewBarUtil.setTextColor(this, 2, textViews);
                break;
            case R.id.bottom_bar_fourth_laout:
                //fragmentUtil.switchFragment(mineFragment);
                BottomNewBarUtil.setImage(this, 3, image, imageSelect, imageviews);
                BottomNewBarUtil.setTextColor(this, 3, textViews);
                break;
        }
    }
}
