package com.sinostar.assistant.ui.mobile.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.bean.Document;
import com.sinostar.assistant.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 审批文书列表Adapter
 */
public class ApproveInfoDocumentAdapter extends BaseAdapter {
    Context context;
    List<Document.DYSXBean> mlist=new ArrayList<>();



    ApproveInfoDocumentAdapter(Context context){
        this.context=context;
    }

    public void getData(List<Document.DYSXBean> list){
        mlist.clear();
        this.mlist=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return mlist.size();
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
            view = inflater.inflate(R.layout.approve_info_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
                holder.imageView.setBackgroundResource(R.drawable.pdf);
                holder.imageView2.setBackgroundResource(R.drawable.right);
                holder.approveInfoListText.setText(mlist.get(i).getDocTitle());


        return view;
    }

    static class ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.approve_info_list_text)
        TextView approveInfoListText;
        @BindView(R.id.imageView2)
        ImageView imageView2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
