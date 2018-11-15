/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.list;

import de.cisha.android.ui.list.RefreshingListHeaderView;

class RefreshingListView
implements Runnable {
    RefreshingListView() {
    }

    @Override
    public void run() {
        int n = RefreshingListView.this._headerView.getBottom();
        RefreshingListView.this.smoothScrollBy(n, 1000);
    }
}
