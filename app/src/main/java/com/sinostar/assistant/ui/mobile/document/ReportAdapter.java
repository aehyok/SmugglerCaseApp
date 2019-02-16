package com.sinostar.assistant.ui.mobile.document;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.bean.Apporove;
import com.sinostar.assistant.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文书审批列表Adapter
 */
public class ReportAdapter extends BaseAdapter {
    Context context;
    List<Apporove> mList = new ArrayList<>();

    private ViewHolder holder;

    public ReportAdapter(Context context) {
        this.context = context;
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }


    public void getData(List<Apporove> list) {
        mList.clear();
        this.mList = list;
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

        if (view == null) {
            view = inflater.inflate(R.layout.document_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.approveListImage.setBackgroundResource(R.drawable.approve_report);
        holder.approveListTitle.setText("审批事项："+mList.get(i).getCQSX());
        holder.approveListName.setText(mList.get(i).getAJMC());
        holder.approveListPerson.setText("呈请人员："+mList.get(i).getCQRY());
        holder.approveListTime.setText("呈请日期："+mList.get(i).getCQRQ());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.approve_list_image)
        ImageView approveListImage;
        @BindView(R.id.approve_list_title)
        TextView approveListTitle;
        @BindView(R.id.approve_list_name)
        TextView approveListName;
        @BindView(R.id.approve_list_person)
        TextView approveListPerson;
        @BindView(R.id.approve_list_time)
        TextView approveListTime;
        @BindView(R.id.approve_list_button)
        TextView approveListButton;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
