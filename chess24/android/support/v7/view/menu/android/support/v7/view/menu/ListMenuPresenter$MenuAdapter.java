/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 */
package android.support.v7.view.menu;

import android.support.v7.view.menu.ListMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

private class ListMenuPresenter.MenuAdapter
extends BaseAdapter {
    private int mExpandedIndex = -1;

    public ListMenuPresenter.MenuAdapter() {
        this.findExpandedIndex();
    }

    void findExpandedIndex() {
        MenuItemImpl menuItemImpl = ListMenuPresenter.this.mMenu.getExpandedItem();
        if (menuItemImpl != null) {
            ArrayList<MenuItemImpl> arrayList = ListMenuPresenter.this.mMenu.getNonActionItems();
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                if (arrayList.get(i) != menuItemImpl) continue;
                this.mExpandedIndex = i;
                return;
            }
        }
        this.mExpandedIndex = -1;
    }

    public int getCount() {
        int n = ListMenuPresenter.this.mMenu.getNonActionItems().size() - ListMenuPresenter.this.mItemIndexOffset;
        if (this.mExpandedIndex < 0) {
            return n;
        }
        return n - 1;
    }

    public MenuItemImpl getItem(int n) {
        int n2;
        ArrayList<MenuItemImpl> arrayList = ListMenuPresenter.this.mMenu.getNonActionItems();
        n = n2 = n + ListMenuPresenter.this.mItemIndexOffset;
        if (this.mExpandedIndex >= 0) {
            n = n2;
            if (n2 >= this.mExpandedIndex) {
                n = n2 + 1;
            }
        }
        return arrayList.get(n);
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        View view2 = view;
        if (view == null) {
            view2 = ListMenuPresenter.this.mInflater.inflate(ListMenuPresenter.this.mItemLayoutRes, viewGroup, false);
        }
        ((MenuView.ItemView)view2).initialize(this.getItem(n), 0);
        return view2;
    }

    public void notifyDataSetChanged() {
        this.findExpandedIndex();
        super.notifyDataSetChanged();
    }
}
