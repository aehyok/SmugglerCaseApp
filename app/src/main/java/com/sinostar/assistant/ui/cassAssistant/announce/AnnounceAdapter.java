package com.sinostar.assistant.ui.cassAssistant.announce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.bean.GuideLine;
import com.sinostar.assistant.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnnounceAdapter extends BaseAdapter {
    Context context;
    List<GuideLine.DataBean> mList=new ArrayList<>();


    AnnounceAdapter(Context context){
        this.context=context;
    }

    public void getData( List<GuideLine.DataBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
    public void lodeData(List<GuideLine.DataBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear(){
        mList.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.announce_pendding_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.announceListTitleText.setText(mList.get(i).getAJMC());
        holder.announceListTimeText.setText(mList.get(i).getSTATENAME());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.announce_list_title_text)
        TextView announceListTitleText;
        @BindView(R.id.announce_list_time_text)
        TextView announceListTimeText;
        @BindView(R.id.imageView4)
        ImageView imageView4;
        @BindView(R.id.imageView5)
        ImageView imageView5;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
