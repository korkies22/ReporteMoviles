/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.HorizontalScrollView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package com.viewpagerindicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.IcsLinearLayout;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.R;

public class TabPageIndicator
extends HorizontalScrollView
implements PageIndicator {
    private static final CharSequence EMPTY_TITLE = "";
    private ViewPager.OnPageChangeListener mListener;
    private int mMaxTabWidth;
    private int mSelectedTabIndex;
    private final View.OnClickListener mTabClickListener = new View.OnClickListener(){

        public void onClick(View object) {
            object = (TabView)((Object)object);
            int n = TabPageIndicator.this.mViewPager.getCurrentItem();
            int n2 = object.getIndex();
            TabPageIndicator.this.mViewPager.setCurrentItem(n2);
            if (n == n2 && TabPageIndicator.this.mTabReselectedListener != null) {
                TabPageIndicator.this.mTabReselectedListener.onTabReselected(n2);
            }
        }
    };
    private final IcsLinearLayout mTabLayout;
    private OnTabReselectedListener mTabReselectedListener;
    private Runnable mTabSelector;
    private ViewPager mViewPager;

    public TabPageIndicator(Context context) {
        this(context, null);
    }

    public TabPageIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setHorizontalScrollBarEnabled(false);
        this.mTabLayout = new IcsLinearLayout(context, R.attr.vpiTabPageIndicatorStyle);
        this.addView((View)this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
    }

    private void addTab(int n, CharSequence charSequence, int n2) {
        TabView tabView = new TabView(this.getContext());
        tabView.mIndex = n;
        tabView.setFocusable(true);
        tabView.setOnClickListener(this.mTabClickListener);
        tabView.setText(charSequence);
        if (n2 != 0) {
            tabView.setCompoundDrawablesWithIntrinsicBounds(n2, 0, 0, 0);
        }
        this.mTabLayout.addView((View)tabView, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, -1, 1.0f));
    }

    private void animateToTab(int n) {
        final View view = this.mTabLayout.getChildAt(n);
        if (this.mTabSelector != null) {
            this.removeCallbacks(this.mTabSelector);
        }
        this.mTabSelector = new Runnable(){

            @Override
            public void run() {
                int n = view.getLeft();
                int n2 = (TabPageIndicator.this.getWidth() - view.getWidth()) / 2;
                TabPageIndicator.this.smoothScrollTo(n - n2, 0);
                TabPageIndicator.this.mTabSelector = null;
            }
        };
        this.post(this.mTabSelector);
    }

    @Override
    public void notifyDataSetChanged() {
        this.mTabLayout.removeAllViews();
        PagerAdapter pagerAdapter = this.mViewPager.getAdapter();
        IconPagerAdapter iconPagerAdapter = pagerAdapter instanceof IconPagerAdapter ? (IconPagerAdapter)((Object)pagerAdapter) : null;
        int n = pagerAdapter.getCount();
        for (int i = 0; i < n; ++i) {
            CharSequence charSequence;
            CharSequence charSequence2 = charSequence = pagerAdapter.getPageTitle(i);
            if (charSequence == null) {
                charSequence2 = EMPTY_TITLE;
            }
            int n2 = iconPagerAdapter != null ? iconPagerAdapter.getIconResId(i) : 0;
            this.addTab(i, charSequence2, n2);
        }
        if (this.mSelectedTabIndex > n) {
            this.mSelectedTabIndex = n - 1;
        }
        this.setCurrentItem(this.mSelectedTabIndex);
        this.requestLayout();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mTabSelector != null) {
            this.post(this.mTabSelector);
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mTabSelector != null) {
            this.removeCallbacks(this.mTabSelector);
        }
    }

    public void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getMode((int)n);
        boolean bl = n3 == 1073741824;
        this.setFillViewport(bl);
        int n4 = this.mTabLayout.getChildCount();
        this.mMaxTabWidth = n4 > 1 && (n3 == 1073741824 || n3 == Integer.MIN_VALUE) ? (n4 > 2 ? (int)((float)View.MeasureSpec.getSize((int)n) * 0.4f) : View.MeasureSpec.getSize((int)n) / 2) : -1;
        n3 = this.getMeasuredWidth();
        super.onMeasure(n, n2);
        n = this.getMeasuredWidth();
        if (bl && n3 != n) {
            this.setCurrentItem(this.mSelectedTabIndex);
        }
    }

    @Override
    public void onPageScrollStateChanged(int n) {
        if (this.mListener != null) {
            this.mListener.onPageScrollStateChanged(n);
        }
    }

    @Override
    public void onPageScrolled(int n, float f, int n2) {
        if (this.mListener != null) {
            this.mListener.onPageScrolled(n, f, n2);
        }
    }

    @Override
    public void onPageSelected(int n) {
        this.setCurrentItem(n);
        if (this.mListener != null) {
            this.mListener.onPageSelected(n);
        }
    }

    @Override
    public void setCurrentItem(int n) {
        if (this.mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        this.mSelectedTabIndex = n;
        this.mViewPager.setCurrentItem(n);
        int n2 = this.mTabLayout.getChildCount();
        for (int i = 0; i < n2; ++i) {
            View view = this.mTabLayout.getChildAt(i);
            boolean bl = i == n;
            view.setSelected(bl);
            if (!bl) continue;
            this.animateToTab(n);
        }
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mListener = onPageChangeListener;
    }

    public void setOnTabReselectedListener(OnTabReselectedListener onTabReselectedListener) {
        this.mTabReselectedListener = onTabReselectedListener;
    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        if (this.mViewPager == viewPager) {
            return;
        }
        if (this.mViewPager != null) {
            this.mViewPager.setOnPageChangeListener(null);
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        this.mViewPager = viewPager;
        viewPager.setOnPageChangeListener(this);
        this.notifyDataSetChanged();
    }

    @Override
    public void setViewPager(ViewPager viewPager, int n) {
        this.setViewPager(viewPager);
        this.setCurrentItem(n);
    }

    public static interface OnTabReselectedListener {
        public void onTabReselected(int var1);
    }

    private class TabView
    extends TextView {
        private int mIndex;

        public TabView(Context context) {
            super(context, null, R.attr.vpiTabPageIndicatorStyle);
        }

        public int getIndex() {
            return this.mIndex;
        }

        public void onMeasure(int n, int n2) {
            super.onMeasure(n, n2);
            if (TabPageIndicator.this.mMaxTabWidth > 0 && this.getMeasuredWidth() > TabPageIndicator.this.mMaxTabWidth) {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)TabPageIndicator.this.mMaxTabWidth, (int)1073741824), n2);
            }
        }
    }

}
