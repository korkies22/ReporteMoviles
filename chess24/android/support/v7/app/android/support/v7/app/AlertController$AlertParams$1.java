/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ArrayAdapter
 */
package android.support.v7.app;

import android.content.Context;
import android.support.v7.app.AlertController;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

class AlertController.AlertParams
extends ArrayAdapter<CharSequence> {
    final /* synthetic */ AlertController.RecycleListView val$listView;

    AlertController.AlertParams(Context context, int n, int n2, CharSequence[] arrcharSequence, AlertController.RecycleListView recycleListView) {
        this.val$listView = recycleListView;
        super(context, n, n2, (Object[])arrcharSequence);
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        view = super.getView(n, view, viewGroup);
        if (AlertParams.this.mCheckedItems != null && AlertParams.this.mCheckedItems[n]) {
            this.val$listView.setItemChecked(n, true);
        }
        return view;
    }
}
