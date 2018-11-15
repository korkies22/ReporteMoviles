/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package de.cisha.android.board;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;

class AbstractNetworkContentFragment
implements Runnable {
    AbstractNetworkContentFragment() {
    }

    @Override
    public void run() {
        if (AbstractNetworkContentFragment.this._networkLostInfo == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)AbstractNetworkContentFragment.this.getActivity());
            builder.setCancelable(false);
            builder.setNeutralButton((CharSequence)"ok", new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    dialogInterface.cancel();
                    AbstractNetworkContentFragment.this._networkLostInfo = null;
                }
            });
            builder.setMessage((CharSequence)"networkconnection lost");
            AbstractNetworkContentFragment.this._networkLostInfo = builder.create();
            AbstractNetworkContentFragment.this._networkLostInfo.show();
        }
    }

}
