package com.mjordan.specialslidingpagerstrip.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt on 6/19/14.
 */
public class SlidingPagerStrip extends HorizontalScrollView implements ViewPager.OnPageChangeListener, View.OnClickListener{

    private LinearLayout mTabLayout;
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private LayoutInflater mLayoutInflater;

    private Path mPath;
    private Paint mPaint;

    private List<TextView> mTitles;

    private int mCurrentPage;
    private float mOffset;

    private int mPathOffset;

    public SlidingPagerStrip(Context context) {
        this(context, null);
    }

    public SlidingPagerStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingPagerStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mOffset = 0;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xff242424);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
        mPath.quadTo(20, 5, 30, 30);
        mPath.quadTo(40, 5, 60, 0);
        mPath.lineTo(0, 0);

        mPathOffset = 0;

        setEnabled(false);

        mLayoutInflater = LayoutInflater.from(context);
        mTitles = new ArrayList<TextView>();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() > 0) {
            throw new IllegalArgumentException("SlidingPagerStrip cannot be inflated with children.");
        }

        mLayoutInflater.inflate(R.layout.view_sliding_power_strip, this);

        mTabLayout = (LinearLayout) findViewById(R.id.tab_layout);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        TextView title = mTitles.get(mCurrentPage);

        int center = (int) (title.getLeft() + title.getWidth() / 2 + mOffset * title.getWidth());

        mPath.offset(-mPathOffset, 0);
        mPath.offset(center - 20, 0);
        mPathOffset = center - 20;

        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            for (TextView tv : mTitles) {
                int width = Math.max(getMeasuredWidth() / mAdapter.getCount(), getMeasuredWidth() / 4);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(params);
            }
        }
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(this);

        mAdapter = mViewPager.getAdapter();

        mTabLayout.removeAllViews();
        mTitles.clear();

        for (int i = 0; i < mAdapter.getCount(); i++) {
            TextView title = new TextView(getContext());
            title.setTextColor(Color.BLACK);
            title.setTextSize(18f);
            title.setText(mAdapter.getPageTitle(i));
            title.setGravity(Gravity.CENTER_HORIZONTAL);
            title.setBackgroundResource(R.drawable.title_background_default);
            title.setOnClickListener(this);

            title.setPadding(0, 20, 0, 20);

            title.setTag(i);

            mTabLayout.addView(title);

            mTitles.add(title);
        }

        mCurrentPage = 0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        TextView title = mTitles.get(position);

        boolean scrollingRight =  mCurrentPage == position;

        if (scrollingRight) {
            if (position > 0) {
                smoothScrollTo((int) (title.getLeft() - title.getWidth() + positionOffset * title.getWidth()), 0);
            }
        }


        mCurrentPage = position;
        mOffset = positionOffset;

        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {
        mViewPager.setCurrentItem((Integer) v.getTag());
    }
}
