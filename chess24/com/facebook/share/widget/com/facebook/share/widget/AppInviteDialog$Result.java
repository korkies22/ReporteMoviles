/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.share.widget;

import android.os.Bundle;
import com.facebook.share.widget.AppInviteDialog;

@Deprecated
public static final class AppInviteDialog.Result {
    private final Bundle bundle;

    public AppInviteDialog.Result(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getData() {
        return this.bundle;
    }
}
