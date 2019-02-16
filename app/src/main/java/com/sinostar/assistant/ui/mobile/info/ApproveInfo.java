package com.sinostar.assistant.ui.mobile.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.ui.mobile.agree.ApproveAgree;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.bean.Document;
import com.sinostar.assistant.bean.LinkBackInfo;
import com.sinostar.assistant.R;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.utils.LogUtil;
import com.sinostar.assistant.utils.OnMultiClickListener;
import com.sinostar.assistant.utils.OnMultiItemClickListener;
import com.sinostar.assistant.utils.WaterMarkBgUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 待审批详情
 */

public class ApproveInfo extends AppCompatActivity {

    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.approve_info_document_list)
    ListView approveInfoDocumentList;
    @BindView(R.id.approve_info_material_list)
    ListView approveInfoMaterialList;
    ApproveInfoDocumentAdapter approveInfonAdapter;
    ApproveInfoAttachmentCountAdapter approveInfoAttachmentCountAdapter;
    Context context;
    String itemId;
    @BindView(R.id.approve_info_layout)
    RelativeLayout approveInfoLayout;

    @BindView(R.id.approve_info_button_layout)
    LinearLayout approveInfoButtonLayout;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.no_net_laoyout)
    RelativeLayout noNetLaoyout;
    @BindView(R.id.title_text)
    TextView titleText1;
    @BindView(R.id.approve_info_list_layout1)
    RelativeLayout approveInfoListLayout1;
    @BindView(R.id.approve_info_list_layout2)
    RelativeLayout approveInfoListLayout2;
    @BindView(R.id.link_back_num)
    TextView linkBackNum;
    @BindView(R.id.link_back_name)
    TextView linkBackName;
    @BindView(R.id.link_back_state)
    TextView linkBackState;
    @BindView(R.id.link_back_reason)
    TextView linkBackReason;
    @BindView(R.id.link_back_info_layout)
    RelativeLayout linkBackInfoLayout;


    private Gson gson;
    private String dataString;
    private boolean isHaveData;
    private boolean isReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_info);
        ButterKnife.bind(this);
        ApplicationUtil.setBzid("");
        ApplicationUtil.setActionId("");

        titleText1.setText("待审批文书列表");
        approveInfoLayout.setBackground(new WaterMarkBgUtil(ApplicationUtil.getWaterMarkText()));
        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        isReport= intent.getBooleanExtra("isReport",false);
        gson = new Gson();
        titleBarText.setText("待审批详情");
        context = ApproveInfo.this;
        approveInfonAdapter = new ApproveInfoDocumentAdapter(context);
        approveInfoAttachmentCountAdapter = new ApproveInfoAttachmentCountAdapter(context);
        approveInfoDocumentList.setAdapter(approveInfonAdapter);
        approveInfoMaterialList.setAdapter(approveInfoAttachmentCountAdapter);
        initListener();
        getData();


    }

    /**
     * 联调待审批文书列表数据
     */
    private void getData() {

        /**
         * 文书审批详情
         */
        ObserverOnNextListener listener = new ObserverOnNextListener<Document>() {
            @Override
            public void onNext(Document result) {
                if (result != null) {
                    if(!isReport){
                        ApplicationUtil.setBzid(result.getBZID());
                        getPassButton();
                        if (result.getDYSX() != null && result.getDYSX().size() != 0) {
                            dataString = gson.toJson(result);
                            approveInfoListLayout1.setVisibility(View.VISIBLE);
                            List<Document.DYSXBean> data = result.getDYSX();
                            LogUtil.d("待审批事件文书列表", dataString);
                            approveInfonAdapter.getData(data);
                            isHaveData = true;
                        }
                        //随附文书列表
                        if (result.getAttachmentCount() != 0) {
                            approveInfoListLayout2.setVisibility(View.VISIBLE);
                            isHaveData = true;
                        }
                    }else{
                        if(result.getItemInfo()!=null){
                            dataString = gson.toJson(result);
                            ApplicationUtil.setBzid(result.getItemInfo().getBZID());
                            if (result.getItemInfo().getDYSX() != null && result.getItemInfo().getDYSX().size() != 0) {
                                approveInfoListLayout1.setVisibility(View.VISIBLE);
                                List<Document.DYSXBean> data= gson.fromJson(gson.toJson(result.getItemInfo().getDYSX()),
                                        new TypeToken<List<Document.DYSXBean>>(){}.getType());
                                dataString = gson.toJson(result);
                                LogUtil.d("待审批事件文书列表", dataString);
                                approveInfonAdapter.getData(data);
                                isHaveData = true;
                            }
                        }

                        if(result.getButtonList()!=null&&result.getButtonList().size()!=0){
                            List<LinkBackInfo.ButtonListBean> data= gson.fromJson(gson.toJson(result.getButtonList()),
                                    new TypeToken<List<LinkBackInfo.ButtonListBean>>(){}.getType());
                            isHaveData=true;
                            addButton(result.getButtonList().size(), data);
                        }
                        isEmpty(isHaveData);
                    }
                }else{
                    isEmpty(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                isEmpty(false);

            }
        };

        /**
         * 环节回退详情
         */
        ObserverOnNextListener listener1 = new ObserverOnNextListener<LinkBackInfo>() {
            @Override
            public void onNext(LinkBackInfo result) {
                if(result!=null){
                if(result.getItemInfo()!=null&&result.getItemInfo().size()!=0){
                    LinkBackInfo.ItemInfoBean data = result.getItemInfo().get(0);
                        linkBackInfoLayout.setVisibility(View.VISIBLE);
                        linkBackNum.setText(data.getAJBH());
                        linkBackName.setText(data.getAJMC());
                        linkBackState.setText(data.getSOURCESTATE());
                        linkBackReason.setText(data.getXJYJ());
                    isHaveData = true;
                }
                if ( result.getButtonList() != null&&result.getButtonList().size()!=0) {
                    ApplicationUtil.setBzid(itemId);
                    isHaveData = true;
                    addButton(result.getButtonList().size(), result.getButtonList());
                }
                }else{
                    isEmpty(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                isEmpty(false);

            }
        };

        switch (ApplicationUtil.getApproveType()) {
            case 1:
                if(isReport){
                    NetMethods.getApproveReportInfo(new MyObserver<Document>(context, listener), itemId, ApplicationUtil.getUserId());
                }else{
                    NetMethods.getDocumentInfo(new MyObserver<Document>(context, listener), itemId);
                }
                break;
            case 2:
                NetMethods.getLinkBackInfo(new MyObserver<LinkBackInfo>(context, listener1), itemId, ApplicationUtil.getUserId());
                break;

        }


    }

    private void isEmpty(final boolean isHaveData) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressLayout.setVisibility(View.GONE);
                if (isHaveData) {
                    emptyLayout.setVisibility(View.GONE);
                } else {
                    emptyLayout.setVisibility(View.VISIBLE);
                }
            }
        }, 500)
        ;
    }

    /**
     * 联调文书审批操作按钮（通过、不通过、报上级）
     */
    private void getPassButton() {
        ObserverOnNextListener listener = new ObserverOnNextListener<List<LinkBackInfo.ButtonListBean>>() {
            @Override
            public void onNext(List<LinkBackInfo.ButtonListBean> result) {
                if (result.size() != 0) {
                    isHaveData = true;
                    addButton(result.size(), result);
                }
                isEmpty(isHaveData);
            }

            @Override
            public void onError(Throwable e) {
                isEmpty(isHaveData);
            }
        };
        NetMethods.getPassButton(new MyObserver<List<LinkBackInfo.ButtonListBean>>(context, listener), ApplicationUtil.getBzid(), ApplicationUtil.getUserId());
    }

    /**
     * 动态添加操作按钮
     *
     * @param num  操作按钮数量
     * @param data 操作按钮数据
     */
    private void addButton(int num, final List<LinkBackInfo.ButtonListBean> data) {
        approveInfoButtonLayout.removeAllViews();
        for (int i = 0; i < num; i++) {
            final LinearLayout ll = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.button, null);
            ll.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));//此处设置权重
            final Button btn = (Button) ll.findViewById(R.id.approve_info_button);

            int m = i + 1;
            btn.setText(data.get(i).getAction().getActionTitle());
            switch (i) {
                case 0:
                    btn.setBackgroundResource(R.drawable.agree);
                    break;
                case 1:
                    btn.setBackgroundResource(R.drawable.no_agree);
                    break;
                case 2:
                    btn.setBackgroundResource(R.drawable.report);
                    break;
            }
            approveInfoButtonLayout.addView(ll);
            progressLayout.setVisibility(View.GONE);
        }

        for (int j = 0; j < approveInfoButtonLayout.getChildCount(); j++) {
            final Button bt = (Button) approveInfoButtonLayout.getChildAt(j).findViewById(R.id.approve_info_button);
            final int finalJ = j;
            /**
             * 动态添加的操作按钮的点击事件
             */
            bt.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onClick1(View v) {
                    Intent intent = new Intent(context, ApproveAgree.class);
                    ApplicationUtil.setActionId(data.get(finalJ).getAction().getActionId());
                    String ActionTitle = data.get(finalJ).getAction().getActionTitle();
                    intent.putExtra("isReport", true);
                    startActivity(intent);

                }
            });
        }
        isEmpty(isHaveData);


    }


    private void initListener() {
        //文书列表list点击事件
        approveInfoDocumentList.setOnItemClickListener(new OnMultiItemClickListener() {
            @Override
            public void onItemClick1(AdapterView<?> adapterView, View view, int i, long l) {

                if (!isReport) {
                    Document data = gson.fromJson(dataString, Document.class);
                    Intent intent = new Intent(context, DocumentInfo.class);
                    intent.putExtra("docId", data.getDYSX().get(i).getDocId());
                    startActivity(intent);
                } else {
                    Document data1 = gson.fromJson(dataString, Document.class);
                    Intent intent1 = new Intent(context, DocumentInfo.class);
                    intent1.putExtra("docId", data1.getItemInfo().getDYSX().get(i).getDocId());
                    startActivity(intent1);
                }


            }

        });
        //随附材料list点击事件
        approveInfoMaterialList.setOnItemClickListener(new OnMultiItemClickListener() {
            @Override
            public void onItemClick1(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text, R.id.empty_layout, R.id.no_net_laoyout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_back_text:
                finish();
                break;
            case R.id.empty_layout:
                showData();
                break;
            case R.id.no_net_laoyout:
                showData();
                break;

        }
    }

    private void showData() {
        emptyLayout.setVisibility(View.GONE);
        noNetLaoyout.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);
        getData();
    }
}
