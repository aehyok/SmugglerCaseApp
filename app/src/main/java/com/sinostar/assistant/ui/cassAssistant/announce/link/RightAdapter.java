package com.sinostar.assistant.ui.cassAssistant.announce.link;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.bean.CaseLinkAction;
import com.sinostar.assistant.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RightAdapter extends BaseExpandableListAdapter {
    Context context;
    List<CaseLinkAction> mList = new ArrayList<>();

    RightAdapter(Context context) {
        this.context = context;
    }

    public void getData(List<CaseLinkAction> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mList.get(i).getChildAction().size();
    }

    @Override
    public Object getGroup(int i) {
        return mList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mList.get(i).getChildAction().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.case_of_link_default_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if(mList.get(i).getChildAction().size()==0){
            holder.caseInfoDefaultListImage.setBackgroundResource(R.drawable.ic_keyboard_arrow_right_grey_500_24dp);
        }
        else{
            if(b){
                holder.caseInfoDefaultListImage.setBackgroundResource(R.drawable.turn_on);
            }else{
                holder.caseInfoDefaultListImage.setBackgroundResource(R.drawable.turn_off);

            }
        }

        holder.caseInfoDefaultListText.setText(i + 1 + "、" + mList.get(i).getActionName());


        return view;

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.case_of_link_child_list, null);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        holder.caseLinkChildList.setText(mList.get(i).getChildAction().get(i1).getActionName());

        return view;
    }

    //        指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


    static class ViewHolder {
        @BindView(R.id.case_info_default_list_text)
        TextView caseInfoDefaultListText;
        @BindView(R.id.case_info_default_list_image)
        ImageView caseInfoDefaultListImage;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    class ChildViewHolder {
        @BindView(R.id.case_link_child_list)
        TextView caseLinkChildList;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
