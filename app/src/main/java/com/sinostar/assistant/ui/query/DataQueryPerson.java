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
import com.sinostar.assistant.bean.DataQueryPersonNoResource;
import com.sinostar.assistant.bean.DataQureyPerson;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.utils.DateUtil;
import com.sinostar.assistant.utils.ToastUtil;
import com.sinostar.assistant.utils.WaterMarkBgUtil;
import com.sinostar.assistant.widget.MyListView;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.sinostar.assistant.base.BaseActivity.setOnEdittFoucusChange;
import static com.sinostar.assistant.utils.DateUtil.getTimeCompareSize;
import static com.sinostar.assistant.utils.DateUtil.showDatePickerDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataQueryPerson extends Fragment {


    @BindView(R.id.data_query_person_name_et)
    EditText dataQueryPersonPersonNameEt;
    @BindView(R.id.data_query_person_date_et)
    TextView dataQueryPersonPersonDateEt;
    @BindView(R.id.checkBox_caseAssistant)
    CheckBox checkBoxCaseAssistant;
    @BindView(R.id.checkBox_count)
    CheckBox checkBoxCount;
    @BindView(R.id.checkBox_transfer)
    CheckBox checkBoxTransfer;
    Unbinder unbinder;
    @BindView(R.id.data_query_assistant_list)
    MyListView dataQueryAssistantList;
    @BindView(R.id.data_query_assistant_layout)
    LinearLayout dataQueryAssistantLayout;
    @BindView(R.id.data_query_count_list)
    MyListView dataQueryCountList;
    @BindView(R.id.data_query_count_layout)
    LinearLayout dataQueryCountLayout;
    @BindView(R.id.data_query_transfer_list)
    MyListView dataQueryTransferList;
    @BindView(R.id.data_query_transfer_layout)
    LinearLayout dataQueryTransferLayout;
    @BindView(R.id.data_query_scrollView)
    ScrollView dataQueryScrollView;
    @BindView(R.id.data_query_no_resource_list)
    ListView dataQueryNoResourceList;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.data_query_result_layout)
    RelativeLayout dataQueryResultLayout;
    @BindView(R.id.data_query_no_data_text)
    TextView dataQueryNoDataText;

    private String currentDate;
    private DataQueryAssistantAdapter dataQueryAssistantAdapter;
    private DataQueryCountAdapter dataQueryCountAdapter;
    private DataQueryTransferAdapter dataQueryTransferAdapter;
    private StringBuffer cheBoxString;
    private boolean isCheck;


    public DataQueryPerson() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.data_query, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setBackground(new WaterMarkBgUtil(ApplicationUtil.getWaterMarkText()));
        titleText.setText("查询结果");
        currentDate = DateUtil.getCurDate("yyyy/MM/dd");
        dataQueryAssistantAdapter = new DataQueryAssistantAdapter(getActivity());
        dataQueryCountAdapter = new DataQueryCountAdapter(getActivity());
        dataQueryTransferAdapter = new DataQueryTransferAdapter(getActivity());
        dataQueryPersonPersonDateEt.setText("1977/04/07");
        dataQueryPersonPersonNameEt.setText("张5");
        dataQueryNoResourceList.setAdapter(dataQueryAssistantAdapter);

        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.data_query_person_date_et, R.id.data_query_button})
    public void onViewClicked(View view) {
        cheBoxString = new StringBuffer();
        isCheck = false;
        switch (view.getId()) {
            case R.id.data_query_person_date_et:
                showDatePickerDialog(getActivity(), dataQueryPersonPersonDateEt, Calendar.getInstance());
                break;
            case R.id.data_query_button:
                dataQueryNoDataText.setVisibility(View.GONE);
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
                if (dataQueryPersonPersonNameEt.getText().toString().equals("")) {

                    ToastUtil.showToast(getActivity(), "请输入要查询的姓名");
                } else if (getTimeCompareSize(currentDate, dataQueryPersonPersonDateEt.getText().toString()) == 3) {
                    ToastUtil.showToast(getActivity(), "选择日期不能大于当前日期");

                } else if (isCheck) {
                    getData();//获取数据查询个人（有数据来源）数据
                } else {
                    getDataQueryPersonNoResource();//获取数据查询个人（无数据来源）数据
                }
                break;
        }
    }

    private void getData() {
        ObserverOnNextListener listener = new ObserverOnNextListener<DataQureyPerson>() {
            @Override
            public void onNext(DataQureyPerson result) {
                if (result != null) {
                    dataQueryNoDataText.setVisibility(View.GONE);
                    dataQueryScrollView.scrollTo(0, 0);
                    dataQueryScrollView.setVisibility(View.VISIBLE);
                    dataQueryNoResourceList.setVisibility(View.GONE);
                    if (result.getBAPT() != null) {
                        dataQueryAssistantLayout.setVisibility(View.VISIBLE);
                        dataQueryAssistantList.setAdapter(dataQueryAssistantAdapter);
                        dataQueryAssistantAdapter.getAssistantDataPerson(result.getBAPT(), 1);
                    } else {
                        dataQueryAssistantLayout.setVisibility(View.GONE);
                    }

                    if (result.getZHTJ() != null) {
                        dataQueryCountLayout.setVisibility(View.VISIBLE);
                        dataQueryCountList.setAdapter(dataQueryCountAdapter);
                        dataQueryCountAdapter.getCounttDataPerson(result.getZHTJ(), 1);
                    } else {
                        dataQueryCountLayout.setVisibility(View.GONE);
                    }

                    if (result.getXSYJ() != null) {
                        dataQueryTransferLayout.setVisibility(View.VISIBLE);
                        dataQueryTransferList.setAdapter(dataQueryTransferAdapter);
                        dataQueryTransferAdapter.getTransferDataPerson(result.getXSYJ(), 1);
                    } else {
                        dataQueryTransferLayout.setVisibility(View.GONE);
                    }
                }else{
                    dataQueryNoDataText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

        };


        String name = dataQueryPersonPersonNameEt.getText().toString();
        String dataTime = dataQueryPersonPersonDateEt.getText().toString();
        String queryType = cheBoxString.substring(1, cheBoxString.length());
        //个人-有数据来源
        NetMethods.getDataQuery(new MyObserver<DataQureyPerson>(getActivity(), listener), name, dataTime, queryType);

    }

    private void getDataQueryPersonNoResource() {

        ObserverOnNextListener listener1 = new ObserverOnNextListener<List<DataQueryPersonNoResource>>() {
            @Override
            public void onNext(List<DataQueryPersonNoResource> result) {
                if (result != null && result.size() != 0) {
                    dataQueryNoDataText.setVisibility(View.GONE);
                    dataQueryResultLayout.setVisibility(View.VISIBLE);
                    dataQueryScrollView.setVisibility(View.GONE);
                    title.setVisibility(View.VISIBLE);
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
        String name = dataQueryPersonPersonNameEt.getText().toString();
        String dataTime = dataQueryPersonPersonDateEt.getText().toString();
        //个人-无数据来源
        NetMethods.getDataQueryPersonNoSource(new MyObserver<List<DataQueryPersonNoResource>>(getActivity(), listener1), name, dataTime);
    }


}
