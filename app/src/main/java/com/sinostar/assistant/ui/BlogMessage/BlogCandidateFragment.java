package com.sinostar.assistant.ui.BlogMessage;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.sinostar.assistant.R;
import com.sinostar.assistant.bean.BlogNewsModel;
import com.sinostar.assistant.bean.HomeBlogModel;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.ui.research.image.ImageSelectAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class BlogCandidateFragment extends Fragment {
    Unbinder unbinder;
    @BindView( R.id.candidate_base_list )
    ListView candidateListView;
    @BindView( R.id.refreshLayout )
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;

    Gson gson;
    private Integer currentIndex=1;

    BlogRemindNewsAdapter newsAdapter;
    private List<BlogNewsAdapter> newsList;
    private com.sinostar.assistant.utils.FragmentUtil fragmentUtil;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog_candidate, container, false);
        unbinder = ButterKnife.bind(this, view);

        newsAdapter=new BlogRemindNewsAdapter(getActivity());
        candidateListView.setAdapter( newsAdapter );
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableRefresh( true );
        gson=new Gson();
        getData();
        refreshData();
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

    public void loadMoreData(){
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
                public void onNext(final JsonArray result) {
                    try{
                        com.sinostar.assistant.utils.LogUtil.d( "result", gson.toJson( result ) );
                    }
                    catch (Throwable e)
                    {
                        throw e;
                    }

                    List<BlogNewsModel> list = new ArrayList<BlogNewsModel>();// = gson.fromJson( result, new TypeToken<List<BlogNewsModel>>() {}.getType());

                    list = gson.fromJson( result, new TypeToken<List<BlogNewsModel>>() {}.getType() );

                    com.sinostar.assistant.utils.LogUtil.d( "待审批列表结果", gson.toJson( result ) );
                    newsAdapter.getData( list, 1 );
                    progressLayout.setVisibility(View.GONE);
                }

                @Override
                public void onError(Throwable e) {

                }
            };
            NetMethods.getBlogNews(new MyObserver<JsonArray>(getActivity(), listener),"https://api.cnblogs.com",1,currentIndex*10);
        }
    }
}

