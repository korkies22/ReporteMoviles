/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.HorizontalScrollView
 *  android.widget.ImageView
 */
package com.viewpagerindicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.IcsLinearLayout;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.R;

public class IconPageIndicator
extends HorizontalScrollView
implements PageIndicator {
    private Runnable mIconSelector;
    private final IcsLinearLayout mIconsLayout;
    private ViewPager.OnPageChangeListener mListener;
    private int mSelectedIndex;
    private ViewPager mViewPager;

    public IconPageIndicator(Context context) {
        this(context, null);
    }

    public IconPageIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setHorizontalScrollBarEnabled(false);
        this.mIconsLayout = new IcsLinearLayout(context, R.attr.vpiIconPageIndicatorStyle);
        this.addView((View)this.mIconsLayout, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -1, 17));
    }

    private void animateToIcon(int n) {
        final View view = this.mIconsLayout.getChildAt(n);
        if (this.mIconSelector != null) {
            this.removeCallbacks(this.mIconSelector);
        }
        this.mIconSelector = new Runnable(){

            @Override
            public void run() {
                int n = view.getLeft();
                int n2 = (IconPageIndicator.this.getWidth() - view.getWidth()) / 2;
                IconPageIndicator.this.smoothScrollTo(n - n2, 0);
                IconPageIndicator.this.mIconSelector = null;
            }
        };
        this.post(this.mIconSelector);
    }

    @Override
    public void notifyDataSetChanged() {
        this.mIconsLayout.removeAllViews();
        IconPagerAdapter iconPagerAdapter = (IconPagerAdapter)((Object)this.mViewPager.getAdapter());
        int n = iconPagerAdapter.getCount();
        for (int i = 0; i < n; ++i) {
            ImageView imageView = new ImageView(this.getContext(), null, R.attr.vpiIconPageIndicatorStyle);
            imageView.setImageResource(iconPagerAdapter.getIconResId(i));
            this.mIconsLayout.addView((View)imageView);
        }
        if (this.mSelectedIndex > n) {
            this.mSelectedIndex = n - 1;
        }
        this.setCurrentItem(this.mSelectedIndex);
        this.requestLayout();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mIconSelector != null) {
            this.post(this.mIconSelector);
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mIconSelector != null) {
            this.removeCallbacks(this.mIconSelector);
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
        this.mSelectedIndex = n;
        this.mViewPager.setCurrentItem(n);
        int n2 = this.mIconsLayout.getChildCount();
        for (int i = 0; i < n2; ++i) {
            View view = this.mIconsLayout.getChildAt(i);
            boolean bl = i == n;
            view.setSelected(bl);
            if (!bl) continue;
            this.animateToIcon(n);
        }
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mListener = onPageChangeListener;
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

}
