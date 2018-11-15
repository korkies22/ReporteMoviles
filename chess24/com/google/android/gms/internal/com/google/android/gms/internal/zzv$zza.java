/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzb;
import com.google.android.gms.internal.zzs;
import com.google.android.gms.internal.zzv;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

static class zzv.zza {
    public String zza;
    public String zzaA;
    public long zzaz;
    public long zzb;
    public long zzc;
    public long zzd;
    public long zze;
    public Map<String, String> zzf;

    private zzv.zza() {
    }

    public zzv.zza(String string, zzb.zza zza2) {
        this.zzaA = string;
        this.zzaz = zza2.data.length;
        this.zza = zza2.zza;
        this.zzb = zza2.zzb;
        this.zzc = zza2.zzc;
        this.zzd = zza2.zzd;
        this.zze = zza2.zze;
        this.zzf = zza2.zzf;
    }

    public static zzv.zza zzf(InputStream inputStream) throws IOException {
        zzv.zza zza2 = new zzv.zza();
        if (zzv.zzb(inputStream) != 538247942) {
            throw new IOException();
        }
        zza2.zzaA = zzv.zzd(inputStream);
        zza2.zza = zzv.zzd(inputStream);
        if (zza2.zza.equals("")) {
            zza2.zza = null;
        }
        zza2.zzb = zzv.zzc(inputStream);
        zza2.zzc = zzv.zzc(inputStream);
        zza2.zzd = zzv.zzc(inputStream);
        zza2.zze = zzv.zzc(inputStream);
        zza2.zzf = zzv.zze(inputStream);
        return zza2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean zza(OutputStream outputStream) {
        try {
            zzv.zza(outputStream, 538247942);
            zzv.zza(outputStream, this.zzaA);
            String string = this.zza == null ? "" : this.zza;
            zzv.zza(outputStream, string);
            zzv.zza(outputStream, this.zzb);
            zzv.zza(outputStream, this.zzc);
            zzv.zza(outputStream, this.zzd);
            zzv.zza(outputStream, this.zze);
            zzv.zza(this.zzf, outputStream);
            outputStream.flush();
            return true;
        }
        catch (IOException iOException) {
            zzs.zzb("%s", iOException.toString());
            return false;
        }
    }

    public zzb.zza zzb(byte[] arrby) {
        zzb.zza zza2 = new zzb.zza();
        zza2.data = arrby;
        zza2.zza = this.zza;
        zza2.zzb = this.zzb;
        zza2.zzc = this.zzc;
        zza2.zzd = this.zzd;
        zza2.zze = this.zze;
        zza2.zzf = this.zzf;
        return zza2;
    }
}
