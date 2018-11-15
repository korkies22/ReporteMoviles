/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

public abstract class DowngradeableSafeParcel
extends zza
implements ReflectedParcelable {
    private static final Object zzaEi = new Object();
    private static ClassLoader zzaEj;
    private static Integer zzaEk;
    private boolean zzaEl = false;

    protected static boolean zzdp(String string) {
        DowngradeableSafeParcel.zzxn();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected static ClassLoader zzxn() {
        Object object = zzaEi;
        // MONITORENTER : object
        // MONITOREXIT : object
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected static Integer zzxo() {
        Object object = zzaEi;
        // MONITORENTER : object
        // MONITOREXIT : object
        return null;
    }
}
