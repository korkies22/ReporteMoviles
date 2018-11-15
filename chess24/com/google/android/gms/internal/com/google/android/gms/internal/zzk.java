/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.SystemClock
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import com.google.android.gms.internal.zzb;
import com.google.android.gms.internal.zzd;
import com.google.android.gms.internal.zzi;
import com.google.android.gms.internal.zzl;
import com.google.android.gms.internal.zzm;
import com.google.android.gms.internal.zzo;
import com.google.android.gms.internal.zzr;
import com.google.android.gms.internal.zzs;
import java.util.Collections;
import java.util.Map;

public abstract class zzk<T>
implements Comparable<zzk<T>> {
    private final zzs.zza zzB;
    private final int zzC;
    private final String zzD;
    private final int zzE;
    private final zzm.zza zzF;
    private Integer zzG;
    private zzl zzH;
    private boolean zzI;
    private boolean zzJ;
    private boolean zzK;
    private long zzL;
    private zzo zzM;
    private zzb.zza zzN;

    public zzk(int n, String string, zzm.zza zza2) {
        zzs.zza zza3 = zzs.zza.zzai ? new zzs.zza() : null;
        this.zzB = zza3;
        this.zzI = true;
        this.zzJ = false;
        this.zzK = false;
        this.zzL = 0L;
        this.zzN = null;
        this.zzC = n;
        this.zzD = string;
        this.zzF = zza2;
        this.zza(new zzd());
        this.zzE = zzk.zzb(string);
    }

    private static int zzb(String string) {
        if (!TextUtils.isEmpty((CharSequence)string) && (string = Uri.parse((String)string)) != null && (string = string.getHost()) != null) {
            return string.hashCode();
        }
        return 0;
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.zzc((zzk)object);
    }

    public Map<String, String> getHeaders() throws com.google.android.gms.internal.zza {
        return Collections.emptyMap();
    }

    public int getMethod() {
        return this.zzC;
    }

    public String getUrl() {
        return this.zzD;
    }

    public String toString() {
        String string = String.valueOf(Integer.toHexString(this.zzf()));
        string = string.length() != 0 ? "0x".concat(string) : new String("0x");
        String string2 = String.valueOf(this.getUrl());
        String string3 = String.valueOf((Object)this.zzo());
        String string4 = String.valueOf(this.zzG);
        StringBuilder stringBuilder = new StringBuilder(3 + String.valueOf("[ ] ").length() + String.valueOf(string2).length() + String.valueOf(string).length() + String.valueOf(string3).length() + String.valueOf(string4).length());
        stringBuilder.append("[ ] ");
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(string);
        stringBuilder.append(" ");
        stringBuilder.append(string3);
        stringBuilder.append(" ");
        stringBuilder.append(string4);
        return stringBuilder.toString();
    }

    public final zzk<?> zza(int n) {
        this.zzG = n;
        return this;
    }

    public zzk<?> zza(zzb.zza zza2) {
        this.zzN = zza2;
        return this;
    }

    public zzk<?> zza(zzl zzl2) {
        this.zzH = zzl2;
        return this;
    }

    public zzk<?> zza(zzo zzo2) {
        this.zzM = zzo2;
        return this;
    }

    protected abstract zzm<T> zza(zzi var1);

    protected abstract void zza(T var1);

    protected zzr zzb(zzr zzr2) {
        return zzr2;
    }

    public int zzc(zzk<T> zzk2) {
        zza zza2;
        zza zza3 = this.zzo();
        if (zza3 == (zza2 = zzk2.zzo())) {
            return this.zzG - zzk2.zzG;
        }
        return zza2.ordinal() - zza3.ordinal();
    }

    public void zzc(zzr zzr2) {
        if (this.zzF != null) {
            this.zzF.zze(zzr2);
        }
    }

    public void zzc(String string) {
        if (zzs.zza.zzai) {
            this.zzB.zza(string, Thread.currentThread().getId());
            return;
        }
        if (this.zzL == 0L) {
            this.zzL = SystemClock.elapsedRealtime();
        }
    }

    void zzd(final String string) {
        if (this.zzH != null) {
            this.zzH.zzf(this);
        }
        if (zzs.zza.zzai) {
            final long l = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new Runnable(){

                    @Override
                    public void run() {
                        zzk.this.zzB.zza(string, l);
                        zzk.this.zzB.zzd(this.toString());
                    }
                });
                return;
            }
            this.zzB.zza(string, l);
            this.zzB.zzd(this.toString());
            return;
        }
        long l = SystemClock.elapsedRealtime() - this.zzL;
        if (l >= 3000L) {
            zzs.zzb("%d ms: %s", l, this.toString());
        }
    }

    public int zzf() {
        return this.zzE;
    }

    public String zzg() {
        return this.getUrl();
    }

    public zzb.zza zzh() {
        return this.zzN;
    }

    @Deprecated
    public String zzi() {
        return this.zzl();
    }

    @Deprecated
    public byte[] zzj() throws com.google.android.gms.internal.zza {
        return null;
    }

    protected String zzk() {
        return "UTF-8";
    }

    public String zzl() {
        String string = String.valueOf(this.zzk());
        if (string.length() != 0) {
            return "application/x-www-form-urlencoded; charset=".concat(string);
        }
        return new String("application/x-www-form-urlencoded; charset=");
    }

    public byte[] zzm() throws com.google.android.gms.internal.zza {
        return null;
    }

    public final boolean zzn() {
        return this.zzI;
    }

    public zza zzo() {
        return zza.zzS;
    }

    public final int zzp() {
        return this.zzM.zzc();
    }

    public zzo zzq() {
        return this.zzM;
    }

    public void zzr() {
        this.zzK = true;
    }

    public boolean zzs() {
        return this.zzK;
    }

    public static final class zza
    extends Enum<zza> {
        public static final /* enum */ zza zzR = new zza();
        public static final /* enum */ zza zzS = new zza();
        public static final /* enum */ zza zzT = new zza();
        public static final /* enum */ zza zzU = new zza();
        private static final /* synthetic */ zza[] zzV;

        static {
            zzV = new zza[]{zzR, zzS, zzT, zzU};
        }

        private zza() {
        }

        public static zza[] values() {
            return (zza[])zzV.clone();
        }
    }

}
