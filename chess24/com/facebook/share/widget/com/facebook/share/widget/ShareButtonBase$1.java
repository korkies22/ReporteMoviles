/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.facebook.share.widget;

import android.view.View;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;

class ShareButtonBase
implements View.OnClickListener {
    ShareButtonBase() {
    }

    public void onClick(View view) {
        ShareButtonBase.this.callExternalOnClickListener(view);
        ShareButtonBase.this.getDialog().show(ShareButtonBase.this.getShareContent());
    }
}
