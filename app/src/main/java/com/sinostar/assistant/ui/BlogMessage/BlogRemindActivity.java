package com.sinostar.assistant.ui.BlogMessage;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.utils.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sinostar.assistant.utils.BottomBarUtil.setImage;
import static com.sinostar.assistant.utils.BottomBarUtil.setTextColor;
import static com.sinostar.assistant.utils.FragmentUtil.addFragment;

public class BlogRemindActivity extends AppCompatActivity {

    @BindView( R.id.title_bar_text )
    TextView titleBarText;

    @BindView(R.id.bottom_bar_text1)
    TextView bottomBarText1;
    @BindView(R.id.bottom_bar_text2)
    TextView bottomBarText2;
    @BindView(R.id.bottom_bar_text3)
    TextView bottomBarText3;

    @BindView(R.id.bottom_bar_image1)
    ImageView bottomBarImage1;
    @BindView(R.id.bottom_bar_image2)
    ImageView bottomBarImage2;
    @BindView(R.id.bottom_bar_image3)
    ImageView bottomBarImage3;




    private ImageView imageView[];
    private TextView textView[];
    private int image1[] = {R.drawable.wssp_1, R.drawable.hjhtsp_1, R.drawable.wsslsp_1, R.drawable.sqsp_1};
    private int image2[] = {R.drawable.wssp, R.drawable.hjhtsp, R.drawable.wsslsp, R.drawable.sqsp};
    private Fragment currentFragment;
    private BlogHomeFragment homeFragment;
    private BlogEssenceFragment essenceFragment;
    private BlogCandidateFragment candidateFragment;
    FragmentUtil fragmentUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_blog_remind );
        ButterKnife.bind( this );
        titleBarText.setText( "cnblogs.com" );

        imageView = new ImageView[]{bottomBarImage1, bottomBarImage2, bottomBarImage3};
        textView = new TextView[]{bottomBarText1, bottomBarText2, bottomBarText3};

        homeFragment=new BlogHomeFragment();
        essenceFragment=new BlogEssenceFragment();
        candidateFragment=new BlogCandidateFragment();
        com.sinostar.assistant.utils.BottomBarUtil.setTextColor(this, 0, textView);
        com.sinostar.assistant.utils.BottomBarUtil.setImage(this, 0, image1, image2, imageView);
        addFragment(getSupportFragmentManager(), homeFragment, R.id.home_fragment_container);
        fragmentUtil = new FragmentUtil(this,homeFragment,R.id.home_fragment_container);
        currentFragment = homeFragment;

        //com.sinostar.assistant.utils.BottomBarUtil.setTextColor( this,R.drawable.bg_btn_dis, );
    }

    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text, R.id.bottom_bar_first_layout,
            R.id.bottom_bar_second_layout, R.id.bottom_bar_third_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_back_text:
                finish();
                break;
            case R.id.bottom_bar_first_layout:
                setTextColor(this, 0, textView);
                setImage(this, 0, image1, image2, imageView);
                ApplicationUtil.setApproveType(1);
                fragmentUtil.switchFragment(homeFragment);
                break;
            case R.id.bottom_bar_second_layout:
                setTextColor(this, 1, textView);
                setImage(this, 1, image1, image2, imageView);
                ApplicationUtil.setApproveType(2);
                fragmentUtil.switchFragment(essenceFragment);
                break;
            case R.id.bottom_bar_third_layout:
                setTextColor(this, 2, textView);
                setImage(this, 2, image1, image2, imageView);
                fragmentUtil.switchFragment(candidateFragment);
                break;
        }
    }
}
