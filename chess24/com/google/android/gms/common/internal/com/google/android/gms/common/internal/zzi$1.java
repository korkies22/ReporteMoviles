/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 */
package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.Intent;

final class zzi
extends com.google.android.gms.common.internal.zzi {
    final /* synthetic */ Activity val$activity;
    final /* synthetic */ int val$requestCode;

    zzi(Activity activity, int n) {
        this.val$activity = activity;
        this.val$requestCode = n;
    }

    @Override
    public void zzxm() {
        if (Intent.this != null) {
            this.val$activity.startActivityForResult(Intent.this, this.val$requestCode);
        }
    }
}
