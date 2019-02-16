package com.sinostar.assistant.ui.emoji;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/6.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private List<GridView> mViews = new ArrayList<>();
    public ViewPagerAdapter(List<GridView> views){
        this.mViews=views;

    }
    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    public Object instantiateItem(ViewGroup container, int position) {
        return container;
    }

}
