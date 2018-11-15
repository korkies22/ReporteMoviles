/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.widget.ArrayAdapter
 */
package android.support.v7.app;

import android.content.Context;
import android.support.v7.app.AlertController;
import android.widget.ArrayAdapter;

private static class AlertController.CheckedItemAdapter
extends ArrayAdapter<CharSequence> {
    public AlertController.CheckedItemAdapter(Context context, int n, int n2, CharSequence[] arrcharSequence) {
        super(context, n, n2, (Object[])arrcharSequence);
    }

    public long getItemId(int n) {
        return n;
    }

    public boolean hasStableIds() {
        return true;
    }
}
