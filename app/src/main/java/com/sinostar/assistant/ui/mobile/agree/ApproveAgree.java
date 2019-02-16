package com.sinostar.assistant.ui.mobile.agree;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.base.BaseActivity;
import com.sinostar.assistant.base.OnEditFocusChange;
import com.sinostar.assistant.bean.ApproveAgreeModel;
import com.sinostar.assistant.bean.ApproveAgreeSend;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.ui.mobile.MobileApproveActivity;
import com.sinostar.assistant.utils.LogUtil;
import com.sinostar.assistant.utils.ToastUtil;
import com.sinostar.assistant.utils.WaterMarkBgUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sinostar.assistant.utils.ToastUtil.toast;

/**
 * 审批通过模版
 */
public class ApproveAgree extends BaseActivity {

    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;
    @BindView(R.id.approve_agree_model_list)
    ListView baseList;
    Context context;
    ApproveAgreeAdapter approveAgreeModelAdapter;
    @BindView(R.id.approve_agree_model_layout)
    RelativeLayout approveAgreeModelLayout;


    String userId = ApplicationUtil.getUserId();
    String bzId = ApplicationUtil.getBzid();
    String actionId = ApplicationUtil.getActionId();
    @BindView(R.id.title_bar_back_image)
    ImageButton titleBarBackImage;
    private Gson gson;
    private boolean isReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_agree_model);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        isReport = intent.getBooleanExtra("isReport", false);
        gson = new GsonBuilder().disableHtmlEscaping().create();
        approveAgreeModelLayout.setBackground(new WaterMarkBgUtil(ApplicationUtil.getWaterMarkText()));
        baseList.setDividerHeight(0);
        context = ApproveAgree.this;
        approveAgreeModelAdapter = new ApproveAgreeAdapter(context);

        getData();
        initListener();
    }


    private void initListener() {
        //监听spinner点击事件回调
        approveAgreeModelAdapter.setOnSpinnerChangeListener(new ApproveAgreeAdapter.onSpinnerChangeListener() {
            @Override
            public void onRadioResponce(String name, int position) {
                //局部更新radioGroup
                approveAgreeModelAdapter.refreshRadio(baseList, position);
            }
        });

        setOnEdittFoucusChange(new OnEditFocusChange() {
            @Override
            public void isEditextFoucus() {
                titleBarBackImage.requestFocusFromTouch();
            }
        });
    }

    /**
     * 联调审批通过模版数据
     */
    private void getData() {
        ObserverOnNextListener listener = new ObserverOnNextListener<Object>() {
            @Override
            public void onNext(Object result) {
                String resultJson = gson.toJson(result);

                //单独获取Inpudata数据（因为有可能会存在随机新加字段）
                String inputDataString = resultJson.substring(resultJson.indexOf("InputData\":{\""), resultJson.indexOf("},\"IsNewData\""))
                        .substring(12);

                //将全部数据根据实体类进行解释
                ApproveAgreeModel data = gson.fromJson(resultJson, ApproveAgreeModel.class);

                //根据返回类型判断是否显示，把显示的数据加入到apater里面去渲染
                List<ApproveAgreeModel.MDDefineBean.GroupsBean.ColumnsBean> columsData =
                        data.getMD_Define().getGroups().get(0).getColumns();
                for (int i = 0; i < columsData.size(); i++) {
                    if (!columsData.get(i).isCanDisplay()) {
                        columsData.remove(i);
                        i--;
                    }
                }
                //设置标题
                titleBarText.setText(data.getMD_Define().getDisplayName());
                approveAgreeModelAdapter.getData(columsData, data, inputDataString, isReport);
                baseList.setAdapter(approveAgreeModelAdapter);
                progressLayout.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {

            }
        };
        NetMethods.getApproveAgreeModel(new MyObserver<>(context, listener), userId, bzId, actionId);
        LogUtil.d("通过页面的输入：userId=" + userId + ",bzid=" + bzId + ",actionId=" + actionId);
    }


    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text, R.id.approve_pass_model_confirm_button, R.id.approve_pass_model_cancel_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_back_text:
                finish();
                break;
            case R.id.approve_pass_model_confirm_button:  //通过按钮
//                String data = approveAgreeModelAdapter.getAgreeData();
                getAgree();
                break;
            case R.id.approve_pass_model_cancel_button:  //取消按钮
                finish();
                break;
        }
    }

    /**
     * 审核监听
     */
    private void getAgree() {
        String data = approveAgreeModelAdapter.getAgreeData();
        ObserverOnNextListener listener = new ObserverOnNextListener<ApproveAgreeSend>() {
            @Override
            public void onNext(ApproveAgreeSend result) {
                if (result.get_errorMessage() != null && !result.get_errorMessage().equals("")) {
                    ToastUtil.showToast(context, result.getErrorMessage());
                    ApplicationUtil.setIsDocSubmit(false);
                    ApplicationUtil.setIsLinkSubmit(false);
                    ApplicationUtil.setIsReportSubmit(false);
                } else {
                    toast("审核通过");
                    switch (ApplicationUtil.getApproveType()) {
                        case 1:
                            if (isReport) {
                                ApplicationUtil.setIsReportSubmit(true);
                            }
                            ApplicationUtil.setIsDocSubmit(true);
                            break;
                        case 2:
                            ApplicationUtil.setIsLinkSubmit(true);
                            break;
                    }
                    Intent intent = new Intent(context, MobileApproveActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onError(Throwable e) {
            }
        };
        NetMethods.getApproveAgreeSend(new MyObserver<ApproveAgreeSend>(context, listener), data);
    }


}
