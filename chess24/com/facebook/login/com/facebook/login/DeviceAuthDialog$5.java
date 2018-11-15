/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.view.View
 */
package com.facebook.login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import com.facebook.login.LoginClient;

class DeviceAuthDialog
implements DialogInterface.OnClickListener {
    DeviceAuthDialog() {
    }

    public void onClick(DialogInterface dialogInterface, int n) {
        dialogInterface = DeviceAuthDialog.this.initializeContentView(false);
        DeviceAuthDialog.this.dialog.setContentView((View)dialogInterface);
        DeviceAuthDialog.this.startLogin(DeviceAuthDialog.this.mRequest);
    }
}
