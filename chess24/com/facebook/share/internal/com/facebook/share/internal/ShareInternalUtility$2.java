/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.facebook.share.internal;

import android.content.Intent;
import com.facebook.internal.CallbackManagerImpl;

static final class ShareInternalUtility
implements CallbackManagerImpl.Callback {
    final /* synthetic */ int val$requestCode;

    ShareInternalUtility(int n) {
        this.val$requestCode = n;
    }

    @Override
    public boolean onActivityResult(int n, Intent intent) {
        return com.facebook.share.internal.ShareInternalUtility.handleActivityResult(this.val$requestCode, n, intent, com.facebook.share.internal.ShareInternalUtility.getShareResultProcessor(null));
    }
}
