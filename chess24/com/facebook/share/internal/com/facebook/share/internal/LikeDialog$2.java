/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.facebook.share.internal;

import android.content.Intent;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;

class LikeDialog
implements CallbackManagerImpl.Callback {
    final /* synthetic */ ResultProcessor val$resultProcessor;

    LikeDialog(ResultProcessor resultProcessor) {
        this.val$resultProcessor = resultProcessor;
    }

    @Override
    public boolean onActivityResult(int n, Intent intent) {
        return ShareInternalUtility.handleActivityResult(LikeDialog.this.getRequestCode(), n, intent, this.val$resultProcessor);
    }
}
