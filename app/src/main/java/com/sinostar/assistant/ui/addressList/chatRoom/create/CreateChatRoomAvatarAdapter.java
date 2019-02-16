package com.sinostar.assistant.ui.addressList.chatRoom.create;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.utils.CircleTransform;
import com.sinostar.assistant.utils.MapUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateChatRoomAvatarAdapter extends RecyclerView.Adapter<CreateChatRoomAvatarAdapter.ViewHolder> {
    private final Context context;
    private Map<String, Object> map=new HashMap<>();
    private List<Object> avatarList=new ArrayList<>();


    CreateChatRoomAvatarAdapter(Context context) {
        this.context = context;
    }


    public void additem(Map<String, Object> map){
        avatarList.clear();
        if(map!=null&&map.size()!=0){
            this.map=map;
            avatarList=MapUtil.getValueList(map);
        }
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(avatarList!=null&&avatarList.size()!=0){
            Picasso.with(context)
                    .load((String) avatarList.get(position))
                    .transform(new CircleTransform(context))
                    .into(holder.imageItem);
        }

    }

    @Override
    public int getItemCount() {
        return map!=null?map.size():0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_item)
        ImageView imageItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
