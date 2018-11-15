/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 */
package com.google.android.gms.internal;

import android.app.Dialog;
import com.google.android.gms.internal.zzaar;
import com.google.android.gms.internal.zzzw;

class zzzw.zza
extends zzaar.zza {
    final /* synthetic */ Dialog zzayL;

    zzzw.zza(Dialog dialog) {
        this.zzayL = dialog;
    }

    @Override
    public void zzvb() {
        zza.this.zzayK.zzva();
        if (this.zzayL.isShowing()) {
            this.zzayL.dismiss();
        }
    }
}
