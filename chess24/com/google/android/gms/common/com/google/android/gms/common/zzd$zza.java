/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.util.zzm;
import com.google.android.gms.common.zzd;
import com.google.android.gms.dynamic.zze;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

static abstract class zzd.zza
extends zzt.zza {
    private int zzaxg;

    protected zzd.zza(byte[] object) {
        Object object2 = object;
        if (((byte[])object).length != 25) {
            boolean bl = false;
            int n = ((byte[])object).length;
            object2 = String.valueOf(zzm.zza((byte[])object, 0, ((byte[])object).length, false));
            StringBuilder stringBuilder = new StringBuilder(51 + String.valueOf(object2).length());
            stringBuilder.append("Cert hash data has incorrect length (");
            stringBuilder.append(n);
            stringBuilder.append("):\n");
            stringBuilder.append((String)object2);
            Log.wtf((String)"GoogleCertificates", (String)stringBuilder.toString(), (Throwable)new Exception());
            object2 = Arrays.copyOfRange((byte[])object, 0, 25);
            if (((byte[])object2).length == 25) {
                bl = true;
            }
            n = ((byte[])object2).length;
            object = new StringBuilder(55);
            object.append("cert hash data has incorrect length. length=");
            object.append(n);
            zzac.zzb(bl, (Object)object.toString());
        }
        this.zzaxg = Arrays.hashCode(object2);
    }

    protected static byte[] zzdb(String arrby) {
        try {
            arrby = arrby.getBytes("ISO-8859-1");
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError(unsupportedEncodingException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof zzt)) {
            return false;
        }
        try {
            object = (zzt)object;
            if (object.zzuC() != this.hashCode()) {
                return false;
            }
            if ((object = object.zzuB()) == null) {
                return false;
            }
            object = (byte[])zze.zzE((com.google.android.gms.dynamic.zzd)object);
            return Arrays.equals(this.getBytes(), (byte[])object);
        }
        catch (RemoteException remoteException) {}
        Log.e((String)"GoogleCertificates", (String)"iCertData failed to retrive data from remote");
        return false;
    }

    abstract byte[] getBytes();

    public int hashCode() {
        return this.zzaxg;
    }

    @Override
    public com.google.android.gms.dynamic.zzd zzuB() {
        return zze.zzA(this.getBytes());
    }

    @Override
    public int zzuC() {
        return this.hashCode();
    }
}
