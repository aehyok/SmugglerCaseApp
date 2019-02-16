package com.sinostar.assistant.ui.cassAssistant.announce.link;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinostar.assistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 办案助手——通知
 */
public class Fragment4 extends android.support.v4.app.Fragment {


    @BindView(R.id.text)
    TextView text;
    Unbinder unbinder;

    public Fragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finish, container, false);
        unbinder = ButterKnife.bind(this, view);
        text.setText("办案助手——通知");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
