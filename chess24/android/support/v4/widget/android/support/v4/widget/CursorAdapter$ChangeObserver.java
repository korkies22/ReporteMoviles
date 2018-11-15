/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.ContentObserver
 *  android.os.Handler
 */
package android.support.v4.widget;

import android.database.ContentObserver;
import android.os.Handler;
import android.support.v4.widget.CursorAdapter;

private class CursorAdapter.ChangeObserver
extends ContentObserver {
    CursorAdapter.ChangeObserver() {
        super(new Handler());
    }

    public boolean deliverSelfNotifications() {
        return true;
    }

    public void onChange(boolean bl) {
        CursorAdapter.this.onContentChanged();
    }
}
