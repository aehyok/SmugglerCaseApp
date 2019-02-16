package com.sinostar.assistant.ui.addressList.chat.voice;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.hyphenate.chat.EMMessage;
import com.sinostar.assistant.R;


public class DialogHelper {
    private Dialog mDialog;
    private ImageView mIcon;
    private ImageView mVoice;
    private TextView mLabel;
    private Context context;
    private EMMessage message;


    public DialogHelper(Context context){
        this.context = context;
    }
    /**
     * 显示
     */
    public void showDialog(){
        mDialog = new Dialog(context, R.style.Theme_audioDialog);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_manager, null);
        mDialog.setContentView(view);

        Window dialogWindow = mDialog.getWindow();
        assert dialogWindow != null;
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setAttributes(p);

        mIcon = (ImageView) mDialog.findViewById(R.id.dialog_icon);
        mVoice=mDialog.findViewById(R.id.dialog_voice);
        mLabel = (TextView) mDialog.findViewById(R.id.recorder_dialogtext);
        mDialog.show();
    }
    /**
     * 录音
     */
    public void recording(){
        if (mDialog != null &&  mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLabel.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.drawable.chat_voice1);
            mLabel.setText(context.getResources().getString(R.string.chat_moveUp));
            mLabel.setBackgroundResource(R.drawable.chat_voice_backgroud);
        }
    }
    /**
     * 准备关闭
     */
    public void wantToCancel(){
        if (mDialog != null &&  mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.drawable.chat_voice_cancel);
            mLabel.setText(context.getResources().getString(R.string.chat_release));
            mLabel.setBackgroundResource(R.drawable.chat_voice_text_shape);
        }
    }
    /**
     * 时间太短提示
     */
    public void tooShort(){
        if (mDialog != null &&  mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.drawable.chat_voice_too_short);
            mLabel.setText(context.getResources().getString(R.string.chat_tooShort));
        }
    }
    /**
     * 关闭
     */
    public void dimiss(){
        if (mDialog != null &&  mDialog.isShowing()) {

            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void updateVoiceLevel(int level) {
        if (mDialog != null &&  mDialog.isShowing()) {
            int resId = context.getResources().getIdentifier("v" + level, "drawable", context.getPackageName());
            mVoice.setImageResource(resId);
        }
    }
}
