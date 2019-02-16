package com.sinostar.assistant.ui.mobile.apply;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinostar.assistant.R;


public class ApplyApproveFragment extends Fragment {


    public ApplyApproveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_apply_approve, container, false);
        return view;
    }

}
