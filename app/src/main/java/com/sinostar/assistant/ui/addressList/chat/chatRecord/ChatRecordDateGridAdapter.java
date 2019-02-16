package com.sinostar.assistant.ui.addressList.chat.chatRecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinostar.assistant.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatRecordDateGridAdapter extends BaseAdapter {
    private Context context;
    List<String> mList=new ArrayList<>();

    ChatRecordDateGridAdapter(Context context, List<String> list) {
        this.context = context;
        mList.clear();
        mList.addAll(list);
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
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.chat_record_date_text, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.chatRecordDateText.setText(mList.get(i));

        return view;
    }


    static class ViewHolder {
        @BindView(R.id.chat_record_date_text)
        TextView chatRecordDateText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
