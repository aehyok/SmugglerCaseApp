package com.sinostar.assistant.ui.emoji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;

import static com.sinostar.assistant.utils.AppScreenMgr.getScreenWidth;
import static com.sinostar.assistant.utils.DensityUtils.dp2px;


/**
 * Created by Administrator on 2018/2/6.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private int unicodeEmoji;


    public GridViewAdapter(Context context,int unicodeEmoji){
        super();
        this.mContext=context;
        this.unicodeEmoji=unicodeEmoji;
    }

    @Override
    public int getCount() {
        return 0x1F64F - 0x1F601;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    class ViewHolder{
        private TextView mEmoji;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        ViewHolder holder = new ViewHolder();
        if(view==null){
            view = inflater.inflate(R.layout.emoji_list, null);
            int itemWidth = (getScreenWidth(ApplicationUtil.getContext()) - dp2px(ApplicationUtil.getContext(), 25)) / 7;
            holder.mEmoji=view.findViewById(R.id.emoji_text);
            ViewGroup.LayoutParams params=  holder.mEmoji.getLayoutParams();
            params.height=itemWidth;
            params.width=itemWidth;
            holder.mEmoji.setLayoutParams(params);
            view.setTag(holder);
    } else{
            holder= (ViewHolder) view.getTag();
        }
        holder.mEmoji.setText(EmojiUtils.getEmojiStringByUnicode(unicodeEmoji+i-1));
        return view;
    }
}
