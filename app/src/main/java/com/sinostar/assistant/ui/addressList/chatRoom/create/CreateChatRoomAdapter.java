package com.sinostar.assistant.ui.addressList.chatRoom.create;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateChatRoomAdapter extends BaseAdapter {
    private Context context;
    List<String> mList=new ArrayList<>();
    Map<String,Object>avatarMap=new LinkedHashMap<>();
    private int position=-1;
    private  OnUserSelectListener onUserSelectListener;
    private List<String>groupMembers=new ArrayList<>();

    CreateChatRoomAdapter(Context context) {
        this.context = context;
    }

    public void getData(List<String> list,List<String> list1){

        if(list!=null){
            mList.clear();
            mList.addAll(list);
        }
        if(list1!=null){
            this.groupMembers.clear();
            this.groupMembers.addAll(list1);
        }
        notifyDataSetChanged();
    }
    public void getCheck(int position){
        this.position=position;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_child, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.listChildCheckbox.setVisibility(View.VISIBLE);
        holder.listChildName.setText(ChatDBHelper.getUserName(mList.get(i)));
        Picasso.get()
                .load(ChatDBHelper.getUserAvatar(mList.get(i)))
                .transform(new CircleTransform(context))
                .into(holder.listChildImage);
        if(groupMembers.size()!=0&&groupMembers.contains(mList.get(i))){
            holder.listChildCheckbox.setBackground(context.getResources().getDrawable(R.drawable.checkbox_enable));
        }else{
            if(i==position){
                if(holder.listChildCheckbox.isChecked()){
                    holder.listChildCheckbox.setChecked(false);
                    avatarMap.remove(mList.get(i));

                }else{
                    holder.listChildCheckbox.setChecked(true);
                    avatarMap.put(mList.get(i),ChatDBHelper.getUserAvatar(mList.get(i)));
                }

            }
            onUserSelectListener.onSeleceted(avatarMap);
        }

        return view;
    }

    public interface OnUserSelectListener{
        void onSeleceted(Map<String,Object>avatarMap);
    }
    public  void setOnUserSelectListener(OnUserSelectListener onUserSelectListener1){
        onUserSelectListener=onUserSelectListener1;
    }

    static class ViewHolder {
        @BindView(R.id.list_child_checkbox)
        CheckBox listChildCheckbox;
        @BindView(R.id.list_child_image)
        ImageView listChildImage;
        @BindView(R.id.list_child_name)
        TextView listChildName;
        @BindView(R.id.list_child_company)
        TextView listChildCompany;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
