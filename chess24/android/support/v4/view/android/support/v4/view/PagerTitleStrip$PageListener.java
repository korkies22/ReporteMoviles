/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.DataSetObserver
 */
package android.support.v4.view;

import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;

private class PagerTitleStrip.PageListener
extends DataSetObserver
implements ViewPager.OnPageChangeListener,
ViewPager.OnAdapterChangeListener {
    private int mScrollState;

    PagerTitleStrip.PageListener() {
    }

    @Override
    public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
        PagerTitleStrip.this.updateAdapter(pagerAdapter, pagerAdapter2);
    }

    public void onChanged() {
        PagerTitleStrip.this.updateText(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
        float f = PagerTitleStrip.this.mLastKnownPositionOffset;
        float f2 = 0.0f;
        if (f >= 0.0f) {
            f2 = PagerTitleStrip.this.mLastKnownPositionOffset;
        }
        PagerTitleStrip.this.updateTextPositions(PagerTitleStrip.this.mPager.getCurrentItem(), f2, true);
    }

    @Override
    public void onPageScrollStateChanged(int n) {
        this.mScrollState = n;
    }

    @Override
    public void onPageScrolled(int n, float f, int n2) {
        n2 = n;
        if (f > 0.5f) {
            n2 = n + 1;
        }
        PagerTitleStrip.this.updateTextPositions(n2, f, false);
    }

    @Override
    public void onPageSelected(int n) {
        if (this.mScrollState == 0) {
            PagerTitleStrip.this.updateText(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
            float f = PagerTitleStrip.this.mLastKnownPositionOffset;
            float f2 = 0.0f;
            if (f >= 0.0f) {
                f2 = PagerTitleStrip.this.mLastKnownPositionOffset;
            }
            PagerTitleStrip.this.updateTextPositions(PagerTitleStrip.this.mPager.getCurrentItem(), f2, true);
        }
    }
}
