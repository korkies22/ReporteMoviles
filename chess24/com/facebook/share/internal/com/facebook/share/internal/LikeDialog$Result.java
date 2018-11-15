/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.share.internal.LikeDialog;

@Deprecated
public static final class LikeDialog.Result {
    private final Bundle bundle;

    public LikeDialog.Result(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getData() {
        return this.bundle;
    }
}
