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
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.internal.zzaar;
import com.google.android.gms.internal.zzaaw;
import com.google.android.gms.internal.zzaax;

public abstract class zzzw
extends zzaaw
implements DialogInterface.OnCancelListener {
    protected boolean mStarted;
    protected final GoogleApiAvailability zzaxX;
    protected boolean zzayG;
    private ConnectionResult zzayH;
    private int zzayI = -1;
    private final Handler zzayJ = new Handler(Looper.getMainLooper());

    protected zzzw(zzaax zzaax2) {
        this(zzaax2, GoogleApiAvailability.getInstance());
    }

    zzzw(zzaax zzaax2, GoogleApiAvailability googleApiAvailability) {
        super(zzaax2);
        this.zzaxX = googleApiAvailability;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void onActivityResult(int var1_1, int var2_2, Intent var3_3) {
        var5_4 = 1;
        var4_5 = 1;
        switch (var1_1) {
            default: {
                ** break;
            }
            case 2: {
                var5_4 = this.zzaxX.isGooglePlayServicesAvailable((Context)this.getActivity());
                var1_1 = var5_4 == 0 ? var4_5 : 0;
                var2_2 = var1_1;
                if (this.zzayH.getErrorCode() != 18) break;
                var2_2 = var1_1;
                if (var5_4 != 18) break;
                return;
            }
            case 1: {
                if (var2_2 == -1) {
                    var2_2 = var5_4;
                    break;
                }
                if (var2_2 == 0) {
                    var1_1 = 13;
                    if (var3_3 != null) {
                        var1_1 = var3_3.getIntExtra("<<ResolutionFailureErrorDetail>>", 13);
                    }
                    this.zzayH = new ConnectionResult(var1_1, null);
                }
lbl23: // 4 sources:
                var2_2 = 0;
            }
        }
        if (var2_2 != 0) {
            this.zzva();
            return;
        }
        this.zza(this.zzayH, this.zzayI);
    }

    public void onCancel(DialogInterface dialogInterface) {
        this.zza(new ConnectionResult(13, null), this.zzayI);
        this.zzva();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.zzayG = bundle.getBoolean("resolving_error", false);
            if (this.zzayG) {
                this.zzayI = bundle.getInt("failed_client_id", -1);
                this.zzayH = new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent)bundle.getParcelable("failed_resolution"));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("resolving_error", this.zzayG);
        if (this.zzayG) {
            bundle.putInt("failed_client_id", this.zzayI);
            bundle.putInt("failed_status", this.zzayH.getErrorCode());
            bundle.putParcelable("failed_resolution", (Parcelable)this.zzayH.getResolution());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mStarted = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mStarted = false;
    }

    protected abstract void zza(ConnectionResult var1, int var2);

    public void zzb(ConnectionResult connectionResult, int n) {
        if (!this.zzayG) {
            this.zzayG = true;
            this.zzayI = n;
            this.zzayH = connectionResult;
            this.zzayJ.post((Runnable)new zza());
        }
    }

    protected abstract void zzuW();

    protected void zzva() {
        this.zzayI = -1;
        this.zzayG = false;
        this.zzayH = null;
        this.zzuW();
    }

    private class zza
    implements Runnable {
        private zza() {
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

}
