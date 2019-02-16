package com.sinostar.assistant.ui.addressList.chat.voice;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class AudioHelper {

    private MediaRecorder mMediaRecorder;
    private String mDir;
    private String mCurrentFilePath;

    private static AudioHelper mInstance;

    private boolean isPrepared;
    public AudioHelper(String dir){
        mDir = dir;
    };



    /**
     * 回调准备完毕
     */
    public interface AudioStateListener {
        void onPrepared();
    }

    public AudioStateListener mListener;

    public void setOnAudioStateListener(AudioStateListener listener){
        mListener = listener;
    }

    public static AudioHelper getInstance(String dir){
        if (mInstance == null) {
            synchronized (AudioHelper.class) {
                if (mInstance == null) {
                    mInstance = new AudioHelper(dir);
                }
            }
        }
        return mInstance;
    }


    /**
     * 准备
     */
    public void prepare() {
        try {
            isPrepared = false;
            File dir = new File(mDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            String fileName = generateFileName();

            File file = new File(dir, fileName);

            mCurrentFilePath = file.getAbsolutePath();

            mMediaRecorder = new MediaRecorder();
            //设置输出文件
            mMediaRecorder.setOutputFile(file.getAbsolutePath());
            //设置MediaRecorder的音频源为麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置音频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            //设置音频的格式为amr
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            //准备结束
            isPrepared = true;
            if (mListener != null) {
                mListener.onPrepared();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *  生成UUID唯一标示符
     *  算法的核心思想是结合机器的网卡、当地时间、一个随即数来生成GUID .amr音频文件
     *
     * @return
     */
    private String generateFileName() {
        return UUID.randomUUID().toString()+".amr";
    }

    /**
     * 获取声音的大小等级
     * @param maxLevel  最大声音对应等级
     * @return
     */
    public int getVoiceLevel(int maxLevel) {
        if (isPrepared) {
            //获得最大的振幅getMaxAmplitude() 1-32767
            try {
                return maxLevel * mMediaRecorder.getMaxAmplitude()/32768+1;
            } catch (Exception e) {

            }
        }
        return 1;
    }

    public void release() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    public void cancel(){
        release();
        if(mCurrentFilePath!=null) {
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;
        }
    }
    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }
}
