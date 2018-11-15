/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 */
package com.facebook.share.internal;

import android.app.Dialog;

class DeviceShareDialogFragment
implements Runnable {
    DeviceShareDialogFragment() {
    }

    @Override
    public void run() {
        DeviceShareDialogFragment.this.dialog.dismiss();
    }
}
