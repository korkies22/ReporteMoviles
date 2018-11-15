/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.internal.zzabi;
import com.google.android.gms.internal.zzzv;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public interface zzaau {
    public ConnectionResult blockingConnect();

    public ConnectionResult blockingConnect(long var1, TimeUnit var3);

    public void connect();

    public void disconnect();

    public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

    @Nullable
    public ConnectionResult getConnectionResult(@NonNull Api<?> var1);

    public boolean isConnected();

    public boolean isConnecting();

    public <A extends Api.zzb, R extends Result, T extends zzzv.zza<R, A>> T zza(@NonNull T var1);

    public boolean zza(zzabi var1);

    public <A extends Api.zzb, T extends zzzv.zza<? extends Result, A>> T zzb(@NonNull T var1);

    public void zzuN();

    public void zzvj();

    public static interface zza {
        public void zzc(int var1, boolean var2);

        public void zzc(ConnectionResult var1);

        public void zzo(Bundle var1);
    }

}
