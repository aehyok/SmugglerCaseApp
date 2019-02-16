package com.sinostar.assistant.ui.query;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.base.OnEditFocusChange;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.bean.DataQueryCompanyBean;
import com.sinostar.assistant.bean.DataQueryPersonNoResource;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.utils.ToastUtil;
import com.sinostar.assistant.utils.WaterMarkBgUtil;
import com.sinostar.assistant.widget.MyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.sinostar.assistant.base.BaseActivity.setOnEdittFoucusChange;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataQueryCompany extends Fragment {


    @BindView(R.id.data_query_name_tv)
    TextView dataQueryNameTv;
    @BindView(R.id.data_query_person_name_et)
    EditText dataQueryPersonNameEt;
    @BindView(R.id.checkBox_caseAssistant)
    CheckBox checkBoxCaseAssistant;
    @BindView(R.id.checkBox_count)
    CheckBox checkBoxCount;
    @BindView(R.id.checkBox_transfer)
    CheckBox checkBoxTransfer;
    Unbinder unbinder;
    @BindView(R.id.data_query_person_date_tv)
    TextView dataQueryPersonDateTv;
    @BindView(R.id.data_query_person_date_et)
    TextView dataQueryPersonDateEt;
    @BindView(R.id.data_query_scrollView)
    ScrollView dataQueryScrollView;
    @BindView(R.id.data_query_assistant_layout)
    LinearLayout dataQueryAssistantLayout;
    @BindView(R.id.data_query_count_layout)
    LinearLayout dataQueryCountLayout;
    @BindView(R.id.data_query_transfer_layout)
    LinearLayout dataQueryTransferLayout;
    @BindView(R.id.data_query_assistant_list)
    MyListView dataQueryAssistantList;
    @BindView(R.id.data_query_count_list)
    MyListView dataQueryCountList;
    @BindView(R.id.data_query_transfer_list)
    MyListView dataQueryTransferList;
    @BindView(R.id.data_query_no_resource_list)
    ListView dataQueryNoResourceList;
    @BindView(R.id.data_query_result_layout)
    RelativeLayout dataQueryResultLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.data_query_no_data_text)
    TextView dataQueryNoDataText;

    private StringBuffer cheBoxString;
    private boolean isCheck;
    private DataQueryAssistantAdapter dataQueryAssistantAdapter;
    private DataQueryCountAdapter dataQueryCountAdapter;
    private DataQueryTransferAdapter dataQueryTransferAdapter;
    private View view;

    public DataQueryCompany() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.data_query, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setBackground(new WaterMarkBgUtil(ApplicationUtil.getWaterMarkText()));
        dataQueryAssistantAdapter = new DataQueryAssistantAdapter(getActivity());
        dataQueryCountAdapter = new DataQueryCountAdapter(getActivity());
        dataQueryTransferAdapter = new DataQueryTransferAdapter(getActivity());
        dataQueryPersonNameEt.setText("青岛琴琴加工贸易有限公司");
        dataQueryNoResourceList.setAdapter(dataQueryAssistantAdapter);
        init();

        return view;
    }




    private void getData() {
        ObserverOnNextListener listener = new ObserverOnNextListener<DataQueryCompanyBean>() {
            @Override
            public void onNext(DataQueryCompanyBean result) {
                if (result != null) {
                    dataQueryNoDataText.setVisibility(View.GONE);
                    dataQueryScrollView.scrollTo(0, 0);
                    dataQueryScrollView.setVisibility(View.VISIBLE);
                    dataQueryNoResourceList.setVisibility(View.GONE);
                    if (result.getBAPT() != null) {
                        dataQueryAssistantLayout.setVisibility(View.VISIBLE);
                        dataQueryAssistantList.setAdapter(dataQueryAssistantAdapter);
                        dataQueryAssistantAdapter.getAssistantDataCompany(result.getBAPT(), 2);
                    } else {
                        dataQueryAssistantLayout.setVisibility(View.GONE);
                    }

                    if (result.getZHTJ() != null) {
                        dataQueryCountLayout.setVisibility(View.VISIBLE);
                        dataQueryCountList.setAdapter(dataQueryCountAdapter);
                        dataQueryCountAdapter.getCounttDataCompany(result.getZHTJ(), 2);
                    } else {
                        dataQueryCountLayout.setVisibility(View.GONE);
                    }

                    if (result.getXSYJ() != null) {
                        dataQueryTransferLayout.setVisibility(View.VISIBLE);
                        dataQueryTransferList.setAdapter(dataQueryTransferAdapter);
                        dataQueryTransferAdapter.getTransferDataPersonCompany(result.getXSYJ(), 2);
                    } else {
                        dataQueryTransferLayout.setVisibility(View.GONE);
                    }
                }else {
                    dataQueryNoDataText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        };
        String name = dataQueryPersonNameEt.getText().toString();
        String queryType = cheBoxString.substring(1, cheBoxString.length());

        //公司-有数据来源
        NetMethods.getDataQueryCompany(new MyObserver<DataQueryCompanyBean>(getActivity(), listener),
                name, queryType);

    }

    private void getDataQueryCompanyNoResource() {

        ObserverOnNextListener listener1 = new ObserverOnNextListener<List<DataQueryPersonNoResource>>() {
            @Override
            public void onNext(List<DataQueryPersonNoResource> result) {
                if (result != null && result.size() != 0) {
                    dataQueryNoDataText.setVisibility(View.GONE);
                    dataQueryResultLayout.setVisibility(View.VISIBLE);
                    dataQueryNoResourceList.setVisibility(View.VISIBLE);
                    dataQueryScrollView.setVisibility(View.GONE);
                    dataQueryAssistantAdapter.getPersonNoResource(result, 4);
                } else {
                    dataQueryNoDataText.setVisibility(View.VISIBLE);
                    dataQueryResultLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                dataQueryResultLayout.setVisibility(View.GONE);
            }
        };
        String name = dataQueryPersonNameEt.getText().toString();

        //公司-无数据来源
        NetMethods.getDataQueryCompanyNoSource(new MyObserver<List<DataQueryPersonNoResource>>(getActivity(), listener1), name);
    }

    private void init() {
        dataQueryPersonDateTv.setVisibility(View.GONE);
        dataQueryPersonDateEt.setVisibility(View.GONE);
        dataQueryNameTv.setText("企业名称");
        dataQueryPersonNameEt.setText("青岛琴琴加工贸易有限公司");
        titleText.setText("查询结果");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.data_query_button)
    public void onViewClicked() {
        dataQueryNoDataText.setVisibility(View.GONE);
        cheBoxString = new StringBuffer();
        isCheck = false;
        if (checkBoxCaseAssistant.isChecked()) {
            cheBoxString.append(",BAPT");
            isCheck = true;
        }
        if (checkBoxCount.isChecked()) {
            cheBoxString.append(",ZHTJ");
            isCheck = true;
        }
        if (checkBoxTransfer.isChecked()) {
            cheBoxString.append(",XSYJ");
            isCheck = true;
        }
        if (dataQueryPersonNameEt.getText().toString().equals("")) {
            ToastUtil.showToast(getActivity(), "请输入要查询的企业名称");
        } else if (isCheck) {
            getData();
        } else {
            getDataQueryCompanyNoResource();
//            ToastUtil.makeText(getActivity(), "请选择数据平台", ToastUtil.LENGTH_SHORT).show();
        }


    }
}
