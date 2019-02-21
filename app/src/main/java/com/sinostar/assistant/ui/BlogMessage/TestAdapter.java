package com.sinostar.assistant.ui.BlogMessage;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sinostar.assistant.R;

import java.util.List;

public class TestAdapter extends BaseQuickAdapter<TestEntity.ResultBean.ListBean, BaseViewHolder> {
    public TestAdapter() {
        super(R.layout.item_test);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestEntity.ResultBean.ListBean item) {
        //将每一个需要赋值的id和对应的数据绑定
        helper.setText(R.id.test_item_title, item.getTestTitle())
                .setText(R.id.test_item_message, item.getTestMessage());
    }
}
