/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.facebook.login;

import android.view.View;

class DeviceAuthDialog
implements View.OnClickListener {
    DeviceAuthDialog() {
    }

    public void onClick(View view) {
        DeviceAuthDialog.this.onCancel();
    }
}
