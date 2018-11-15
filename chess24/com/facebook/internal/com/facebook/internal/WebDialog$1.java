/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 */
package com.facebook.internal;

import android.content.DialogInterface;

class WebDialog
implements DialogInterface.OnCancelListener {
    WebDialog() {
    }

    public void onCancel(DialogInterface dialogInterface) {
        WebDialog.this.cancel();
    }
}
