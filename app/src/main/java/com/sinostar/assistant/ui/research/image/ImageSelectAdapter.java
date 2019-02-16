package com.sinostar.assistant.ui.research.image;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzy.imagepicker.bean.ImageItem;
import com.sinostar.assistant.R;
import com.sinostar.assistant.utils.DensityUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.sinostar.assistant.utils.AppScreenMgr.getScreenWidth;


public class ImageSelectAdapter extends RecyclerView.Adapter <ImageSelectAdapter.ViewHolder>{
    Context context;
    List<ImageItem>mList=new ArrayList<>();
    ImageSelectAdapter(Context context){
        this.context=context;
    }
    public void getImage(List<ImageItem>list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ImageSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.image,null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageSelectAdapter.ViewHolder holder, int position) {
        int itemWidth =  ((getScreenWidth(context)- DensityUtils.dp2px(context,60))/ 3);
        if(mList.size()==0){
            Picasso.with(context)
                    .load(R.drawable.add_image)
                    .resize(itemWidth,itemWidth)
                    .centerCrop()
                    .into(holder.imageView);
        }else if(position<mList.size()){
            String s="file://"+mList.get(position).path;
            Picasso.with(context).load(s)
                      .resize(itemWidth,itemWidth)
                    .centerCrop().
                    into(holder.imageView);
        }else{
            Picasso.with(context).load(R.drawable.add_image)
                    .resize(itemWidth,itemWidth)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        int num=1;
        if(mList.size()==0){
            num=1;
//        }else if(mList.size()==9){
//            num=9;
        }else{
            num=mList.size()+1;
        }
        return num;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);

        }
    }
}
