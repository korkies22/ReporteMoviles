/*
 * Decompiled with CFR 0_134.
 */
package android.support.design.widget;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

public static class TabLayout.ViewPagerOnTabSelectedListener
implements TabLayout.OnTabSelectedListener {
    private final ViewPager mViewPager;

    public TabLayout.ViewPagerOnTabSelectedListener(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        this.mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }
}
