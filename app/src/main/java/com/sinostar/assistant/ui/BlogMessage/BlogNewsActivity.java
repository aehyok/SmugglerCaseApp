package com.sinostar.assistant.ui.BlogMessage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sinostar.assistant.R;
import com.sinostar.assistant.bean.ArticleModel;
import com.sinostar.assistant.bean.BlogNewsModel;
import com.sinostar.assistant.bean.ObtainEvidenceBean;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.ui.mobile.info.ApproveInfo;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlogNewsActivity extends AppCompatActivity {
    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.srl_test)
    SmartRefreshLayout srlTest;
    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    List<BlogNewsModel> NewsModel=new ArrayList<BlogNewsModel>();
    private  Integer blogNewsPageIndex=1;

    private BlogNewsAdapter mTestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_blog_news );
        ButterKnife.bind(this);

        //TextView文本框赋值
        titleBarText.setText("Blog News");
        refreshView();
        smartRefreshView();
        getNewsData(blogNewsPageIndex);//获取第一页首页数据
    }

    private void refreshView() {
        //1,加载空布局文件，便于第五步适配器在没有数据的时候加载
        //View emptyView = View.inflate(this, R.layout.empty_view, null);
        //2，设置LayoutManager,LinearLayoutManager表示竖直向下
        rvTest.setLayoutManager(new LinearLayoutManager(this));
        //3，初始化一个无数据的适配器
        mTestAdapter = new BlogNewsAdapter();
        //4，绑定recyclerView和适配器
        rvTest.setAdapter(mTestAdapter);
        //5，给recyclerView设置空布局
        //mTestAdapter.setEmptyView(emptyView);
        //6，给recyclerView的每一个子列表添加点击事件
        mTestAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String itemId;
                itemId = NewsModel.get(position).getId();

                Toast.makeText(BlogNewsActivity.this,  itemId,
                        Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(BlogNewsActivity.this, BlogNewsContentActivity.class);
                intent.putExtra("Id", itemId);
                startActivity(intent);
            }
        });
    }

    public void getNewsData(int pageIndex){
        try{
            ObserverOnNextListener listener = new ObserverOnNextListener<JsonArray>() {
            @Override
            public void onNext(final JsonArray result) {
                Gson gson = new Gson();
                List<BlogNewsModel> list=new ArrayList<BlogNewsModel>();// = gson.fromJson( result, new TypeToken<List<BlogNewsModel>>() {}.getType());
                try{
                    list = gson.fromJson( result, new TypeToken<List<BlogNewsModel>>() {}.getType());
                }
                catch (Throwable e)
                {
                    throw e;
                }
                NewsModel.addAll( list );
//                Collections.sort(NewsModel, new Comparator<BlogNewsModel>() {
//
//                    @Override
//                    public int compare(BlogNewsModel o1, BlogNewsModel o2) {
//                        int flag = o1.getDateAdded().compareTo(o2.getDateAdded());
//                        return flag;
//                    }
//                });
                mTestAdapter.setNewData( NewsModel );
            }

            @Override
            public void onError(Throwable e) {
            }
        };

            NetMethods.getBlogNews(new MyObserver<JsonArray>(BlogNewsActivity.this, listener),"http://api.cnblogs.com",pageIndex );
        }
        catch (Throwable e)
        {
            throw e;
        }
    }

    //返回按键
    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_back_text:
                finish();
                break;
        }
    }

    private void smartRefreshView() {
        srlTest.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉刷新,一般添加调用接口获取数据的方法
                getNewsData(1);
                refreshLayout.finishRefresh();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载，一般添加调用接口获取更多数据的方法
                blogNewsPageIndex=blogNewsPageIndex+1;
                getNewsData(blogNewsPageIndex);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        });
    }
}
