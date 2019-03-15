package com.sinostar.assistant.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.bean.BlogNewsModel;
import com.sinostar.assistant.bean.HomeBlogModel;
import com.sinostar.assistant.ui.BlogMessage.BlogHomeAdapter;
import com.sinostar.assistant.ui.BlogMessage.BlogRemindNewsAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewHomeBlogAdapter extends BaseAdapter {

    Context context;
    List<BlogNewsModel> modelList=new ArrayList<BlogNewsModel>(  );
    int type;
    private NewHomeBlogAdapter.ViewHolder holder;
    public NewHomeBlogAdapter(Context context,List<BlogNewsModel> list)
    {
        this.context=context;
        modelList=list;
    }

    public void getData(List<BlogNewsModel> list, int type) {
        modelList.clear();
        this.modelList = list;
        this.type = type;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (view == null) {
            view = inflater.inflate(R.layout.activity_newhome_blog_list, null);
            holder = new NewHomeBlogAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (NewHomeBlogAdapter.ViewHolder) view.getTag();
        }


        holder.blogDate.setText(modelList.get( i ).getDateAdded());
        holder.blogTitle.setText( modelList.get(i).getTitle());
        Picasso.get().load( modelList.get( i ).getTopicIcon() ).into(holder.blogImage);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.newBlogTitle)
        TextView blogTitle;
        @BindView(R.id.newBlogDate)
        TextView blogDate;
        @BindView(R.id.newBlogImage)
        ImageView blogImage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
