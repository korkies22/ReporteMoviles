/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.Intent
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.internal.zzaar;
import com.google.android.gms.internal.zzaax;
import com.google.android.gms.internal.zzzw;

private class zzzw.zza
implements Runnable {
    private zzzw.zza() {
    }

    @MainThread
    @Override
    public void run() {
        if (!zzzw.this.mStarted) {
            return;
        }
        if (zzzw.this.zzayH.hasResolution()) {
            zzzw.this.zzaBs.startActivityForResult(GoogleApiActivity.zzb((Context)zzzw.this.getActivity(), zzzw.this.zzayH.getResolution(), zzzw.this.zzayI, false), 1);
            return;
        }
        if (zzzw.this.zzaxX.isUserResolvableError(zzzw.this.zzayH.getErrorCode())) {
            zzzw.this.zzaxX.zza(zzzw.this.getActivity(), zzzw.this.zzaBs, zzzw.this.zzayH.getErrorCode(), 2, zzzw.this);
            return;
        }
        if (zzzw.this.zzayH.getErrorCode() == 18) {
            final Dialog dialog = zzzw.this.zzaxX.zza(zzzw.this.getActivity(), zzzw.this);
            zzzw.this.zzaxX.zza(zzzw.this.getActivity().getApplicationContext(), new zzaar.zza(){

                @Override
                public void zzvb() {
                    zzzw.this.zzva();
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });
            return;
        }
        zzzw.this.zza(zzzw.this.zzayH, zzzw.this.zzayI);
    }

}
