package com.sinostar.assistant.ui.mobile.link;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.bean.Apporove;
import com.sinostar.assistant.R;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.utils.AppNetworkMgr;
import com.sinostar.assistant.utils.LogUtil;
import com.sinostar.assistant.utils.WaterMarkBgUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;



/**
 * 文书审批——已完成
 */

public class LinkBackFinish extends Fragment {

    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;
    @BindView(R.id.base_list)
    ListView approveList;
    Unbinder unbinder;
    LinkBackAdapter linkBackAdapter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.no_net_laoyout)
    RelativeLayout noNetLaoyout;
    private Gson gson;
    public LinkBackFinish() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.base_list_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setBackground(new WaterMarkBgUtil(ApplicationUtil.getWaterMarkText()));
        linkBackAdapter = new LinkBackAdapter(getActivity());
        approveList.setAdapter(linkBackAdapter);
        refreshLayout.setEnableLoadMore(false);
        gson = new Gson();
        getData();
        refreshData();
        approveList.setClickable(true);
        approveList.setSelector(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    private void refreshData() {

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                linkBackAdapter.clear();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();

                    }
                }, 500);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
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
                    progressLayout.setVisibility(View.GONE);
                    if (result.size() != 0) {

                        LogUtil.d("待审批列表结果", gson.toJson(result));
                        linkBackAdapter.getData(result, 2);
                        refreshLayout.finishRefresh();
                    } else {
                        emptyLayout.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onError(Throwable e) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressLayout.setVisibility(View.GONE);
                            emptyLayout.setVisibility(View.VISIBLE);
                        }
                    },500);
                }
            };
                    NetMethods.getLinkBackPended(new MyObserver<List<Apporove>>(getActivity(), listener), ApplicationUtil.getUserId());


        }
    }


    private void showData() {
        emptyLayout.setVisibility(View.GONE);
        noNetLaoyout.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 500);
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

    @OnItemClick(R.id.base_list)
    public void onViewClicked(int i) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
