package com.sinostar.assistant.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sinostar.assistant.R;
import com.sinostar.assistant.bean.BlogNewsModel;
import com.sinostar.assistant.bean.HomeBlogModel;
import com.sinostar.assistant.bean.Login;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.ui.BlogMessage.BlogMessageActivity;
import com.sinostar.assistant.ui.BlogMessage.BlogNewsActivity;
import com.sinostar.assistant.ui.BlogMessage.BlogRemindActivity;
import com.sinostar.assistant.ui.ImagePicker.ImagePickerMainActivity;
import com.sinostar.assistant.ui.LoginActivity;
import com.sinostar.assistant.ui.addressList.ChatLogin;
import com.sinostar.assistant.ui.home.HomeGridViewAdapter;
import com.sinostar.assistant.ui.mobile.MobileApproveActivity;
import com.sinostar.assistant.ui.research.ObtainEvidence;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * @Author aehyok
 */
public class NewHomeFragment extends android.support.v4.app.Fragment {
    @BindView(R.id.home_banner)
    Banner homeBanner;
    Unbinder unbinder;
    String[] homeText = {"Blog ", "Assistant", " Remind", " Manager", "Gallery", "Approval", "Picker", "Contacts"};
    int[] homeImage = {R.drawable.tztg, R.drawable.bazs, R.drawable.yjts,
            R.drawable.zfda, R.drawable.xcqz, R.drawable.ydsp, R.drawable.sjcx, R.drawable.txl};
    @BindView(R.id.home_gridview)
    GridView homeGridview;
    NewHomeGridViewAdapter homeGridViewAdapter;
    NewHomeBlogAdapter newHomeBlogAdapter;
    @BindView(R.id.newHome_blog_list)
    ListView newHomeBlogList;
    List<BlogNewsModel> newBloglist=new ArrayList<>();
    public NewHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate( R.layout.fragment_new_home, container, false );
        unbinder = ButterKnife.bind(this, view);
        List list = new ArrayList();
        list.add(R.drawable.home_lbt);
        list.add(R.drawable.home_hot_1);
        list.add(R.drawable.home_hot_2);
        initBanner(list);
        initListener();
        homeGridViewAdapter = new NewHomeGridViewAdapter(getContext(), homeText, homeImage);
        homeGridview.setAdapter(homeGridViewAdapter);
//        getMovieData();

        int length=10;
        for(int i=0;i<length;i++)
        {
            BlogNewsModel item=new BlogNewsModel();
            item.setDateAdded(  "2019年3月15日");
            item.setTitle( "小目标"+i );
            item.setTopicIcon( "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2548870813.webp" );
            newBloglist.add( item );
        }
        newHomeBlogAdapter=new NewHomeBlogAdapter(getContext(),newBloglist);
        newHomeBlogList.setAdapter( newHomeBlogAdapter );
        return view;
    }
    private void initBanner(List images) {
        //设置banner样式
        //只显示圆形指示器
        homeBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        homeBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        homeBanner.setImages(images);
        //设置banner动画效果
        homeBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        //设置标题集合（当banner样式有显示title时）
        //homeBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        homeBanner.isAutoPlay(true);
        //设置轮播时间
        homeBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        homeBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        homeBanner.start();
    }
    private void getMovieData() {
        try
        {
            ObserverOnNextListener listener = new ObserverOnNextListener<JsonObject>() {
                @Override
                public void onNext(JsonObject result) {
                    Gson gson = new Gson();

                    newBloglist = gson.fromJson( result.getAsJsonArray( "subjects" ), new TypeToken<List<BlogNewsModel>>() {
                    }.getType() );

                    com.sinostar.assistant.utils.LogUtil.d( "待审批列表结果", gson.toJson( result ) );
                    newHomeBlogAdapter.notifyDataSetChanged();
                    newHomeBlogAdapter.getData( newBloglist, 1 );
                }

                @Override
                public void onError(Throwable e) {

                }
            };
            NetMethods.getMovie(new MyObserver<JsonObject>(getActivity(), listener));
        }
        catch(Throwable e)
        {
            throw e;
        }
    }

    private void initListener() {
        homeBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                enterStoreInfo();
            }
        });

        homeGridview.setOnItemClickListener(new com.sinostar.assistant.utils.OnMultiItemClickListener() {
            @Override
            public void onItemClick1(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    //通知公告
                    case 0:
                        startActivity(new Intent(getActivity(), NewTestActivity.class));
                        break;
                    //办案助手
                    case 1:
                        break;
                    //预警提示
                    case 2:
                        break;
                    //执法档案
                    case 3:
                        break;
                    //现场取证
                    case 4:
                        break;
                    //移动审批
                    case 5:
                        break;
                    //数据查询
                    case 6:
                        break;
                    //通讯录
                    case 7:
                        break;
                        default:
                }
            }
        });
    }

    private void enterStoreInfo(){
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
}
