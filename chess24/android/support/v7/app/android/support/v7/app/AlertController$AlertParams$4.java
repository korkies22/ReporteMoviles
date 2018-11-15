/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnMultiChoiceClickListener
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 */
package android.support.v7.app;

import android.content.DialogInterface;
import android.support.v7.app.AlertController;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.AdapterView;

class AlertController.AlertParams
implements AdapterView.OnItemClickListener {
    final /* synthetic */ AlertController val$dialog;
    final /* synthetic */ AlertController.RecycleListView val$listView;

    AlertController.AlertParams(AlertController.RecycleListView recycleListView, AlertController alertController) {
        this.val$listView = recycleListView;
        this.val$dialog = alertController;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
        if (AlertParams.this.mCheckedItems != null) {
            AlertParams.this.mCheckedItems[n] = this.val$listView.isItemChecked(n);
        }
        AlertParams.this.mOnCheckboxClickListener.onClick((DialogInterface)this.val$dialog.mDialog, n, this.val$listView.isItemChecked(n));
    }
}
