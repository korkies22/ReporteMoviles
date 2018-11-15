/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.zzacw;

public class zzacx {
    private static zzacx zzaHl = new zzacx();
    private zzacw zzaHk = null;

    public static zzacw zzaQ(Context context) {
        return zzaHl.zzaP(context);
    }

    public zzacw zzaP(Context object) {
        synchronized (this) {
            if (this.zzaHk == null) {
                if (object.getApplicationContext() != null) {
                    object = object.getApplicationContext();
                }
                this.zzaHk = new zzacw((Context)object);
            }
            object = this.zzaHk;
            return object;
        }
    }
}
