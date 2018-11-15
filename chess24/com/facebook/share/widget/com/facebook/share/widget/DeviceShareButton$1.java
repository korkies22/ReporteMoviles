/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.facebook.share.widget;

import android.view.View;
import com.facebook.share.DeviceShareDialog;
import com.facebook.share.model.ShareContent;

class DeviceShareButton
implements View.OnClickListener {
    DeviceShareButton() {
    }

    public void onClick(View view) {
        DeviceShareButton.this.callExternalOnClickListener(view);
        DeviceShareButton.this.getDialog().show(DeviceShareButton.this.getShareContent());
    }
}
