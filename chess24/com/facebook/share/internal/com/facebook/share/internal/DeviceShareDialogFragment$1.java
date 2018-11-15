/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.facebook.share.internal;

import android.app.Dialog;
import android.view.View;

class DeviceShareDialogFragment
implements View.OnClickListener {
    DeviceShareDialogFragment() {
    }

    public void onClick(View view) {
        DeviceShareDialogFragment.this.dialog.dismiss();
    }
}
