package com.sinostar.assistant.ui.mobile.document;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.R;
import com.sinostar.assistant.utils.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.sinostar.assistant.utils.FragmentUtil.addFragment;

/**
 * 文书审批
 */

public class DocumentApproveFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.document_tablayout)
    TabLayout documentTablayout;

    private WaitApproveFragment waitApproveFragment;
    private FinishApproveFragment finishApproveFragment;
    private ReportApproveFragment reportApproveFragment;
    private FragmentUtil fragmentUtil;

    public DocumentApproveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document_approve, container, false);
        unbinder = ButterKnife.bind(this, view);

        ApplicationUtil.setApproveType(1);
        waitApproveFragment = new WaitApproveFragment();
        finishApproveFragment = new FinishApproveFragment();
        reportApproveFragment = new ReportApproveFragment();
        addFragment(getFragmentManager(), waitApproveFragment, R.id.document_approve_fragment_container);
        fragmentUtil = new FragmentUtil((AppCompatActivity) getActivity(), waitApproveFragment, R.id.document_approve_fragment_container);
        init();
        return view;
    }
    private void init(){
        documentTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()){
                case 0:
                    fragmentUtil.switchFragment(waitApproveFragment);
                    break;
                case 1:
                    fragmentUtil.switchFragment(finishApproveFragment);
                    break;
                case 2:
                    fragmentUtil.switchFragment(reportApproveFragment);
                    break;
            }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            ApplicationUtil.setApproveType(1);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

}
