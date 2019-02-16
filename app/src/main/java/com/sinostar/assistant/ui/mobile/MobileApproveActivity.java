package com.sinostar.assistant.ui.mobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.ui.mobile.apply.ApplyApproveFragment;
import com.sinostar.assistant.ui.mobile.authorization.AuthorizationApproveFragment;
import com.sinostar.assistant.ui.mobile.agree.ApproveAgree;
import com.sinostar.assistant.ui.mobile.document.DocumentApproveFragment;
import com.sinostar.assistant.ui.mobile.link.LinkBackFragment;
import com.sinostar.assistant.R;
import com.sinostar.assistant.utils.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sinostar.assistant.utils.BottomBarUtil.setImage;
import static com.sinostar.assistant.utils.BottomBarUtil.setTextColor;
import static com.sinostar.assistant.utils.FragmentUtil.addFragment;

/**
 * 移动审批主模块
 */

public class MobileApproveActivity extends AppCompatActivity {


    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.mobile_approve_fragment_container)
    RelativeLayout fragmentContainer;
    @BindView(R.id.bottom_bar_text1)
    TextView bottomBarText1;
    @BindView(R.id.bottom_bar_text2)
    TextView bottomBarText2;
    @BindView(R.id.bottom_bar_text3)
    TextView bottomBarText3;
    @BindView(R.id.bottom_bar_text4)
    TextView bottomBarText4;
    @BindView(R.id.bottom_bar_image1)
    ImageView bottomBarImage1;
    @BindView(R.id.bottom_bar_image2)
    ImageView bottomBarImage2;
    @BindView(R.id.bottom_bar_image3)
    ImageView bottomBarImage3;
    @BindView(R.id.bottom_bar_image4)
    ImageView bottomBarImage4;
    @BindView(R.id.bottom_bar_fourth_laout)
    RelativeLayout bottomBarFourthLaout;

    private Fragment currentFragment;
    private DocumentApproveFragment documentApproveFragment;
    private LinkBackFragment linkBackFragment;
    private ApplyApproveFragment ApplyApproveFragment;
    private AuthorizationApproveFragment authorizationApproveFragment;
    private FragmentUtil fragmentUtil;
    ApproveAgree approveAgreeModel;
    private int image1[] = {R.drawable.wssp_1, R.drawable.hjhtsp_1, R.drawable.wsslsp_1, R.drawable.sqsp_1};
    private int image2[] = {R.drawable.wssp, R.drawable.hjhtsp, R.drawable.wsslsp, R.drawable.sqsp};
    private ImageView imageView[];
    private TextView textView[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_approve);
        ButterKnife.bind(this);
        bottomBarFourthLaout.setVisibility(View.VISIBLE);
        approveAgreeModel = new ApproveAgree();
        init();
        imageView = new ImageView[]{bottomBarImage1, bottomBarImage2, bottomBarImage3, bottomBarImage4};
        textView = new TextView[]{bottomBarText1, bottomBarText2, bottomBarText3, bottomBarText4};
        documentApproveFragment = new DocumentApproveFragment();
        linkBackFragment = new LinkBackFragment();
        ApplyApproveFragment = new ApplyApproveFragment();
        authorizationApproveFragment = new AuthorizationApproveFragment();
        setTextColor(this, 0, textView);
        setImage(this, 0, image1, image2, imageView);
        addFragment(getSupportFragmentManager(), documentApproveFragment, R.id.mobile_approve_fragment_container);
        fragmentUtil = new FragmentUtil(this, documentApproveFragment, R.id.mobile_approve_fragment_container);
        currentFragment = documentApproveFragment;
    }


    private void init() {
        titleBarText.setText(R.string.mobile_approve_title_text);
        bottomBarText1.setText(R.string.mobile_approve_first);
        bottomBarText2.setText(R.string.mobile_approve_second);
        bottomBarText3.setText(R.string.mobile_approve_third);
        bottomBarText4.setText(R.string.mobile_approve_fourth);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        switch (ApplicationUtil.getApproveType()){
//            case 1:
//                fragmentUtil.switchFragment(documentApproveFragment);
//                break;
//            case 2:
//                fragmentUtil.switchFragment(linkBackFragment);
//                break;
//        }
    }

    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text, R.id.bottom_bar_first_layout,
            R.id.bottom_bar_second_layout, R.id.bottom_bar_third_layout, R.id.bottom_bar_fourth_laout})
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
                fragmentUtil.switchFragment(documentApproveFragment);
                break;
            case R.id.bottom_bar_second_layout:
                setTextColor(this, 1, textView);
                setImage(this, 1, image1, image2, imageView);
                ApplicationUtil.setApproveType(2);
                fragmentUtil.switchFragment(linkBackFragment);
                break;
            case R.id.bottom_bar_third_layout:
                setTextColor(this, 2, textView);
                setImage(this, 2, image1, image2, imageView);
                fragmentUtil.switchFragment(ApplyApproveFragment);
                break;
            case R.id.bottom_bar_fourth_laout:
                setTextColor(this, 3, textView);
                setImage(this, 3, image1, image2, imageView);
                fragmentUtil.switchFragment(authorizationApproveFragment);
                break;
        }
    }
}
