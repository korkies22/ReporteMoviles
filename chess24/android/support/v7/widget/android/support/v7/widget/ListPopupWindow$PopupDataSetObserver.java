/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.DataSetObserver
 */
package android.support.v7.widget;

import android.database.DataSetObserver;
import android.support.v7.widget.ListPopupWindow;

private class ListPopupWindow.PopupDataSetObserver
extends DataSetObserver {
    ListPopupWindow.PopupDataSetObserver() {
    }

    public void onChanged() {
        if (ListPopupWindow.this.isShowing()) {
            ListPopupWindow.this.show();
        }
    }

    public void onInvalidated() {
        ListPopupWindow.this.dismiss();
    }
}
