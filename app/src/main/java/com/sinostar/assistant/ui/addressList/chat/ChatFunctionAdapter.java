package com.sinostar.assistant.ui.addressList.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sinostar.assistant.utils.AppScreenMgr.getScreenWidth;
import static com.sinostar.assistant.utils.DensityUtils.dp2px;

public class ChatFunctionAdapter extends BaseAdapter {
    int image[] = {};
    String text[] = {};
    Context context;

    ChatFunctionAdapter(Context context, int image[], String text[]) {
        this.context = context;
        this.image = image;
        this.text = text;

    }

    @Override
    public int getCount() {
        return image.length;
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
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.chat_more_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        int height=(getScreenWidth(context)-dp2px(context,88))/4;
        Picasso.with(context).load(image[i]).resize(height,height).centerCrop().into(holder.chatFunctionImage);
//        holder.chatFunctionImage.setBackgroundResource(image[i]);
        holder.chatFunctionText.setText(text[i]);
        return view;
    }


    class ViewHolder {
        @BindView(R.id.chat_function_image)
        ImageView chatFunctionImage;
        @BindView(R.id.chat_function_text)
        TextView chatFunctionText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
