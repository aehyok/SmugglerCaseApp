package com.sinostar.assistant.ui.main;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sinostar.assistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewNewsFragment extends android.support.v4.app.Fragment {

    Unbinder unbinder;
    @BindView( R.id.spinner )
    Spinner spinner;
    public NewNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate( R.layout.fragment_new_news, container, false );
        unbinder = ButterKnife.bind(this, view);
        String[] ctype = new String[]{"全部", "游戏", "电影", "娱乐", "图书"};
        //创建一个数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ctype);
        //设置下拉列表框的下拉选项样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return view;
    }

}
