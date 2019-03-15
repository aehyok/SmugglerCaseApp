package com.sinostar.assistant.ui.BlogMessage;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.bean.Apporove;
import com.sinostar.assistant.bean.BlogNewsModel;
import com.sinostar.assistant.bean.HomeBlogModel;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.ui.mobile.document.DocumentApproveAdapter;
import com.sinostar.assistant.ui.mobile.info.ApproveInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



public class BlogHomeFragment extends Fragment {
    @BindView( R.id.homeblog_base_list )
    ListView blogListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;

    Unbinder unbinder;
    private int currentIndex=1;
    private  BlogHomeAdapter blogHomeAdapter;
    private com.sinostar.assistant.utils.FragmentUtil fragmentUtil;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        view.setBackground(new com.sinostar.assistant.utils.WaterMarkBgUtil(ApplicationUtil.getWaterMarkText()));
        blogHomeAdapter = new BlogHomeAdapter(getActivity());
        blogListView.setAdapter(blogHomeAdapter);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableRefresh(true);
        getData();
        refreshData();
        loadMoreData();
        initListener();
        return view;
    }
    private void initListener() {
        blogListView.setOnItemClickListener(new com.sinostar.assistant.utils.OnMultiItemClickListener() {
            @Override
            public void onItemClick1(AdapterView<?> adapterView, View view, int i, long l) {
                long itemId = 0;
//                List<HomeBlogModel> dataList = gson.fromJson(data, new TypeToken<List<Apporove>>() {
//                }.getType());
//                itemId = dataList.get(i).getId();
//                Intent intent = new Intent(getActivity(), ApproveInfo.class);
//                intent.putExtra("itemId", itemId + "");
//                startActivity(intent);
            }
        });


    }
    private void loadMoreData(){
        refreshLayout.setOnLoadMoreListener( new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                currentIndex+=1;
                getData();
                refreshLayout.finishLoadMore();
            }
        } );
    }

    private void refreshData() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                getData();
                refreshLayout.finishRefresh();
            }
        });
    }
    private void getData() {
        //先判断有没有网络
        if (!com.sinostar.assistant.utils.AppNetworkMgr.isNetworkConnected(getActivity())) {

        } else {
            ObserverOnNextListener listener = new ObserverOnNextListener<JsonArray>() {
                @Override
                public void onNext(JsonArray result) {
                    Gson gson = new Gson();

                    List<HomeBlogModel> list;

                    list = gson.fromJson( result, new TypeToken<List<HomeBlogModel>>() {
                    }.getType() );

                    com.sinostar.assistant.utils.LogUtil.d( "待审批列表结果", gson.toJson( result ) );
                    blogHomeAdapter.notifyDataSetChanged();
                    blogHomeAdapter.getData( list, 1 );
                    progressLayout.setVisibility(View.GONE);
                }

                @Override
                public void onError(Throwable e) {

                }
            };
            NetMethods.getHomeBlog(new MyObserver<JsonArray>(getActivity(), listener),"https://api.cnblogs.com",1,10*currentIndex);
        }
    }
}

