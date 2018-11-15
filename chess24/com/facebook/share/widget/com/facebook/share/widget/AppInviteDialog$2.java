/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.facebook.share.widget;

import android.content.Intent;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;

class AppInviteDialog
implements CallbackManagerImpl.Callback {
    final /* synthetic */ ResultProcessor val$resultProcessor;

    AppInviteDialog(ResultProcessor resultProcessor) {
        this.val$resultProcessor = resultProcessor;
    }

    @Override
    public boolean onActivityResult(int n, Intent intent) {
        return ShareInternalUtility.handleActivityResult(AppInviteDialog.this.getRequestCode(), n, intent, this.val$resultProcessor);
    }
}
