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
//    @BindView(R.id.progress_layout)
//    RelativeLayout progressLayout;
//    @BindView(R.id.no_net_laoyout)
//    RelativeLayout noNetLaoyout;
//    @BindView(R.id.empty_layout)
//    RelativeLayout emptyLayout;

    Unbinder unbinder;
    Gson gson;


    private  BlogHomeAdapter blogHomeAdapter;
    private List<HomeBlogModel> blogList;
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
        refreshLayout.setEnableLoadMore(false);
        gson = new Gson();
        getData();
        refreshData();
        initListener();
        try{
            ObserverOnNextListener listener = new ObserverOnNextListener<JsonArray>() {
                @Override
                public void onNext(final JsonArray result) {
                    Gson gson = new Gson();

                    List<HomeBlogModel> list=new ArrayList<HomeBlogModel>();// = gson.fromJson( result, new TypeToken<List<BlogNewsModel>>() {}.getType());

                    list = gson.fromJson( result, new TypeToken<List<HomeBlogModel>>() {}.getType());

                    com.sinostar.assistant.utils.LogUtil.d("待审批列表结果", gson.toJson(result));
                    blogHomeAdapter.getData(list, 1);
                    refreshLayout.finishRefresh();
                }

                @Override
                public void onError(Throwable e) {
                }
            };

            NetMethods.getHomeBlog(new MyObserver<JsonArray>(getContext(), listener),"http://api.cnblogs.com",1,10 );
        }
        catch (Throwable e)
        {
            throw e;
        }





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
    private void refreshData() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                blogList.clear();
                new Handler().postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        getData();
                    }
                }, 500);
            }
        });
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
                        gson=new Gson();
//                    if (result.size() != 0) {
//
//                        data = gson.toJson(result);
//                        com.sinostar.assistant.utils.LogUtil.d("待审批列表结果", gson.toJson(result));
//                        documentApproveAdapter.getData(result, 1);
//                        refreshLayout.finishRefresh();
//                        isEmpty(true);
//                    } else {
//                        isEmpty(false);
//                    }

                }

                @Override
                public void onError(Throwable e) {

                }
            };
            NetMethods.getBlogToken(new MyObserver<JsonArray>(getActivity(), listener),"http://api.cnblogs.com",1);

        }
    }
}

