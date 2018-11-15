/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.common.internal;

import android.content.Intent;
import android.support.v4.app.Fragment;

final class zzi
extends com.google.android.gms.common.internal.zzi {
    final /* synthetic */ Fragment val$fragment;
    final /* synthetic */ int val$requestCode;

    zzi(Fragment fragment, int n) {
        this.val$fragment = fragment;
        this.val$requestCode = n;
    }

    @Override
    public void zzxm() {
        if (Intent.this != null) {
            this.val$fragment.startActivityForResult(Intent.this, this.val$requestCode);
        }
    }
}
