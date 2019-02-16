package com.sinostar.assistant.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.sinostar.assistant.R;
import com.sinostar.assistant.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 引导页面
 */
public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.guide_image)
    ImageView guideImage;
    @BindView(R.id.vp_guide)
    ViewPager vp_guide;
    @BindView(R.id.guide_enter_image)
    ImageView guideEnterImage;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.iv_dot_now)
    ImageView ivDotNow;
    @BindView(R.id.guide_layout)
    RelativeLayout guideLayout;
    private int pics[] = {R.drawable.ydy1, R.drawable.ydy2, R.drawable.ydy3};
    private GuideActivity context;

    private List<ImageView> mImgList;//导航图集合

    private int mCurrentIndex = 0;//当前小圆点的位置
    private int mDotDis;//小圆点的距离
    private int[] imgArray = {R.drawable.ydy1, R.drawable.ydy2, R.drawable.ydy3};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        context = GuideActivity.this;
        QMUIStatusBarHelper.translucent(context);
        SharedPreferences sharedPreferences = this.getSharedPreferences("is", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirstRun) {//第一次运行打开引导页
            guideLayout.setVisibility(View.VISIBLE);
            guideImage.setVisibility(View.GONE);
        mImgList = new ArrayList<>();
        for (int i = 0; i < imgArray.length; i++) {
            ImageView imageView = new ImageView(this);//获取3个圆点
            imageView.setImageResource(imgArray[i]);
            mImgList.add(imageView);
            ImageView dot = new ImageView(this);
            dot.setImageResource(R.drawable.circle_unselect);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = 35;//设置圆点边距
            }
            dot.setLayoutParams(params);
            llContainer.addView(dot);//将圆点添加到容器中
        }
            vp_guide.setAdapter(new MyAdapter(mImgList));
        //添加监听
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
                if (position == 2) {
                    guideEnterImage.setVisibility(View.VISIBLE);
                    guideEnterImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    guideEnterImage.setVisibility(View.GONE);
                }
                // 滚动过程中
                // 红色小圆点的移动距离=移动百分比*两个圆点的间距
                // 更新小红点距离
                int dis = (int) (mDotDis * positionOffset) + position * mDotDis;//
                // 因为移动完一个界面后，百分比会归0，所以要加上移动过的单位position*mPointDis
                //获取小圆点的布局属性，更新左边距
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivDotNow
                        .getLayoutParams();
                params.leftMargin = dis;// 修改左边距
                // 重新设置布局参数
                ivDotNow.setLayoutParams(params);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 监听layout方法结束的事件，位置确定之后获取圆点间距
        // 视图树
            ivDotNow.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                //视图树移除监听
                ivDotNow.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //计算得到小圆点距离
                mDotDis = llContainer.getChildAt(1).getLeft() - llContainer.getChildAt(0)
                        .getLeft();
            }
        });
            editor.putBoolean("isFirstRun", false);
            editor.apply();
        } else {//打开启动页
            guideLayout.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }
    }
    public class MyAdapter extends PagerAdapter {
        private List<ImageView> mViewList;

        public MyAdapter(List<ImageView> viewList) {
            mViewList = viewList;
        }

        @Override
        public ImageView instantiateItem(ViewGroup container, int position) {
            ImageView view = mViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }

}
