package com.sinostar.assistant.ui.mobile.link;


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
 * A simple {@link Fragment} subclass.
 */
public class LinkBackFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.link_back_tablayout)
    TabLayout tabLayout;
    private LinkBackWait waitApproveFragment;
    private LinkBackFinish finishApproveFragment;
    private FragmentUtil fragmentUtil;

    public LinkBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_link_back, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApplicationUtil.setApproveType(2);
        waitApproveFragment = new LinkBackWait();
        finishApproveFragment = new LinkBackFinish();
        addFragment(getFragmentManager(), waitApproveFragment, R.id.link_back_fragment_container);
        fragmentUtil = new FragmentUtil((AppCompatActivity) getActivity(), waitApproveFragment, R.id.link_back_fragment_container);
        initListener();
        return view;
    }

    private void initListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        fragmentUtil.switchFragment(waitApproveFragment);
                        break;
                    case 1:
                        fragmentUtil.switchFragment(finishApproveFragment);
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
            ApplicationUtil.setApproveType(2);

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

}
