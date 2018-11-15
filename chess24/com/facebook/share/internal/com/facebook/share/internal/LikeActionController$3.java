/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.facebook.share.internal;

import android.content.Intent;
import com.facebook.internal.CallbackManagerImpl;

static final class LikeActionController
implements CallbackManagerImpl.Callback {
    LikeActionController() {
    }

    @Override
    public boolean onActivityResult(int n, Intent intent) {
        return com.facebook.share.internal.LikeActionController.handleOnActivityResult(CallbackManagerImpl.RequestCodeOffset.Like.toRequestCode(), n, intent);
    }
}
