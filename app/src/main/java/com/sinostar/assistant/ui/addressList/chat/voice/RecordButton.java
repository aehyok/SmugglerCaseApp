package com.sinostar.assistant.ui.addressList.chat.voice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.sinostar.assistant.R;

import java.io.File;

/**
 * 语音录制按钮
 */
public class RecordButton extends android.support.v7.widget.AppCompatButton implements AudioHelper.AudioStateListener {
    private static final String TAG = "RecordButton";
    //手指向上移动的距离50，如果超过，说明要cancel
    private static final int DISTANCE_Y_CANCEL = 50;
    // 按住说话状态
    private static final int STATE_NORMAL = 0x00;
    // 正在录音状态
    private static final int STATE_RECORDING = 0x01;
    // 准备取消状态
    private static final int STATE_WANT_TO_CANCEL = 0x02;

    // 当前状态
    private int mCurState = STATE_NORMAL;
    // 表示已经录音
    private boolean isRecording;
    //是否是长按了
    private boolean isLongClick;

    private DialogHelper mdDialogHelper;
    private AudioHelper mAudioHelper;

    @Override
    public void onPrepared() {
        mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }

    /**
     * 录音完成回调
     */
    public interface AudioFinishRecordListener {
        void onFinish(float second, String path);
        void onStar();
        void onEnd();
    }

    private AudioFinishRecordListener mListener;

    public void setAudioFinishRecordListener(AudioFinishRecordListener listener) {
        this.mListener = listener;
    }

    public RecordButton(Context context) {
        this(context, null);
    }
    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mdDialogHelper = new DialogHelper(context);
        String dir = Environment.getExternalStorageDirectory() + File.separator + "chat";
        mAudioHelper  = AudioHelper.getInstance(dir);
        mAudioHelper.setOnAudioStateListener(this);


        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isLongClick = true;
                mAudioHelper.prepare();
                return false;
            }
        });

    }



    private Runnable mGetVoiceLevelRunnable = new Runnable() {
        @Override
        public void run() {
            while(isRecording){
                //SystemClock.sleep(100);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //每个0.1秒更新声音s
                mTime += 0.1f;
                mHandler.sendEmptyMessage(MSG_VOICE_CHANGED);

                //控制录音的最大时长
                if((int)mTime == 60 ){
                    isRecording=false;
                    mHandler.sendEmptyMessage(MSG_COUNT_DOWN_DONE);

                }
            }

        }
    };

    private static final int MSG_AUDIO_PREPARED = 0x11;//准备录制声音
    private static final int MSG_VOICE_CHANGED = 0x12;//声音改变
    private static final int MSG_DIALOG_DIMISS = 0x13;//对话框消失
    private  static final int MSG_COUNT_DOWN_DONE =0x14; //时间过长结束
    private float mTime = 0;

    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_PREPARED:
                    isRecording = true;
                    //显示声音对话框
                    mdDialogHelper.showDialog();
                    mListener.onStar();
                    //开启线程获取音量
                    new Thread(mGetVoiceLevelRunnable).start();
                    break;
                case MSG_VOICE_CHANGED:
                    int level = mAudioHelper.getVoiceLevel(6);

                    //在对话框里更新声音的大小
                    mdDialogHelper.updateVoiceLevel(level);
                    break;
                case MSG_DIALOG_DIMISS:
                    mdDialogHelper.dimiss();
                    mListener.onEnd();
                    break;
                case MSG_COUNT_DOWN_DONE:
                    mdDialogHelper.dimiss();
                    mListener.onEnd();
                    if (mListener != null) {
                        mListener.onFinish(mTime, mAudioHelper.getCurrentFilePath());
                    }
                    mAudioHelper.release();
                    reset();
                    break;
                default:
                    break;
            }
        }
    };




    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 按下时，开始播放
                changeState(STATE_RECORDING);

                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecording) {
                    // 根据坐标判断是否要取消
                    if (wantToCancel(x, y)) {
                        changeState(STATE_WANT_TO_CANCEL);
                    } else {
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                //如果没有长按
                if (!isLongClick) {
                    reset();
                    return super.onTouchEvent(event);
                }
                //如果没有录制
                if (!isRecording || mTime < 0.6f) {
                    //显示按的时间太短
                    mdDialogHelper.tooShort();
                    //关闭录制
                    mListener.onEnd();
                    mAudioHelper.cancel();
                    //发送信息关闭资源
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);
                } else if (mCurState == STATE_RECORDING) {
                    mdDialogHelper.dimiss();
                    mListener.onEnd();
                    if (mListener != null) {
                        mListener.onFinish(mTime, mAudioHelper.getCurrentFilePath());
                    }
                    mAudioHelper.release();

                } else if (mCurState == STATE_WANT_TO_CANCEL) {
                    mListener.onEnd();
                    mdDialogHelper.dimiss();
                    mAudioHelper.cancel();
                }
                // 恢复状态等信息
                reset();
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 录音重置
     */
    private void reset() {
        isRecording = false;
        changeState(STATE_NORMAL);
        mTime = 0;
        isLongClick = false;
    }

    /**
     * 手指位置监听
     * @param x 手指所在X
     * @param y 手指所在Y
     * @return
     */
    private boolean wantToCancel(int x, int y) {
        //如果手指不在按钮的x轴范围内
        if (x < 0 || x > getWidth()) {
            return true;
        }
        //getHeight是按钮的高度
        if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
            return true;
        }

        return false;
    }

    /**
     * 根据手指位置改变录制框的状态
     * @param state
     */
    private void changeState(int state) {
        if (mCurState != state) {
            mCurState = state;
        }
        switch (state) {
            case STATE_NORMAL:
                setBackgroundResource(R.drawable.chat_button_shape);
                setText(R.string.chat_normal);
                break;
            case STATE_RECORDING:
                setBackgroundResource(R.drawable.chat_eidt);
                setText(R.string.chat_recording);
                if (isRecording) {
                    mdDialogHelper.recording();
                }
                break;
            case STATE_WANT_TO_CANCEL:
                setBackgroundResource(R.drawable.chat_eidt);
                setText(R.string.chat_want_to_cancel);
                mdDialogHelper.wantToCancel();
                break;
            default:
                break;
        }
    }





}