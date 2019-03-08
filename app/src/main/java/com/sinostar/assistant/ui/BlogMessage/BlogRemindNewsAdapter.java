package com.sinostar.assistant.ui.BlogMessage;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlogRemindNewsAdapter extends BaseAdapter {
    Context context;
    List<BlogNewsModel> modelList=new ArrayList<BlogNewsModel>(  );
    int type;
    private ViewHolder holder;

    public BlogRemindNewsAdapter(Context context) {
        this.context=context;
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
            view = inflater.inflate(R.layout.document_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.approveListImage.setBackgroundResource(R.drawable.approve_warning);
        holder.approveListButton.setVisibility(View.VISIBLE);
        holder.approveListTitle.setText(modelList.get(i).getTitle());

        holder.approveListName.setText(modelList.get(i).getSummary().substring( 0,40 ));
        holder.approveListPerson.setText("访问量："+modelList.get(i).getViewCount());
        holder.approveListTime.setText("发布时间："+modelList.get(i).getDateAdded());
//
//        return view;LayoutInflater inflater = LayoutInflater.from(context);
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.approve_list_image)
        ImageView approveListImage;
        @BindView(R.id.approve_list_title)
        TextView approveListTitle;
        @BindView(R.id.approve_list_name)
        TextView approveListName;
        @BindView(R.id.approve_list_person)
        TextView approveListPerson;
        @BindView(R.id.approve_list_time)
        TextView approveListTime;
        @BindView(R.id.approve_list_button)
        TextView approveListButton;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
