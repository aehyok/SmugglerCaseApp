package com.sinostar.assistant.ui.cassAssistant.announce;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.ui.cassAssistant.announce.link.CaseOfLInk;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.bean.GuideLine;
import com.sinostar.assistant.bean.ObtainEvidenceBean;
import com.sinostar.assistant.R;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.OnMultiItemClickListener;
import com.sinostar.assistant.utils.WaterMarkBgUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;




public class AnnouncePendding extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;
    @BindView(R.id.base_list)
    ListView baseList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private AnnounceAdapter announceAdapter;
    String guidelineId="7030000009602";
    private Gson gson;
    private double page;
    private String resultJson;


    public AnnouncePendding() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.base_list_view, container, false);
        view.setBackground(new WaterMarkBgUtil(ApplicationUtil.getWaterMarkText()));
        unbinder = ButterKnife.bind(this, view);
        announceAdapter = new AnnounceAdapter(getActivity());
        baseList.setAdapter(announceAdapter);
        baseList.setDividerHeight(5);
        progressLayout.setVisibility(View.GONE);
         gson=new Gson();
         page=1;
        getData(page);
        refreshData();
        initListener();
        return view;
    }

    private void getData(final double page) {
        ObserverOnNextListener listener=new ObserverOnNextListener<GuideLine>() {
            @Override
            public void onNext(GuideLine result) {
                if (result.getData()!=null){
                    resultJson=gson.toJson(result);
                    progressLayout.setVisibility(View.GONE);
                    if(page==1){
                        announceAdapter.getData(result.getData());
                    }else{
                        announceAdapter.lodeData(result.getData());
                        refreshLayout.finishLoadMore();
                    }
                }


            }
            @Override
            public void onError(Throwable e) {

            }
        };
        NetMethods.getGuideLinePagging(new MyObserver<List<ObtainEvidenceBean>>(getActivity(),listener),
                guidelineId, "",page, Constent.PAGE_SIZE);
//        NetMethods.getGuideLine(new MyObserver<List<ObtainEvidenceBean>>(getActivity(),listener),guidelineId, "");
    }

    private void refreshData() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                getData(page);
                refreshLayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page=page+1;
                getData(page);
                refreshLayout.finishLoadMore();
                    }

        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void initListener(){
        baseList.setOnItemClickListener(new OnMultiItemClickListener() {
            @Override
            public void onItemClick1(AdapterView<?> adapterView, View view, int i, long l) {
                String guideLineDataJson=gson.toJson(announceAdapter.mList.get(i)) ;
                Intent intent=new Intent(getActivity(), CaseOfLInk.class);
                intent.putExtra("guideLineDataJson",guideLineDataJson);
                startActivity(intent);
            }
        });
    }

}
