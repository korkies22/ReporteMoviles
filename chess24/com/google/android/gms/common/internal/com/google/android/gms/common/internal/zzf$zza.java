/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.BinderThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzf;

private abstract class zzf.zza
extends zzf.zze<Boolean> {
    public final int statusCode;
    public final Bundle zzaDU;

    @BinderThread
    protected zzf.zza(int n, Bundle bundle) {
        super(zzf.this, true);
        this.statusCode = n;
        this.zzaDU = bundle;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void zzc(Boolean object) {
        Object var3_2 = null;
        if (object == null) {
            zzf.this.zza(1, (T)null);
            return;
        }
        int n = this.statusCode;
        if (n != 0) {
            if (n == 10) {
                zzf.this.zza(1, (T)null);
                throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
            }
            zzf.this.zza(1, (T)null);
            object = var3_2;
            if (this.zzaDU != null) {
                object = (PendingIntent)this.zzaDU.getParcelable("pendingIntent");
            }
            object = new ConnectionResult(this.statusCode, (PendingIntent)object);
        } else {
            if (this.zzwZ()) {
                return;
            }
            zzf.this.zza(1, (T)null);
            object = new ConnectionResult(8, null);
        }
        this.zzn((ConnectionResult)object);
    }

    protected abstract void zzn(ConnectionResult var1);

    @Override
    protected /* synthetic */ void zzu(Object object) {
        this.zzc((Boolean)object);
    }

    protected abstract boolean zzwZ();
}
