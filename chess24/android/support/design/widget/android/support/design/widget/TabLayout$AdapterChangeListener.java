/*
 * Decompiled with CFR 0_134.
 */
package android.support.design.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

private class TabLayout.AdapterChangeListener
implements ViewPager.OnAdapterChangeListener {
    private boolean mAutoRefresh;

    TabLayout.AdapterChangeListener() {
    }

    @Override
    public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter2) {
        if (TabLayout.this.mViewPager == viewPager) {
            TabLayout.this.setPagerAdapter(pagerAdapter2, this.mAutoRefresh);
        }
    }

    void setAutoRefresh(boolean bl) {
        this.mAutoRefresh = bl;
    }
}
