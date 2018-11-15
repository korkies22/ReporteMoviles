/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.DataSetObserver
 */
package android.support.v4.view;

import android.database.DataSetObserver;
import android.support.v4.view.ViewPager;

private class ViewPager.PagerObserver
extends DataSetObserver {
    ViewPager.PagerObserver() {
    }

    public void onChanged() {
        ViewPager.this.dataSetChanged();
    }

    public void onInvalidated() {
        ViewPager.this.dataSetChanged();
    }
}
