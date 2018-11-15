/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.DataSetObserver
 */
package de.cisha.android.ui.view;

import android.database.DataSetObserver;

class HorizontalListView
extends DataSetObserver {
    HorizontalListView() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onChanged() {
        de.cisha.android.ui.view.HorizontalListView horizontalListView = HorizontalListView.this;
        synchronized (horizontalListView) {
            HorizontalListView.this.mDataChanged = true;
        }
        HorizontalListView.this.adjustSubviewPositions();
    }

    public void onInvalidated() {
        HorizontalListView.this.reset();
        HorizontalListView.this.adjustSubviewPositions();
    }
}
