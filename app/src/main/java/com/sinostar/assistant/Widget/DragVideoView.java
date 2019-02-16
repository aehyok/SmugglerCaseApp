package com.sinostar.assistant.widget;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import android.view.MotionEvent;
import android.widget.VideoView;

/**
 * 可拖拽的视频播放
 */
public class DragVideoView extends VideoView {


    public DragVideoView(Context context) {
        super(context);
    }

    public DragVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//这里重写onMeasure的方法
// TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(0, widthMeasureSpec);//得到默认的大小（0，宽度测量规范）
        int height = getDefaultSize(0, heightMeasureSpec);//得到默认的大小（0，高度度测量规范）
        setMeasuredDimension(width, height); //设置测量尺寸,将高和宽放进去
    }


}
