package com.sinostar.assistant.ui.BlogMessage;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.bean.HomeBlogModel;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class BlogEssenceFragment extends Fragment {
    Unbinder unbinder;
    @BindView( R.id.essence_base_list)
    ListView essenceListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;

    Gson gson;
    private Integer currentIndex=1;
    private  BlogEssenceAdapter blogEssenceAdapter;
    private List<HomeBlogModel> blogList;
    private com.sinostar.assistant.utils.FragmentUtil fragmentUtil;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog_essence, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setBackground(new com.sinostar.assistant.utils.WaterMarkBgUtil(ApplicationUtil.getWaterMarkText()));
        gson = new Gson();
        blogEssenceAdapter= new BlogEssenceAdapter( getActivity());
        essenceListView.setAdapter(blogEssenceAdapter);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableRefresh(true);
        loadMoreData();
        refreshData();
        getData();
        return view;
    }

    private void refreshData() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
                refreshLayout.finishRefresh();
            }
        });
    }

    private void loadMoreData(){
        refreshLayout.setOnLoadMoreListener( new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentIndex++;
                getData();
                refreshLayout.finishLoadMore();
            }
        } );
    }



    private void getData() {
        //先判断有没有网络
        if (!com.sinostar.assistant.utils.AppNetworkMgr.isNetworkConnected(getActivity())) {
            refreshLayout.finishRefresh();
//            progressLayout.setVisibility(View.GONE);
//            noNetLaoyout.setVisibility(View.VISIBLE);
        } else {
            ObserverOnNextListener listener = new ObserverOnNextListener<JsonArray>() {
                @Override
                public void onNext(JsonArray result) {

                    List<HomeBlogModel> list = new ArrayList<HomeBlogModel>();// = gson.fromJson( result, new TypeToken<List<BlogNewsModel>>() {}.getType());

                    list = gson.fromJson( result, new TypeToken<List<HomeBlogModel>>() {
                    }.getType() );

                    com.sinostar.assistant.utils.LogUtil.d( "待审批列表结果", gson.toJson( result ) );
                    blogEssenceAdapter.notifyDataSetChanged();
                    blogEssenceAdapter.getData( list, 1 );
                    progressLayout.setVisibility(View.GONE);
                }

                @Override
                public void onError(Throwable e) {

                }
            };
            NetMethods.getPickBlog(new MyObserver<JsonArray>(getActivity(), listener),"http://api.cnblogs.com",1,10*currentIndex);
        }
    }
}
