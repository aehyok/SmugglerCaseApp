package com.sinostar.assistant.ui.BlogMessage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sinostar.assistant.R;
import com.sinostar.assistant.bean.BlogNewsModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class BlogNewsAdapter extends BaseQuickAdapter<BlogNewsModel, BaseViewHolder> {
    private Target loadtarget=null;

    public BlogNewsAdapter() {
        super( R.layout.activity_blog_newsitem );
    }

    @Override
    protected void convert(BaseViewHolder helper, BlogNewsModel item) {
        //将每一个需要赋值的id和对应的数据绑定
        ImageView imageView=helper.getView( R.id.login_title_images );
        helper.setText( R.id.test_item_title, item.getTitle() )
               .setText( R.id.test_item_message, item.getSummary() )
               .setText( R.id.title_date, item.getDateAdded() )
               .setText( R.id.title_author, "阅读量：" + item.getViewCount() );
        try
        {
            Picasso.get().load( item.getTopicIcon() ).into( imageView );
        }
        catch(Throwable e)
        {
            throw e;
        }

        //setImageView(item.getTopicIcon(),helper);
    }
}
