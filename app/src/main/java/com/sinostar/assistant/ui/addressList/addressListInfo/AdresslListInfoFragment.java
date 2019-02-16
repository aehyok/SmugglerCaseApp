package com.sinostar.assistant.ui.addressList.addressListInfo;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.BaseActivity;
import com.sinostar.assistant.base.OnEditFocusChange;
import com.sinostar.assistant.ui.addressList.chat.Chat;
import com.sinostar.assistant.utils.ClickUtil;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.RecycleViewDivider;
import com.sinostar.assistant.utils.ToastUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

/**
 * 通讯录页面
 */
public class AdresslListInfoFragment extends Fragment {

    AdressAdapter adressAdapter;

    @BindView(R.id.title_bar_back_text)
    TextView titleBarBackText;
    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.title_bar_right_image)
    ImageView titleBarRightImage;
    Unbinder unbinder;
    @BindView(R.id.adress_list)
    SwipeMenuRecyclerView adressList;
    @BindView(R.id.title_bar_back_image)
    ImageButton titleBarBackImage;
    private List<String> usernames = new ArrayList<>();

    public AdresslListInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        titleBarText.setText("通讯录");
        titleBarBackText.setVisibility(View.GONE);
        titleBarRightImage.setBackground(getResources().getDrawable(R.drawable.more));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        adressList.setLayoutManager(linearLayoutManager);
        initSwipeList();

        adressList.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        getData();

        BaseActivity.setOnEdittFoucusChange(new OnEditFocusChange() {
            @Override
            public void isEditextFoucus() {
                titleBarBackImage.requestFocusFromTouch();
            }
        });
        return view;
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取所有好友
                    usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    if (usernames != null && usernames.size() != 0) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adressAdapter = new AdressAdapter(getActivity(), usernames);
                                adressList.setAdapter(adressAdapter);
                            }
                        });
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initSwipeList() {
        /**
         * 消息的点击跳转
         */
        adressList.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                if (!ClickUtil.isFastClick()) {
                    Intent chat = new Intent(getActivity(), Chat.class);
                    chat.putExtra(Constent.PERSON_NAME, usernames.get(position));
                    startActivity(chat);
                }
            }
        });
        /**
         * 消息的左滑按钮设置
         */
        adressList.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                SwipeMenuItem item1 = new SwipeMenuItem(getActivity());
                item1.setBackground(R.color.grey);
                item1.setWidth(dp2px(50));
                item1.setHeight(MATCH_PARENT);
                item1.setWeight(1);
                item1.setText("备注");
                item1.setTextSize(18);
                item1.setTextColor(Color.WHITE);
                rightMenu.addMenuItem(item1);
            }
        });
        /**
         * 消息的左滑按钮点击监听
         */
        adressList.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                switch (menuPosition) {
                    case 0:
                        ToastUtil.showToast(getActivity(), "点击了备注");
                        break;

                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.title_bar_back_image, R.id.title_bar_right_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                getActivity().finish();
                break;
            case R.id.title_bar_right_image:
                break;
        }
    }
}
