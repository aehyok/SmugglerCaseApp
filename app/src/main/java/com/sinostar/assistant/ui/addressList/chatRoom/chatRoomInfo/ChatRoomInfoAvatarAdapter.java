package com.sinostar.assistant.ui.addressList.chatRoom.chatRoomInfo;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.utils.AppScreenMgr;
import com.sinostar.assistant.utils.CircleTransform;
import com.sinostar.assistant.utils.DensityUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatRoomInfoAvatarAdapter extends BaseAdapter {
    private final boolean isMyGroup;
    private final int imageWidth;
    private Context context;
    private List<String>mList;


    ChatRoomInfoAvatarAdapter(Context context, List<String> list, boolean isMyGroup) {
        mList=new ArrayList<>();
        this.context = context;
        this.isMyGroup=isMyGroup;
        this.mList=list;
         imageWidth=(AppScreenMgr.getScreenWidth(context)- DensityUtils.dp2px(context,104))/5;
    }

    @Override
    public int getCount() {
        return isMyGroup?mList.size()+2:mList.size()+1;
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
            view = LayoutInflater.from(context).inflate(R.layout.image_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if(i<mList.size()){
            Picasso.get()
                    .load( ChatDBHelper.getUserAvatar(mList.get(i)))
                    .resize(imageWidth,imageWidth)
                    .centerCrop()
                    .transform(new CircleTransform(context))
                    .into(holder.imageItem);

        }else{
            if(i==mList.size()){
                Picasso.get()
                        .load(R.drawable.group_add)
                        .resize(imageWidth,imageWidth)
                        .centerCrop()
                        .into(holder.imageItem);
            }else{
                Picasso.get()
                        .load(R.drawable.group_delete)
                        .resize(imageWidth,imageWidth)
                        .centerCrop()
                        .into(holder.imageItem);
            }

        }
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.image_item)
        ImageView imageItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
