package com.sinostar.assistant.ui.addressList.chat.chatRecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatRecordMenuListAdapter extends BaseAdapter {
    private Context context;
    private int[] image;
    private String[] text;

    ChatRecordMenuListAdapter(Context context, int image[], String text[]) {
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
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.chat_record_menu_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.chatRecordMenuImage.setBackgroundResource(image[i]);
        holder.chatRecordMenuText.setText(text[i]);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.chat_record_menu_image)
        ImageView chatRecordMenuImage;
        @BindView(R.id.chat_record_menu_text)
        TextView chatRecordMenuText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
