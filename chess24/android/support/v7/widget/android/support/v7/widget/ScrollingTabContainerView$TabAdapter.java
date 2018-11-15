/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 */
package android.support.v7.widget;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

private class ScrollingTabContainerView.TabAdapter
extends BaseAdapter {
    ScrollingTabContainerView.TabAdapter() {
    }

    public int getCount() {
        return ScrollingTabContainerView.this.mTabLayout.getChildCount();
    }

    public Object getItem(int n) {
        return ((ScrollingTabContainerView.TabView)ScrollingTabContainerView.this.mTabLayout.getChildAt(n)).getTab();
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        if (view == null) {
            return ScrollingTabContainerView.this.createTabView((ActionBar.Tab)this.getItem(n), true);
        }
        ((ScrollingTabContainerView.TabView)view).bindTab((ActionBar.Tab)this.getItem(n));
        return view;
    }
}
