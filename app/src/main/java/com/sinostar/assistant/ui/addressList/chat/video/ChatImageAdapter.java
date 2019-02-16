package com.sinostar.assistant.ui.addressList.chat.video;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ChatImageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mList;
    public ChatImageAdapter(FragmentManager fm,List<Fragment>list) {
        super(fm);
        this.mList=list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }


}
