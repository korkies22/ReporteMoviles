/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.v4.view;

import android.support.v4.view.ViewPager;
import android.view.View;

class PagerTabStrip
implements View.OnClickListener {
    PagerTabStrip() {
    }

    public void onClick(View view) {
        PagerTabStrip.this.mPager.setCurrentItem(PagerTabStrip.this.mPager.getCurrentItem() - 1);
    }
}
