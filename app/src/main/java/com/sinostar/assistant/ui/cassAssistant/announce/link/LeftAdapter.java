package com.sinostar.assistant.ui.cassAssistant.announce.link;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinostar.assistant.bean.CaseLinkCase;
import com.sinostar.assistant.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeftAdapter extends BaseAdapter {
    Context context;
    List<CaseLinkCase.CaseOfLinkBean> mList = new ArrayList<>();
    int mPosition=0;


    LeftAdapter(Context context) {
        this.context = context;
    }

    public void getData(List<CaseLinkCase.CaseOfLinkBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
    public void getSelect(int position){
        this.mPosition =position;
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

    private int getType(int position) {
        int type = 0;
        if(mPosition<position){  //未选中（选中之后）
            if(mPosition==position-1){
                type=4; //未选中下一个

            }else{
                type=5; //未选中下两个及之后
            }
        }else if(mPosition==position){
            type=3;  //选中
        }else{  //未选中（选中之前）
            if(position!=0){
                type=2;  //未选中之前
            }else{
                type=1;  //第一个
            }
        }

       return type;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.case_of_link_left_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        switch (getType(i)){
            case 1://第一个
                holder.leftText.setTextColor(context.getResources().getColor(R.color.normal));
                holder.leftText.setBackgroundResource(R.color.left_before);
                holder.leftImage.setBackgroundResource(R.drawable.left1);
                break;
            case 2://未选中之前
                holder.leftText.setTextColor(context.getResources().getColor(R.color.normal));
                holder.leftText.setBackgroundResource(R.color.left_before);
                holder.leftImage.setBackgroundResource(R.drawable.left2);
                break;
            case 3: //选中
                if(i==0){
                    holder.leftText.setTextColor(context.getResources().getColor(R.color.white));
                    holder.leftText.setBackgroundResource(R.color.homeTitleBar);
                    holder.leftImage.setBackgroundResource(R.drawable.left0 );
                }else{
                    holder.leftText.setTextColor(context.getResources().getColor(R.color.white));
                    holder.leftText.setBackgroundResource(R.color.homeTitleBar);
                    holder.leftImage.setBackgroundResource(R.drawable.left3);
                }
                break;
            case 4: //未选中下一个
                if(i==1){
                    holder.leftImage.setBackgroundResource(R.drawable.left4);
                }else{
                    holder.leftImage.setBackgroundResource(R.drawable.left4);
                }
                holder.leftText.setTextColor(context.getResources().getColor(R.color.white));
                holder.leftText.setBackgroundResource(R.color.left_next);

                break;
            case 5://未选中下两个及之后
                holder.leftText.setTextColor(context.getResources().getColor(R.color.white));
                holder.leftText.setBackgroundResource(R.color.left_next);
                holder.leftImage.setBackgroundResource(R.drawable.left5);
                break;

        }
//        if(mPosition ==i){
//            holder.leftText.setTextColor(context.getResources().getColor(R.color.black));
////            holder.leftLayout.setBackgroundResource(R.color.homeTitleBar);
//        }else{
//            holder.leftText.setTextColor(context.getResources().getColor(R.color.normal));
//            holder.leftLayout.setBackgroundResource(R.color.left);
//        }
        holder.leftText.setText(mList.get(i).getLinkName());

        return view;
    }


    static class ViewHolder {
        @BindView(R.id.left_text)
        TextView leftText;

        @BindView(R.id.left_image)
        ImageView leftImage;

        @BindView(R.id.left_layout)
        RelativeLayout leftLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }
}
