/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.share.widget;

import android.os.Bundle;
import com.facebook.share.widget.JoinAppGroupDialog;

@Deprecated
public static final class JoinAppGroupDialog.Result {
    private final Bundle data;

    private JoinAppGroupDialog.Result(Bundle bundle) {
        this.data = bundle;
    }

    public Bundle getData() {
        return this.data;
    }
}
