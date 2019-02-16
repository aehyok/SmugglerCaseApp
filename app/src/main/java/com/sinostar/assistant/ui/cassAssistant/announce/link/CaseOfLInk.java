package com.sinostar.assistant.ui.cassAssistant.announce.link;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sinostar.assistant.base.ApplicationUtil;

import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.bean.CaseLinkAction;
import com.sinostar.assistant.bean.CaseLinkCase;
import com.sinostar.assistant.bean.GuideLine;
import com.sinostar.assistant.R;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.utils.ClickUtil;
import com.sinostar.assistant.utils.WaterMarkBgUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 案件详情
 */
public class CaseOfLInk extends AppCompatActivity {

    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.case_num)
    TextView caseNum;
    @BindView(R.id.case_name)
    TextView caseName;
    @BindView(R.id.case_state)
    TextView caseState;
    @BindView(R.id.left_list)
    ListView leftList;
    @BindView(R.id.right_list)
    ExpandableListView rightList;
    @BindView(R.id.case_of_link_layout)
    RelativeLayout caseOfLinkLayout;
    @BindView(R.id.title_text)
    TextView titleText;

    private Context context;
    private String caseId;
    private String caseType = "行政一般案件";
    private String guideLineDataJson;
    private Gson gson;
    private GuideLine.DataBean guideLineData;
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;
    private String leftDataJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_of_link);
        ButterKnife.bind(this);
        context = CaseOfLInk.this;
        caseOfLinkLayout.setBackground(new WaterMarkBgUtil(ApplicationUtil.getWaterMarkText()));
        leftAdapter = new LeftAdapter(context);
        rightAdapter = new RightAdapter(context);
        leftList.setAdapter(leftAdapter);
        rightList.setAdapter(rightAdapter);
        init();
        initListener();
        getLeftData();
    }


    private void init() {
        titleBarText.setText("详情");
        Intent intent = getIntent();
        guideLineDataJson = intent.getStringExtra("guideLineDataJson");
        gson = new Gson();
        guideLineData = gson.fromJson(guideLineDataJson, GuideLine.DataBean.class);
        caseId = guideLineData.getAJID() + "";
        caseNum.setText("编号：" + guideLineData.getAJBH());
        caseName.setText("名称：" + guideLineData.getAJMC());
        caseState.setText("状态：" + guideLineData.getSTATENAME());
    }

    private void initListener() {
        rightList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if (!ClickUtil.isFastClick()) {
                    startActivity(new Intent(context, MapActivity.class));
                }
                    return false;
                }

        });
        leftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                leftAdapter.getSelect(i);
                List<CaseLinkCase.CaseOfLinkBean> data = gson.fromJson(leftDataJson, new TypeToken<List<CaseLinkCase.CaseOfLinkBean>>() {
                }.getType());
                getRightData(data.get(i).getId() + "",data.get(i).getLinkName());
            }
        });

    }

    private void getLeftData() {
        ObserverOnNextListener listener = new ObserverOnNextListener<CaseLinkCase>() {
            @Override
            public void onNext(CaseLinkCase result) {
                if (result != null) {
                    leftDataJson = gson.toJson(result.getCaseOfLink());
                    leftAdapter.getData(result.getCaseOfLink());
                    for(int i=0;i<result.getCaseOfLink().size();i++){
                        if(result.getCurrentStatusOrder()==result.getCaseOfLink().get(i).getDisplayOrder()){
                            leftAdapter.getSelect(i);
                            getRightData(result.getCaseOfLink().get(i).getId() + "",result.getCaseOfLink().get(0).getLinkName());
                        }
                    }

                }
            }

            @Override
            public void onError(Throwable e) {

            }
        };
        NetMethods.getLinkCase(new MyObserver<CaseLinkCase>(context,listener), caseId, caseType);
    }

    private void getRightData(String linkId,String name) {
        ObserverOnNextListener listener = new ObserverOnNextListener<List<CaseLinkAction>>() {
            @Override
            public void onNext(List<CaseLinkAction> result) {
                if (result != null) {
                    rightAdapter.getData(result);

                }
            }

            @Override
            public void onError(Throwable e) {

            }
        };titleText.setText(name);
        NetMethods.getLinkAction(new MyObserver<List<CaseLinkAction>>(context, listener), linkId);
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
