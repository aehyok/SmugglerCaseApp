package com.sinostar.assistant.ui.mobile.document;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.ui.mobile.info.ApproveInfo;
import com.sinostar.assistant.ui.mobile.link.LinkBackAdapter;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.bean.Apporove;
import com.sinostar.assistant.R;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.utils.AppNetworkMgr;
import com.sinostar.assistant.utils.LogUtil;
import com.sinostar.assistant.utils.OnMultiItemClickListener;
import com.sinostar.assistant.utils.WaterMarkBgUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;



/**
 * 文书审批-待审批
 */
public class WaitApproveFragment extends Fragment {

    @BindView(R.id.base_list)
    ListView approveList;
    Unbinder unbinder;
    DocumentApproveAdapter documentApproveAdapter;
    Gson gson;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.no_net_laoyout)
    RelativeLayout noNetLaoyout;
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;
    private String data;
    private LinkBackAdapter linkBackAdapter;

    public WaitApproveFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.base_list_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setBackground(new WaterMarkBgUtil(ApplicationUtil.getWaterMarkText()));
        documentApproveAdapter = new DocumentApproveAdapter(getActivity());
         approveList.setAdapter(documentApproveAdapter);
        refreshLayout.setEnableLoadMore(false);
        gson = new Gson();
        getData();
        refreshData();
        initListener();
        return view;
    }

    private void refreshData() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                documentApproveAdapter.clear();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                    }
                }, 500);
            }
        });

    }


    private void initListener() {
        approveList.setOnItemClickListener(new OnMultiItemClickListener() {
            @Override
            public void onItemClick1(AdapterView<?> adapterView, View view, int i, long l) {
                long itemId = 0;
                List<Apporove> dataList = gson.fromJson(data, new TypeToken<List<Apporove>>() {
                }.getType());
                itemId = dataList.get(i).getID();
                Intent intent = new Intent(getActivity(), ApproveInfo.class);
                intent.putExtra("itemId", itemId + "");
                startActivity(intent);
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        if (ApplicationUtil.isIsDocSubmit()) {
            documentApproveAdapter.clear();
            getData();
            ApplicationUtil.setIsDocSubmit(false);
        }
    }

    private void getData() {
        //先判断有没有网络
        if (!AppNetworkMgr.isNetworkConnected(getActivity())) {
            refreshLayout.finishRefresh();
            progressLayout.setVisibility(View.GONE);
            noNetLaoyout.setVisibility(View.VISIBLE);
        } else {
            ObserverOnNextListener listener = new ObserverOnNextListener<List<Apporove>>() {
                @Override
                public void onNext(List<Apporove> result) {

                    if (result.size() != 0) {

                        data = gson.toJson(result);
                        LogUtil.d("待审批列表结果", gson.toJson(result));
                        documentApproveAdapter.getData(result, 1);
                        refreshLayout.finishRefresh();
                        isEmpty(true);
                    } else {
                        isEmpty(false);
                    }

                }

                @Override
                public void onError(Throwable e) {
                    isEmpty(false);
                }
            };
            NetMethods.getApprovalPending(new MyObserver<List<Apporove>>(getActivity(), listener),ApplicationUtil.getUserId());

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
    private void showData(){
        emptyLayout.setVisibility(View.GONE);
        noNetLaoyout.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        },500);
    }
    @OnClick({R.id.empty_layout, R.id.no_net_laoyout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.empty_layout:
                showData();
                break;
            case R.id.no_net_laoyout:
                showData();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
