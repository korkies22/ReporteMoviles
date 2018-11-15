/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.v7.widget;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.View;

private class ScrollingTabContainerView.TabClickListener
implements View.OnClickListener {
    ScrollingTabContainerView.TabClickListener() {
    }

    public void onClick(View view) {
        ((ScrollingTabContainerView.TabView)view).getTab().select();
        int n = ScrollingTabContainerView.this.mTabLayout.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view2 = ScrollingTabContainerView.this.mTabLayout.getChildAt(i);
            boolean bl = view2 == view;
            view2.setSelected(bl);
        }
    }
}
