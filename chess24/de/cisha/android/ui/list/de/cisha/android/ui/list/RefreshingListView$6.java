/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.ui.list;

import android.view.View;
import de.cisha.android.ui.list.RefreshingListViewFooterView;

class RefreshingListView
implements Runnable {
    RefreshingListView() {
    }

    @Override
    public void run() {
        RefreshingListView.this.removeFooterView((View)RefreshingListView.this._footerView);
    }
}
