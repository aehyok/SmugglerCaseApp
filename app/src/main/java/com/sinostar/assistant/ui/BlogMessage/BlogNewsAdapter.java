package com.sinostar.assistant.ui.BlogMessage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sinostar.assistant.R;
import com.sinostar.assistant.Utils.ImageUtils;
import com.sinostar.assistant.bean.BlogNewsModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;

public class BlogNewsAdapter extends BaseQuickAdapter<BlogNewsModel, BaseViewHolder> {
    private Target loadtarget=null;

    public BlogNewsAdapter() {
        super( R.layout.activity_blog_newsitem );
    }

    @Override
    protected void convert(BaseViewHolder helper, BlogNewsModel item) {
        //Bitmap bitmap=ImageUtils.getimage(item.getTopicIcon());
        //将每一个需要赋值的id和对应的数据绑定
        helper.setText( R.id.test_item_title, item.getTitle() )
                .setText( R.id.test_item_message, item.getSummary() )
                .setText( R.id.title_date, item.getDateAdded() )
               // .setImageBitmap(R.id.login_title_image,bitmap)
                .setText( R.id.title_author, "阅读量：" + item.getViewCount() );

        //setImageView(item.getTopicIcon(),helper);
    }

    public void setImageView(String url,final BaseViewHolder helper){
        if (loadtarget == null) loadtarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                helper.setImageBitmap( R.id.login_title_image,bitmap );
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }


        };

        Picasso.get().load(url).into(loadtarget);
    }
}
