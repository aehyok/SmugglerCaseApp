package com.sinostar.assistant.ui.query;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.bean.DataQueryCompanyBean;
import com.sinostar.assistant.bean.DataQueryPersonNoResource;
import com.sinostar.assistant.bean.DataQureyPerson;
import com.sinostar.assistant.bean.ObtainEvidenceBean;
import com.sinostar.assistant.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataQueryAssistantAdapter extends BaseAdapter {
    Context context;
    ViewHolder holder;
    private List<DataQureyPerson.BAPTBean> mListP = new ArrayList<>();
    private List<DataQueryCompanyBean.BAPTBean> mListC = new ArrayList<>();
    private List<ObtainEvidenceBean> mListO = new ArrayList<>();
    private List<DataQueryPersonNoResource> mListN = new ArrayList<>();

    int type;


    public DataQueryAssistantAdapter(Context context) {
        this.context = context;
    }

    public void getAssistantDataPerson(List<DataQureyPerson.BAPTBean> list, int type) {
        this.type = type;
        mListP.clear();
        mListP.addAll(list);
        notifyDataSetChanged();

    }

    public void getAssistantDataCompany(List<DataQueryCompanyBean.BAPTBean> list, int type) {
        this.type = type;
        mListC.clear();
        mListC.addAll(list);
        notifyDataSetChanged();
    }

    public void getObtainEvidence(List<ObtainEvidenceBean> list, int type) {
        this.type = type;
        mListO.clear();
        mListO.addAll(list);
        notifyDataSetChanged();
    }

    public void getPersonNoResource(List<DataQueryPersonNoResource> list, int type) {
        this.type = type;
        mListN.clear();
        mListN.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        int num = 0;
        switch (type) {
            case 1:
                num = mListP.size();
                break;
            case 2:
                num = mListC.size();
                break;
            case 3:
                num = mListO.size();
                break;
            case 4:
                num = mListN.size();
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
//        holder.documentCompany.setVisibility(View.VISIBLE);
//        holder.documentApproveListImage.setBackgroundResource(R.drawable.approve_warning);
        switch (type) {
            case 1://数据查询（个人）
                holder.documentApproveListTitle.setText(mListP.get(i).getAJBH());
                holder.documentApproveListContent.setText(mListP.get(i).getAJMC());
                holder.documentApproveListPerson.setText(mListP.get(i).getAJFL());
                holder.documentApproveListTime.setText(mListP.get(i).getLARQ());
                holder.documentCompany.setText(mListP.get(i).getBADW());
                break;
            case 2://数据查询（公司）
                holder.documentApproveListTitle.setText(mListC.get(i).getAJBH());
                holder.documentApproveListContent.setText(mListC.get(i).getAJMC());
                holder.documentApproveListPerson.setText(mListC.get(i).getAJFL());
                holder.documentApproveListTime.setText(mListC.get(i).getLARQ());
                holder.documentCompany.setText(mListC.get(i).getBADW());
                break;
            case 3://现场取证
                holder.documentApproveListTitle.setText(mListO.get(i).getAJBH());
                holder.documentApproveListContent.setText(mListO.get(i).getAJMC());
                holder.documentApproveListPerson.setText(mListO.get(i).getAJZL());
                holder.documentApproveListTime.setText(mListO.get(i).getCQRQ());
                holder.documentCompany.setText(mListO.get(i).getCQDW());
                break;
            case 4://数据查询（个人）-无数据来源
                holder.documentApproveListTitle.setText(mListN.get(i).getAJBH());
                holder.documentApproveListContent.setText(mListN.get(i).getAJMC());
                holder.documentApproveListPerson.setText(mListN.get(i).getAJFL());
                holder.documentApproveListTime.setText(mListN.get(i).getLARQ());
                holder.documentCompany.setText(mListN.get(i).getBADW());
                holder.text3.setText("案件分类：");
                break;
        }
        return view;
    }



     class ViewHolder {
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
        @BindView(R.id.text3)
        TextView text3;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
