package com.sinostar.assistant.ui.main;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinostar.assistant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrderFragment extends android.support.v4.app.Fragment {


    public NewOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_new_order, container, false );
    }

}
