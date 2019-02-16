package com.sinostar.assistant.ui.emoji;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.sinostar.assistant.R;

/**
 * Created by TitleZWC on 2017/4/15.
 */

public class EmojiViewHolder extends RecyclerView.ViewHolder {
    private TextView mEmoji;

    public EmojiViewHolder(View itemView) {
        super(itemView);
        mEmoji = (TextView) itemView.findViewById(R.id.tv_emoji);
    }

    public void render(int unicodeEmoji, final EmojiAdapter.OnEmojiClickListener onEmojiClickListener) {
        mEmoji.setText(EmojiUtils.getEmojiStringByUnicode(unicodeEmoji));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmojiClickListener.onEmojiCLick(mEmoji.getText().toString());
            }
        });
    }
}
