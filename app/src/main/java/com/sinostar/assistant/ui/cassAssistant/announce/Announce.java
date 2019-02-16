package com.sinostar.assistant.ui.cassAssistant.announce;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.ui.cassAssistant.announce.link.FinishFragment;
import com.sinostar.assistant.ui.cassAssistant.announce.link.Fragment3;
import com.sinostar.assistant.ui.cassAssistant.announce.link.Fragment4;
import com.sinostar.assistant.utils.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.sinostar.assistant.utils.AppScreenMgr.getScreenHeight;
import static com.sinostar.assistant.utils.FragmentUtil.addFragment;


public class Announce extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.announce_image)
    ImageView announceImage;
    @BindView(R.id.announce_tablayout)
    TabLayout announceTablayout;
    private FragmentUtil fragmentUtil;
    private Fragment fragment3;
    private Fragment fragment4;
    private Fragment finishFragment;
    private AnnouncePendding announcePendding;


    public Announce() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_announce, container, false);
        unbinder = ButterKnife.bind(this, view);
       fragment3=new Fragment3();
       fragment4=new Fragment4();
       finishFragment=new FinishFragment();
         announcePendding = new AnnouncePendding();
        addFragment(getFragmentManager(), announcePendding, R.id.announce_container);
        fragmentUtil=new FragmentUtil((AppCompatActivity) getActivity(),announcePendding,R.id.announce_container);
        init();
        initListener();
        return view;
    }

    private void initListener() {
        announceTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        fragmentUtil.switchFragment(announcePendding);
                        break;
                    case 1:
                        fragmentUtil.switchFragment(finishFragment);
                        break;
                    case 2:
                        fragmentUtil.switchFragment(fragment3);
                        break;
                    case 3:
                        fragmentUtil.switchFragment(fragment4);
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

    private void init() {
        int height = getScreenHeight(getActivity()) / 4;
        ViewGroup.LayoutParams params = announceImage.getLayoutParams();
        params.height = height;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
