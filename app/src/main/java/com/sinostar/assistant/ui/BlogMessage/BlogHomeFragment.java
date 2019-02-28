package com.sinostar.assistant.ui.BlogMessage;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinostar.assistant.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;



public class BlogHomeFragment extends Fragment {
    Unbinder unbinder;
    private com.sinostar.assistant.utils.FragmentUtil fragmentUtil;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
}

