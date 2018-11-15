/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.TaskCompletionSource;

public abstract class zzabn<A extends Api.zzb, TResult> {
    protected abstract void zza(A var1, TaskCompletionSource<TResult> var2) throws RemoteException;
}
