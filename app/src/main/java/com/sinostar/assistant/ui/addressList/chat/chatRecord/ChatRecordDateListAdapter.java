package com.sinostar.assistant.ui.addressList.chat.chatRecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.utils.OnMultiItemClickListener;
import com.sinostar.assistant.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatRecordDateListAdapter extends BaseAdapter {
    private Context context;
    String []text;
    List<String>mList=new ArrayList<>();

    ChatRecordDateListAdapter(Context context,String []text,List<String> list) {
        this.context = context;
        this.text=text;
        mList.clear();
        mList.addAll(list);

//        for (int j=1;j<10;j++){
//            mList.remove(0);
//        }
//        for(int j=31;j>28;j--){
//            mList.remove(mList.size()-1);
//        }
//        for (int j=0;j<6;j++){
//            mList.add(j,"");
//        }

    }

    @Override
    public int getCount() {
        return text.length;
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
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.chat_record_date_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.chatRecordDateListText.setText(text[i]);


        ChatRecordDateGridAdapter adapter=new ChatRecordDateGridAdapter(context,mList);
        holder.chatRecordDateListGridview.setAdapter(adapter);
        holder.chatRecordDateListGridview.setOnItemClickListener(new OnMultiItemClickListener() {
            @Override
            public void onItemClick1(AdapterView<?> adapterView, View view, int i, long l) {
                ToastUtil.showToast(context,mList.get(i));
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.chat_record_date_list_text)
        TextView chatRecordDateListText;
        @BindView(R.id.chat_record_date_list_gridview)
        GridView chatRecordDateListGridview;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
