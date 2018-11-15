/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.list;

class RefreshingListView
implements Runnable {
    RefreshingListView() {
    }

    @Override
    public void run() {
        RefreshingListView.this.updateHeader();
        RefreshingListView.this.measureHeader();
        RefreshingListView.this.tryToScrollBack();
    }
}
