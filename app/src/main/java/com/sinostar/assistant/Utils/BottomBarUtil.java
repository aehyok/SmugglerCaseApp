package com.sinostar.assistant.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.R;

public class BottomBarUtil {
    public  static void setTextColor(Context context, int index, TextView[] textViews) {
        for (int i = 0; i < textViews.length; i++) {
            if (i == index) {
                textViews[i].setTextColor(context.getResources().getColor(R.color.blue));
            } else {
                textViews[i].setTextColor(context.getResources().getColor(R.color.black));
                }
            }
        }

    public  static void setImage(Context context, int index,int image1[],int image2[],ImageView[] imageViews) {
        for (int i = 0; i < imageViews.length; i++) {
            if (i == index) {
                imageViews[i].setBackground(context.getResources().getDrawable(image2[i]));
            } else {
                imageViews[i].setBackground(context.getResources().getDrawable(image1[i]));
            }
        }
    }
}
