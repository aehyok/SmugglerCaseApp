package com.sinostar.assistant.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.bean.Login;
import com.sinostar.assistant.ui.LoginActivity;
import com.sinostar.assistant.ui.home.HomeGridViewAdapter;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
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
        return view;
    }
    private void initBanner(List images) {
        //设置banner样式
        homeBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);  //只显示圆形指示器
        //设置图片加载器
        homeBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        homeBanner.setImages(images);
        //设置banner动画效果
        homeBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        //设置标题集合（当banner样式有显示title时）
//        homeBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        homeBanner.isAutoPlay(true);
        //设置轮播时间
        homeBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        homeBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        homeBanner.start();
    }
    private void initListener() {
        homeBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                enterStoreInfo();
            }
        });
    }

    private void enterStoreInfo(){
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

}
