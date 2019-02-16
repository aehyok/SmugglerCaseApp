package com.sinostar.assistant.ui.addressList.addressListInfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdressAdapter extends SwipeMenuRecyclerView.Adapter<AdressAdapter.ViewHolder> {
    private Context context;
    List<String> mList=new ArrayList<>();

    AdressAdapter(Context context,List<String>list) {
        this.context = context;
        mList.clear();
        mList.addAll(list);
    }


    @Override
    public AdressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_child, null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AdressAdapter.ViewHolder holder, int i) {
        holder.listChildName.setText(ChatDBHelper.getUserName(mList.get(i)));
        holder.listChildName.setText(ChatDBHelper.getUserName(mList.get(i)));
        ChatHelper.setPersonAvatar(context,mList.get(i),holder.listChildImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.list_child_image)
        ImageView listChildImage;
        @BindView(R.id.list_child_name)
        TextView listChildName;
        @BindView(R.id.list_child_company)
        TextView listChildCompany;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
