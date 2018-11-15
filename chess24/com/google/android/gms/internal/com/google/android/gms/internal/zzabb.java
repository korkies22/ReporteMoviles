/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.internal.zzaaw;
import com.google.android.gms.internal.zzaax;
import com.google.android.gms.internal.zzzw;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.CancellationException;

public class zzabb
extends zzzw {
    private TaskCompletionSource<Void> zzayo = new TaskCompletionSource();

    private zzabb(zzaax zzaax2) {
        super(zzaax2);
        this.zzaBs.zza("GmsAvailabilityHelper", this);
    }

    public static zzabb zzu(Activity object) {
        zzabb zzabb2 = (object = zzabb.zzs((Activity)object)).zza("GmsAvailabilityHelper", zzabb.class);
        if (zzabb2 != null) {
            if (zzabb2.zzayo.getTask().isComplete()) {
                zzabb2.zzayo = new TaskCompletionSource();
            }
            return zzabb2;
        }
        return new zzabb((zzaax)object);
    }

    public Task<Void> getTask() {
        return this.zzayo.getTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.zzayo.setException(new CancellationException("Host activity was destroyed before Google Play services could be made available."));
    }

    @Override
    protected void zza(ConnectionResult connectionResult, int n) {
        this.zzayo.setException(zzb.zzl(connectionResult));
    }

    public void zzk(ConnectionResult connectionResult) {
        this.zzb(connectionResult, 0);
    }

    @Override
    protected void zzuW() {
        int n = this.zzaxX.isGooglePlayServicesAvailable((Context)this.zzaBs.zzwo());
        if (n == 0) {
            this.zzayo.setResult(null);
            return;
        }
        this.zzk(new ConnectionResult(n, null));
    }
}
