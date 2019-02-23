package com.sinostar.assistant.ui.image;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.widget.DragPhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageCheckAdapter extends PagerAdapter {
    public static final String TAG = ImageCheckAdapter.class.getSimpleName();
    private List<String> imageUrls;
    private Activity context;
    private DragPhotoView photoView;
    int image[]={};
    int type=1;




    public ImageCheckAdapter() {

    }
    public void getImageCheckData(List<String> imageUrls, Activity activity){
        this.imageUrls = imageUrls;
        this.context = activity;
    }

    /**
     * 引导页图片数据
     * @param image
     * @param type
     * @param activity
     */
    public void getGuideImage(int image[],int type,Activity activity){
        this.context = activity;
        this.image=image;
        this.type=type;
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      View view = null;
        if(type!=1){
            ImageView imageView=new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view=imageView;
            Picasso.get()
                    .load(image[position])
                    .into(imageView);
            container.addView(imageView);
        }else{
            photoView = new DragPhotoView(context);
            view=photoView;
            String url = imageUrls.get(position);

            Picasso.get()
                    .load(url)
                    .into(photoView);
            container.addView(photoView);
            /**
             * 点击屏幕然后activity关闭
             */
            photoView.setOnExitListener(new DragPhotoView.OnExitListener() {
                @Override
                public void onExit(DragPhotoView view, float translateX, float translateY, float w, float h) {
                    context.finish();
                }
            });
            photoView.setOnTapListener(new DragPhotoView.OnTapListener() {
                @Override
                public void onTap(DragPhotoView view) {
                    context.finish();
                }
            });
        }
        return view;

    }

    @Override
    public int getCount() {
        int num=0;
        if(type!=1){
            num=image.length;
        }else{
            num=imageUrls.size();
        }
        return num;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
