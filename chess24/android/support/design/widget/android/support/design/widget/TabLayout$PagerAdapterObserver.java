/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.DataSetObserver
 */
package android.support.design.widget;

import android.database.DataSetObserver;
import android.support.design.widget.TabLayout;

private class TabLayout.PagerAdapterObserver
extends DataSetObserver {
    TabLayout.PagerAdapterObserver() {
    }

    public void onChanged() {
        TabLayout.this.populateFromPagerAdapter();
    }

    public void onInvalidated() {
        TabLayout.this.populateFromPagerAdapter();
    }
}
