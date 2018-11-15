/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.IBinder
 */
package com.google.android.gms.common.api;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzr;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Set;

public static interface Api.zze
extends Api.zzb {
    public void disconnect();

    public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

    public boolean isConnected();

    public boolean isConnecting();

    public void zza(zzf.zzf var1);

    public void zza(zzr var1, Set<Scope> var2);

    public boolean zzqD();

    public boolean zzqS();

    public Intent zzqT();

    public boolean zzuI();

    @Nullable
    public IBinder zzuJ();
}
