package com.qs.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GuideAcivity extends AppCompatActivity {
    private static final int[] mImageIds={R.mipmap.ic_001,R.mipmap.ic_002,R.mipmap.ic_003};
    private ViewPager mViewPager;
    private LinearLayout ll_point_group;// 引导圆点的父控件
    private ArrayList<ImageView> mImageViewList;
    private int mPointWidth;// 圆点间的距离

    private View viewRedPoint;// 小红点
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_guide_acivity);
        mViewPager= (ViewPager) findViewById(R.id.vp_guide);
        ll_point_group= (LinearLayout) findViewById(R.id.ll_point_group);
        viewRedPoint=findViewById(R.id.view_red_point);
        initViews();
        mViewPager.setAdapter(new GuideAdapter());
        mViewPager.addOnPageChangeListener(new GuidePageListener());

    }
    private void initViews(){
        mImageViewList=new ArrayList<>();
        //初始化引导的三个页面
        for (int i=0;i<mImageIds.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setBackgroundResource(mImageIds[i]);
            mImageViewList.add(imageView);
        }

        //初始化引导页的小圆点
        for (int i=0;i<mImageIds.length;i++){
            View point=new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);// 设置引导页默认圆点
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(DensityUtil.dip2px(this,10),DensityUtil.dip2px(this,10));
            if(i>0){
                layoutParams.leftMargin=DensityUtil.dip2px(this,10);// 设置圆点间隔
            }

            point.setLayoutParams(layoutParams);// 设置圆点的大小
            ll_point_group.addView(point);// 将圆点添加给线性布局
        }

        ll_point_group.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.e("TAG","layout 结束");
                mPointWidth=ll_point_group.getChildAt(1).getLeft()-ll_point_group.getChildAt(0).getLeft();
                Log.e("TAG","圆点距离:" + mPointWidth);
            }
        });


    }

    /**
     * viewpager适配器
     */
    public class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    public class GuidePageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
             Log.e("TAG","当前位置:" + position + ";百分比:" + positionOffset
             + ";移动距离:" + positionOffsetPixels);
            int len = (int)(mPointWidth*positionOffset)+position*mPointWidth;

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewRedPoint.getLayoutParams();
            layoutParams.leftMargin = len;// 设置左边距

            viewRedPoint.setLayoutParams(layoutParams);// 重新给小红点设置布局参数
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
