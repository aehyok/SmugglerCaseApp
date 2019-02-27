package com.sinostar.assistant.ui.BlogMessage;

import android.os.Handler;
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
import com.google.gson.JsonObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sinostar.assistant.R;
import com.sinostar.assistant.bean.ArticleModel;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.utils.Constent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlogMessageActivity extends AppCompatActivity {
    @BindView(R.id.title_bar_text)
    TextView titleBarText;

    private  ArticleModel model=new ArticleModel();
    String cls;
    private BlogArticleAdapter mTestAdapter;
    private ArrayList<ArticleModel.Article> mTestList=new ArrayList<ArticleModel.Article>(  );

    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    @BindView(R.id.srl_test)
    SmartRefreshLayout srlTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //设置View视图
        setContentView( R.layout.activity_blog_message );

        //ButterKnife.bind(this);必须在setContentView();之后，且父类bind绑定后，子类不需要再bind
        ButterKnife.bind(this);

        //TextView文本框赋值
        titleBarText.setText("Blog Message");

        //getData( 1 );
        refreshView();
        smartRefreshView();
        getBlogData( 1 );
    }

    public void getBlogData(Integer index) {
        ObserverOnNextListener listener = new ObserverOnNextListener<JsonObject>() {
            @Override
            public void onNext(final JsonObject result) {
                Gson gson = new Gson();
                model=gson.fromJson(result.toString(), ArticleModel.class);
                mTestAdapter.setNewData( model.getArticleList() );
            }

            @Override
            public void onError(Throwable e) {
            }
        };

        NetMethods.getToken(new MyObserver<JsonObject>(BlogMessageActivity.this, listener),index,"http://aehyok.com:1281");
    }

    private void refreshView() {
        //1,加载空布局文件，便于第五步适配器在没有数据的时候加载
        //View emptyView = View.inflate(this, R.layout.empty_view, null);
        //2，设置LayoutManager,LinearLayoutManager表示竖直向下
        rvTest.setLayoutManager(new LinearLayoutManager(this));
        //3，初始化一个无数据的适配器
        mTestAdapter = new BlogArticleAdapter();
        //4，绑定recyclerView和适配器
        rvTest.setAdapter(mTestAdapter);
        //5，给recyclerView设置空布局
        //mTestAdapter.setEmptyView(emptyView);
        //6，给recyclerView的每一个子列表添加点击事件
        mTestAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(BlogMessageActivity.this, "我点击了第" + position + "个子view",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取数据的方法
     * 该方法纯属展示各种效果，实际应用时候请自己根据需求做判断即可
     *
     * @param mode 模式：1为刚开始进来加载数据 空数据 2为下拉刷新 3为上拉加载
     */
    private void getData(int mode) {
        //添加临时数据，一般直接从接口获取
        switch (mode) {
            case 1:
                break;
            case 2:
                getBlogData(2);
                mTestList = new ArrayList<>();
                mTestList=model.getArticleList();
                mTestAdapter.setNewData(mTestList);
                break;
            case 3:
                getBlogData(3);

                mTestList=model.getArticleList();
                mTestAdapter.setNewData(mTestList);
                break;
            default:
                mTestList = new ArrayList<>();
                mTestList=model.getArticleList();
                break;
        }
    }

    private void smartRefreshView() {
        srlTest.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉刷新,一般添加调用接口获取数据的方法
                getData(2);
                refreshLayout.finishRefresh();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载，一般添加调用接口获取更多数据的方法
                getData(3);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        });
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
}
