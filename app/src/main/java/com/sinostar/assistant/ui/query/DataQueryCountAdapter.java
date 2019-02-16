package com.sinostar.assistant.ui.query;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.bean.DataQueryCompanyBean;
import com.sinostar.assistant.bean.DataQureyPerson;
import com.sinostar.assistant.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataQueryCountAdapter extends BaseAdapter {
    Context context;
    ViewHolder holder;

    List<DataQureyPerson.ZHTJBean> mListP =new ArrayList<>();
    List<DataQueryCompanyBean.ZHTJBean> mListC =new ArrayList<>();
    int type;


    DataQueryCountAdapter(Context context) {
        this.context = context;
    }

    public void getCounttDataPerson(List<DataQureyPerson.ZHTJBean> list,int type ){
        this.type=type;
        mListP.clear();
        mListP.addAll(list);
        notifyDataSetChanged();

    }
    public void getCounttDataCompany(List<DataQueryCompanyBean.ZHTJBean> list,int type ){
        this.type=type;
        mListC.clear();
        mListC.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        int num=0;
        switch (type){
            case 1:
                num=mListP.size();
                break;
            case 2:
                num=mListC.size();
                break;
        }
        return num;
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
            view = inflater.inflate(R.layout.document_approve_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.documentCompany.setVisibility(View.VISIBLE);
        holder.documentApproveListImage.setBackgroundResource(R.drawable.approve_warning);
        switch (type){
            case 1:
                holder.documentApproveListTitle.setText("案件编号:" + mListP.get(i).getAJBH());
                holder.documentApproveListContent.setText("案件名称:" + mListP.get(i).getAJMC());
                holder.documentApproveListPerson.setText("案件分类:" + mListP.get(i).getAJFL());
                holder.documentApproveListTime.setText("立案日期:" + mListP.get(i).getLARQ());
                holder.documentCompany.setText("办案单位:" + mListP.get(i).getBADW());
                break;
            case 2:
                holder.documentApproveListTitle.setText("案件编号:" + mListC.get(i).getAJBH());
                holder.documentApproveListContent.setText("案件名称:" + mListC.get(i).getAJMC());
                holder.documentApproveListPerson.setText("案件分类:" + mListC.get(i).getAJFL());
                holder.documentApproveListTime.setText("立案日期:" + mListC.get(i).getLARQ());
                holder.documentCompany.setText("办案单位:" + mListC.get(i).getBADW());
                break;
        }

        return view;
    }



    static class ViewHolder {
        @BindView(R.id.document_approve_list_image)
        ImageView documentApproveListImage;
        @BindView(R.id.document_approve_list_title)
        TextView documentApproveListTitle;
        @BindView(R.id.document_approve_list_content)
        TextView documentApproveListContent;
        @BindView(R.id.document_approve_list_person)
        TextView documentApproveListPerson;
        @BindView(R.id.document_approve_list_time)
        TextView documentApproveListTime;
        @BindView(R.id.document_company)
        TextView documentCompany;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
