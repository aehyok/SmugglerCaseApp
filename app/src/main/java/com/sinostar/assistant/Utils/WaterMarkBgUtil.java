package com.sinostar.assistant.utils;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.R;

import static com.sinostar.assistant.utils.AppScreenMgr.getScreenHeight;
import static com.sinostar.assistant.utils.AppScreenMgr.getScreenWidth;

/**
 * 水印工具类
 */
public class WaterMarkBgUtil extends Drawable {
    private Paint paint = new Paint();

    private String logo = "SoYoung";

    public WaterMarkBgUtil(String logo) {
        this.logo = logo;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {


//        int width = getBounds().right;
        int width = (getScreenWidth(ApplicationUtil.getContext())) ;
        int height=(getScreenHeight(ApplicationUtil.getContext())) ;
//        int height = getBounds().bottom;

        canvas.drawColor(ApplicationUtil.getContext().getResources().getColor(R.color.white));
        paint.setColor(ApplicationUtil.getContext().getResources().getColor(R.color.watermark));
        paint.setAntiAlias(true);
//        paint.setAlpha(120);
        paint.setTextSize(40);
        canvas.rotate(-30);
        canvas.save();

        float textWidth = paint.measureText(logo);
        int index = 0;
        for (int positionY = height / 10; positionY <= height; positionY += height / 10) {
            float fromX = -width + (index++ % 2) * textWidth;
            for (float positionX = fromX; positionX < width; positionX += textWidth * 2) {
                canvas.drawText(logo, positionX, positionY, paint);
            }
        }


    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }


}
