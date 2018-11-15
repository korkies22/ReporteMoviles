/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.util.Log
 */
package com.facebook.share.internal;

import android.os.Bundle;
import android.util.Log;
import com.facebook.internal.DialogPresenter;
import com.facebook.share.internal.LikeContent;
import com.facebook.share.internal.LikeDialog;

class LikeDialog.NativeHandler
implements DialogPresenter.ParameterProvider {
    final /* synthetic */ LikeContent val$content;

    LikeDialog.NativeHandler(LikeContent likeContent) {
        this.val$content = likeContent;
    }

    @Override
    public Bundle getLegacyParameters() {
        Log.e((String)LikeDialog.TAG, (String)"Attempting to present the Like Dialog with an outdated Facebook app on the device");
        return new Bundle();
    }

    @Override
    public Bundle getParameters() {
        return LikeDialog.createParameters(this.val$content);
    }
}
