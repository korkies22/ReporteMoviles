/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.list;

class RefreshingListView
implements Runnable {
    final /* synthetic */ int val$first;

    RefreshingListView(int n) {
        this.val$first = n;
    }

    @Override
    public void run() {
        RefreshingListView.this.removeHeaders();
        if (this.val$first <= 1) {
            RefreshingListView.this.setSelection(RefreshingListView.this.getContentStartIndex());
        }
    }
}
