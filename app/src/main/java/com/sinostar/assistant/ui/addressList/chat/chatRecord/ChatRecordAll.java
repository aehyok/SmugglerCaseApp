package com.sinostar.assistant.ui.addressList.chat.chatRecord;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.sinostar.assistant.R;
import com.sinostar.assistant.utils.Constent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatRecordAll extends android.support.v4.app.Fragment {


    @BindView(R.id.chat_record_all_list)
    ListView chatRecordAllList;
    Unbinder unbinder;
    private String personName;
    private ChatRecordAllAdapter adapter;
    private Bundle arg;


    public ChatRecordAll() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         arg = getArguments();
         personName=arg.getString(Constent.PERSON_NAME);
    }

    public static ChatRecordAll newInstance(Bundle args) {
        ChatRecordAll fragment = new ChatRecordAll();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_record_all, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle=new Bundle();
        bundle.getString(Constent.PERSON_NAME);
        adapter=new ChatRecordAllAdapter(getActivity());
        chatRecordAllList.setAdapter(adapter);
        initRecord();

        return view;
    }

    /**
     * 初始化聊天记录
     */
    private void initRecord() {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(personName);
        List<EMMessage> messages = conversation.getAllMessages();
        adapter.getData(messages,personName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
