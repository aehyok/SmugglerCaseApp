package com.sinostar.assistant.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.sinostar.assistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter extends BaseAdapter {
    private Context context;

    Adapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
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
            view = LayoutInflater.from(context).inflate(R.layout.chat_record_date_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.chat_record_date_list_text)
        TextView chatRecordDateListText;
        @BindView(R.id.chat_record_date_list_gridview)
        GridView chatRecordDateListGridview;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
