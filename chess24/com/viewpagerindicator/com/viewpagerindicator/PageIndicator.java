/*
 * Decompiled with CFR 0_134.
 */
package com.viewpagerindicator;

import android.support.v4.view.ViewPager;

public interface PageIndicator
extends ViewPager.OnPageChangeListener {
    public void notifyDataSetChanged();

    public void setCurrentItem(int var1);

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener var1);

    public void setViewPager(ViewPager var1);

    public void setViewPager(ViewPager var1, int var2);
}
