/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.DataSetObserver
 */
package android.support.v7.widget;

import android.database.DataSetObserver;
import android.support.v7.widget.ActivityChooserView;

class ActivityChooserView
extends DataSetObserver {
    ActivityChooserView() {
    }

    public void onChanged() {
        super.onChanged();
        ActivityChooserView.this.mAdapter.notifyDataSetChanged();
    }

    public void onInvalidated() {
        super.onInvalidated();
        ActivityChooserView.this.mAdapter.notifyDataSetInvalidated();
    }
}
