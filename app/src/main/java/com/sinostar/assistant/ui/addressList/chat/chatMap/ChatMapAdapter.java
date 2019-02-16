package com.sinostar.assistant.ui.addressList.chat.chatMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.sinostar.assistant.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatMapAdapter extends BaseAdapter {
    Context context;
     ArrayList<PoiItem> mList = new ArrayList<>();
    private int positon;

    ChatMapAdapter(Context context) {
        this.context = context;
    }
    public void addData(ArrayList<PoiItem> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void getData(ArrayList<PoiItem> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
    public void refreshUI(int position){
        this.positon=position;
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
            view = inflater.inflate(R.layout.chat_map_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if(positon==i){
            holder.chatMapListImage.setVisibility(View.VISIBLE);
        }else{
            holder.chatMapListImage.setVisibility(View.INVISIBLE);
        }
        holder.chatMapListText1.setText(mList.get(i).getTitle());
        holder.chatMapListText2.setText(mList.get(i).getProvinceName() + mList.get(i).getCityName() + mList.get(i).getAdName() + mList.get(i).getSnippet());
        return view;
    }



     class ViewHolder {
        @BindView(R.id.chat_map_list_text1)
        TextView chatMapListText1;
        @BindView(R.id.chat_map_list_text2)
        TextView chatMapListText2;
        @BindView(R.id.chat_map_list_image)
        ImageView chatMapListImage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
