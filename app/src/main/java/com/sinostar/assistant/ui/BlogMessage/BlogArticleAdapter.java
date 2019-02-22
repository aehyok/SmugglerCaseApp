package com.sinostar.assistant.ui.BlogMessage;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sinostar.assistant.R;
import com.sinostar.assistant.bean.ArticleModel;

public class BlogArticleAdapter extends BaseQuickAdapter<ArticleModel.Article, BaseViewHolder> {
    public BlogArticleAdapter() {
        super(R.layout.activity_blog_item );
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleModel.Article item) {
        //将每一个需要赋值的id和对应的数据绑定
        helper.setText(R.id.test_item_title, item.getTitle())
                .setText(R.id.test_item_message, item.getContent())
                .setText(R.id.title_date, item.getDate())
                .setText( R.id.title_author,"阅读量："+item.getCount().toString() );
    }
}
