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

public class DataQueryTransferAdapter extends BaseAdapter {
    Context context;
    ViewHolder holder;

    List<DataQureyPerson.XSYJBean> mListP =new ArrayList<>();
    List<DataQueryCompanyBean.XSYJBean> mListC =new ArrayList<>();

    int type;


    DataQueryTransferAdapter(Context context) {
        this.context = context;
    }

    public void getTransferDataPerson(List<DataQureyPerson.XSYJBean> list , int type) {
        this.type=type;
        mListP.clear();
        mListP.addAll(list);
        notifyDataSetChanged();

    }
    public void getTransferDataPersonCompany(List<DataQueryCompanyBean.XSYJBean> list , int type) {
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
                holder.documentApproveListTitle.setText(  mListP.get(i).getXSBH());
                holder.documentApproveListContent.setText( mListP.get(i).getXSMC());
                holder.documentApproveListPerson.setText( mListP.get(i).getAJFL());
                holder.documentApproveListTime.setText( mListP.get(i).getDJSJ());
                holder.documentCompany.setText( mListP.get(i).getBADW());
                break;
            case 2:
                holder.documentApproveListTitle.setText( mListC.get(i).getXSBH());
                holder.documentApproveListContent.setText( mListC.get(i).getXSMC());
                holder.documentApproveListPerson.setText( mListC.get(i).getAJFL());
                holder.documentApproveListTime.setText( mListC.get(i).getDJSJ());
                holder.documentCompany.setText(mListC.get(i).getBADW());
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
