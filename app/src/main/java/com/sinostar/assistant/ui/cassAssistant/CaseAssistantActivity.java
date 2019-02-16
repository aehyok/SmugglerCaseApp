package com.sinostar.assistant.ui.cassAssistant;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.ui.cassAssistant.announce.Announce;
import com.sinostar.assistant.R;
import com.sinostar.assistant.utils.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sinostar.assistant.utils.FragmentUtil.addFragment;

/**
 * 办案助手主模块
 */
public class CaseAssistantActivity extends AppCompatActivity {

    @BindView(R.id.title_bar_text)
    TextView titleBarText;

    Context context;
    private FragmentUtil fragmentUtil;
    private Announce announceFragment;
    private int image1[]={R.drawable.ic_wssp_1, R.drawable.ic_hjhtsp_1, R.drawable.ic_wsslsp_1, R.drawable.ic_sqsp_1};
    private int image2[]={R.drawable.ic_wssp, R.drawable.ic_hjhtsp, R.drawable.ic_wsslsp, R.drawable.ic_sqsp};
    private ImageView imageView[];
    private TextView textView[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_assistant);
        ButterKnife.bind(this);
        context = CaseAssistantActivity.this;
//        imageView= new ImageView[]{bottomBarImage1, bottomBarImage2, bottomBarImage3, bottomBarImage4};
//        textView=new TextView[]{bottomBarText1, bottomBarText2, bottomBarText3, bottomBarText4};
        announceFragment = new Announce();
        addFragment(getSupportFragmentManager(), announceFragment, R.id.case_assistant_fragment_container);
        fragmentUtil = new FragmentUtil(this, announceFragment, R.id.case_assistant_fragment_container);
        init();
    }

    private void init() {
        titleBarText.setText(R.string.case_assistant_title_text);
    }

    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_back_text:
                finish();
                break;
//            case R.id.bottom_bar_first_layout:
//                setTextColor(this, 0,textView );
//                setImage(this, 0,image1,image2,imageView);
//                fragmentUtil.switchFragment(announceFragment);
//                break;
//            case R.id.bottom_bar_second_layout:
//                setTextColor(this, 1,textView );
//                setImage(this, 1,image1,image2,imageView);
//                break;
//            case R.id.bottom_bar_third_layout:
//                setTextColor(this, 2,textView );
//                setImage(this, 2,image1,image2,imageView);
//                break;
//            case R.id.bottom_bar_fourth_laout:
//                setTextColor(this, 3,textView );
//                setImage(this, 3,image1,image2,imageView);
//                break;
        }
    }

}
