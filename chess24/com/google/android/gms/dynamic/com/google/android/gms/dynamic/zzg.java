/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 */
package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.zze;

public abstract class zzg<T> {
    private final String zzaQo;
    private T zzaQp;

    protected zzg(String string) {
        this.zzaQo = string;
    }

    protected final T zzaT(Context object) throws zza {
        if (this.zzaQp == null) {
            zzac.zzw(object);
            object = zze.getRemoteContext((Context)object);
            if (object == null) {
                throw new zza("Could not get remote context.");
            }
            object = object.getClassLoader();
            try {
                this.zzaQp = this.zzc((IBinder)object.loadClass(this.zzaQo).newInstance());
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new zza("Could not access creator.", illegalAccessException);
            }
            catch (InstantiationException instantiationException) {
                throw new zza("Could not instantiate creator.", instantiationException);
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new zza("Could not load creator class.", classNotFoundException);
            }
        }
        return this.zzaQp;
    }

    protected abstract T zzc(IBinder var1);

    public static class zza
    extends Exception {
        public zza(String string) {
            super(string);
        }

        public zza(String string, Throwable throwable) {
            super(string, throwable);
        }
    }

}
