/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Intent
 */
package com.google.android.gms.common.internal;

import android.annotation.TargetApi;
import android.content.Intent;
import com.google.android.gms.internal.zzaax;

final class zzi
extends com.google.android.gms.common.internal.zzi {
    final /* synthetic */ int val$requestCode;
    final /* synthetic */ zzaax zzaEh;

    zzi(zzaax zzaax2, int n) {
        this.zzaEh = zzaax2;
        this.val$requestCode = n;
    }

    @TargetApi(value=11)
    @Override
    public void zzxm() {
        if (Intent.this != null) {
            this.zzaEh.startActivityForResult(Intent.this, this.val$requestCode);
        }
    }
}
