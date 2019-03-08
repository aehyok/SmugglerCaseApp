package com.sinostar.assistant.ui.BlogMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.bean.HomeBlogModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlogEssenceAdapter extends BaseAdapter{
    Context context;
    List<HomeBlogModel> mList = new ArrayList<>();
    int type;
    private BlogHomeAdapter.ViewHolder holder;

    public BlogEssenceAdapter(Context context){
        this.context = context;
    }

    public void getData(List<HomeBlogModel> list, int type) {
        mList.clear();
        this.mList = list;
        this.type = type;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
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
            holder = new BlogHomeAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (BlogHomeAdapter.ViewHolder) view.getTag();
        }


        holder.approveListImage.setBackgroundResource(R.drawable.approve_warning);
        holder.approveListButton.setVisibility(View.VISIBLE);
        holder.approveListTitle.setText(mList.get(i).getTitle());

        holder.approveListName.setText(mList.get(i).getDescription().substring( 0,40 ));
        holder.approveListPerson.setText("作者："+mList.get(i).getAuthor());
        holder.approveListTime.setText("发布时间："+mList.get(i).getPostDate());
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
