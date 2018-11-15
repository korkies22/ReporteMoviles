/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.viewpagerindicator;

import android.support.v4.view.ViewPager;
import android.view.View;
import com.viewpagerindicator.TabPageIndicator;

class TabPageIndicator
implements View.OnClickListener {
    TabPageIndicator() {
    }

    public void onClick(View object) {
        object = (TabPageIndicator.TabView)((Object)object);
        int n = TabPageIndicator.this.mViewPager.getCurrentItem();
        int n2 = object.getIndex();
        TabPageIndicator.this.mViewPager.setCurrentItem(n2);
        if (n == n2 && TabPageIndicator.this.mTabReselectedListener != null) {
            TabPageIndicator.this.mTabReselectedListener.onTabReselected(n2);
        }
    }
}
