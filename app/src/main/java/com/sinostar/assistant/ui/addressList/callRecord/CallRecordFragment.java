package com.sinostar.assistant.ui.addressList.callRecord;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinostar.assistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 *  通话记录
 */
public class CallRecordFragment extends Fragment {


    @BindView(R.id.title_bar_back_text)
    TextView titleBarBackText;
    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    Unbinder unbinder;

    public CallRecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call_record, container, false);
        unbinder = ButterKnife.bind(this, view);
        titleBarText.setText("通话记录");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                getActivity().finish();
                break;
            case R.id.title_bar_back_text:
                getActivity().finish();
                break;
        }
    }
}
