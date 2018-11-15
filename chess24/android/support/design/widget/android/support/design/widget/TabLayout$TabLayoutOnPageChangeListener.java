/*
 * Decompiled with CFR 0_134.
 */
package android.support.design.widget;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import java.lang.ref.WeakReference;

public static class TabLayout.TabLayoutOnPageChangeListener
implements ViewPager.OnPageChangeListener {
    private int mPreviousScrollState;
    private int mScrollState;
    private final WeakReference<TabLayout> mTabLayoutRef;

    public TabLayout.TabLayoutOnPageChangeListener(TabLayout tabLayout) {
        this.mTabLayoutRef = new WeakReference<TabLayout>(tabLayout);
    }

    @Override
    public void onPageScrollStateChanged(int n) {
        this.mPreviousScrollState = this.mScrollState;
        this.mScrollState = n;
    }

    @Override
    public void onPageScrolled(int n, float f, int n2) {
        TabLayout tabLayout = (TabLayout)((Object)this.mTabLayoutRef.get());
        if (tabLayout != null) {
            n2 = this.mScrollState;
            boolean bl = false;
            boolean bl2 = n2 != 2 || this.mPreviousScrollState == 1;
            if (this.mScrollState != 2 || this.mPreviousScrollState != 0) {
                bl = true;
            }
            tabLayout.setScrollPosition(n, f, bl2, bl);
        }
    }

    @Override
    public void onPageSelected(int n) {
        TabLayout tabLayout = (TabLayout)((Object)this.mTabLayoutRef.get());
        if (tabLayout != null && tabLayout.getSelectedTabPosition() != n && n < tabLayout.getTabCount()) {
            boolean bl = this.mScrollState == 0 || this.mScrollState == 2 && this.mPreviousScrollState == 0;
            tabLayout.selectTab(tabLayout.getTabAt(n), bl);
        }
    }

    void reset() {
        this.mScrollState = 0;
        this.mPreviousScrollState = 0;
    }
}
