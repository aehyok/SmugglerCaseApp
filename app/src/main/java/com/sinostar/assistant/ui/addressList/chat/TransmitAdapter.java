package com.sinostar.assistant.ui.addressList.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransmitAdapter extends BaseAdapter {
    private Context context;
     List<String> mList = new ArrayList<>();

    TransmitAdapter(Context context) {
        this.context = context;
    }

    public void getData(List<String> list) {
        mList.clear();
        mList.addAll(list);
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
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.chat_transmit_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.chatTrasmitText.setText(mList.get(i));
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.chat_trasmit_image)
        ImageView chatTrasmitImage;
        @BindView(R.id.chat_trasmit_text)
        TextView chatTrasmitText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
