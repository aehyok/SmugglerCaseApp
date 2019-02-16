package com.sinostar.assistant.ui.query;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.base.BaseActivity;
import com.sinostar.assistant.base.OnEditFocusChange;
import com.sinostar.assistant.utils.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sinostar.assistant.utils.FragmentUtil.addFragment;

public class DataQuery extends BaseActivity {
    Context context;
    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.data_query_layout)
    RelativeLayout dataQueryFragmentContainer;
    @BindView(R.id.data_query_raidoGroup)
    RadioGroup dataQueryRaidoGroup;
    @BindView(R.id.title_bar_back_image)
    ImageButton titleBarBackImage;
    private DataQueryPerson dataQueryPerson;
    private DataQueryCompany dataQueryCompany;
    private FragmentUtil fragmentUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_query);
        ButterKnife.bind(this);
        dataQueryPerson = new DataQueryPerson();
        dataQueryCompany = new DataQueryCompany();
        context = DataQuery.this;
        init();
        fragmentUtil = new FragmentUtil(this, dataQueryPerson, R.id.data_query_fragment_container);
        initListener();


    }

    private void initListener() {
        setOnEdittFoucusChange(new OnEditFocusChange() {
            @Override
            public void isEditextFoucus() {
                titleBarBackImage.requestFocusFromTouch();
            }
        });
        dataQueryRaidoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButton_person:
                        fragmentUtil.switchFragment(dataQueryPerson);
                        break;
                    case R.id.radioButton_company:
                        fragmentUtil.switchFragment(dataQueryCompany);
                        break;
                }
            }
        });
    }


    private void init() {
        titleBarText.setText("缉私记录查询");
        addFragment(getSupportFragmentManager(), dataQueryPerson, R.id.data_query_fragment_container);
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


        }
    }


}
