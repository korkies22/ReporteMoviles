/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.util.Log
 */
package com.google.android.gms.common.api;

import android.app.Activity;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;

public abstract class ResolvingResultCallbacks<R extends Result>
extends ResultCallbacks<R> {
    private final Activity mActivity;
    private final int zzaye;

    protected ResolvingResultCallbacks(@NonNull Activity activity, int n) {
        this.mActivity = zzac.zzb(activity, (Object)"Activity must not be null");
        this.zzaye = n;
    }

    @Override
    public final void onFailure(@NonNull Status status) {
        Status status2 = status;
        if (status.hasResolution()) {
            try {
                status.startResolutionForResult(this.mActivity, this.zzaye);
                return;
            }
            catch (IntentSender.SendIntentException sendIntentException) {
                Log.e((String)"ResolvingResultCallback", (String)"Failed to start resolution", (Throwable)sendIntentException);
                status2 = new Status(8);
            }
        }
        this.onUnresolvableFailure(status2);
    }

    @Override
    public abstract void onSuccess(@NonNull R var1);

    public abstract void onUnresolvableFailure(@NonNull Status var1);
}
