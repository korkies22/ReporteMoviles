/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
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

    AlertController.AlertParams(AlertController alertController) {
        this.val$dialog = alertController;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
        AlertParams.this.mOnClickListener.onClick((DialogInterface)this.val$dialog.mDialog, n);
        if (!AlertParams.this.mIsSingleChoice) {
            this.val$dialog.mDialog.dismiss();
        }
    }
}
