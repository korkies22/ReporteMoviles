/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public abstract class ResultCallbacks<R extends Result>
implements ResultCallback<R> {
    public abstract void onFailure(@NonNull Status var1);

    @Override
    public final void onResult(@NonNull R object) {
        Status status = object.getStatus();
        if (status.isSuccess()) {
            this.onSuccess(object);
            return;
        }
        this.onFailure(status);
        if (object instanceof Releasable) {
            try {
                ((Releasable)object).release();
                return;
            }
            catch (RuntimeException runtimeException) {
                object = String.valueOf(object);
                StringBuilder stringBuilder = new StringBuilder(18 + String.valueOf(object).length());
                stringBuilder.append("Unable to release ");
                stringBuilder.append((String)object);
                Log.w((String)"ResultCallbacks", (String)stringBuilder.toString(), (Throwable)runtimeException);
            }
        }
    }

    public abstract void onSuccess(@NonNull R var1);
}
