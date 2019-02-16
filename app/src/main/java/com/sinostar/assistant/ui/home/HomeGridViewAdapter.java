package com.sinostar.assistant.ui.home;

import android.content.Context;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinostar.assistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sinostar.assistant.utils.AppScreenMgr.getRealScreenHeight;
import static com.sinostar.assistant.utils.AppScreenMgr.getScreenHeight;
import static com.sinostar.assistant.utils.AppScreenMgr.getScreenWidth;
import static com.sinostar.assistant.utils.AppScreenMgr.getStatusBarHeight;
import static com.sinostar.assistant.utils.DensityUtils.dip2px;
import static com.sinostar.assistant.utils.DensityUtils.dp2px;

/**
 * 主页的Adapter
 */
public class HomeGridViewAdapter extends BaseAdapter {
    String mList[] = {};
    Context context;
    int mImageList[];

    HomeGridViewAdapter(Context context, String list[], int imageList[]) {
        this.context = context;
        this.mList = list;
        this.mImageList = imageList;
    }

    @Override
    public int getCount() {
        return mList.length;
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
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.obtain_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        int itemWidth = (getScreenWidth(context)) / 5;
        ViewGroup.LayoutParams lp = holder.homeImage.getLayoutParams();
        lp.width = itemWidth;
        lp.height = itemWidth;



        int height;
        if(!isNavigationBarAvailable()){
             height = (getScreenHeight(context)-dp2px(context,56)-getStatusBarHeight(context)) / 4;

        }else{
             height = (getRealScreenHeight(context)-dp2px(context,56)-getStatusBarHeight(context)) / 4;
        }
        ViewGroup.LayoutParams params = holder.homeLayout.getLayoutParams();
        params.height = height;




        holder.homeImage.setBackgroundResource(mImageList[i]);
        holder.homeText.setText(mList[i]);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.home_gridview_image)
        ImageView homeImage;
        @BindView(R.id.home_gridview_text)
        TextView homeText;
        @BindView(R.id.home_gridview_layout)
        RelativeLayout homeLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //判断底部导航栏是否显示
    private static boolean isNavigationBarAvailable() {
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        return (!(hasBackKey && hasHomeKey));
    }



}
