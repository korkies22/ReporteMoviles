/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.DataSetObserver
 */
package android.support.v4.widget;

import android.database.DataSetObserver;
import android.support.v4.widget.CursorAdapter;

private class CursorAdapter.MyDataSetObserver
extends DataSetObserver {
    CursorAdapter.MyDataSetObserver() {
    }

    public void onChanged() {
        CursorAdapter.this.mDataValid = true;
        CursorAdapter.this.notifyDataSetChanged();
    }

    public void onInvalidated() {
        CursorAdapter.this.mDataValid = false;
        CursorAdapter.this.notifyDataSetInvalidated();
    }
}
