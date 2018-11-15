/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.util.Pair
 */
package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.util.Pair;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.zzta;
import java.util.UUID;

public final class zzta.zza {
    private final String mName;
    private final long zzafG;

    private zzta.zza(String string, long l) {
        zzac.zzdv(string);
        boolean bl = l > 0L;
        zzac.zzas(bl);
        this.mName = string;
        this.zzafG = l;
    }

    private void zzpK() {
        long l = zzta.this.zznq().currentTimeMillis();
        SharedPreferences.Editor editor = zzta.this.zzafC.edit();
        editor.remove(this.zzpP());
        editor.remove(this.zzpQ());
        editor.putLong(this.zzpO(), l);
        editor.commit();
    }

    private long zzpL() {
        long l = this.zzpN();
        if (l == 0L) {
            return 0L;
        }
        return Math.abs(l - zzta.this.zznq().currentTimeMillis());
    }

    private long zzpN() {
        return zzta.this.zzafC.getLong(this.zzpO(), 0L);
    }

    private String zzpO() {
        return String.valueOf(this.mName).concat(":start");
    }

    private String zzpP() {
        return String.valueOf(this.mName).concat(":count");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzcb(String string) {
        if (this.zzpN() == 0L) {
            this.zzpK();
        }
        String string2 = string;
        if (string == null) {
            string2 = "";
        }
        synchronized (this) {
            long l = zzta.this.zzafC.getLong(this.zzpP(), 0L);
            if (l <= 0L) {
                string = zzta.this.zzafC.edit();
                string.putString(this.zzpQ(), string2);
                string.putLong(this.zzpP(), 1L);
                string.apply();
                return;
            }
            long l2 = UUID.randomUUID().getLeastSignificantBits();
            boolean bl = (l2 & Long.MAX_VALUE) < Long.MAX_VALUE / ++l;
            string = zzta.this.zzafC.edit();
            if (bl) {
                string.putString(this.zzpQ(), string2);
            }
            string.putLong(this.zzpP(), l);
            string.apply();
            return;
        }
    }

    public Pair<String, Long> zzpM() {
        long l = this.zzpL();
        if (l < this.zzafG) {
            return null;
        }
        if (l > this.zzafG * 2L) {
            this.zzpK();
            return null;
        }
        String string = zzta.this.zzafC.getString(this.zzpQ(), null);
        l = zzta.this.zzafC.getLong(this.zzpP(), 0L);
        this.zzpK();
        if (string != null) {
            if (l <= 0L) {
                return null;
            }
            return new Pair((Object)string, (Object)l);
        }
        return null;
    }

    protected String zzpQ() {
        return String.valueOf(this.mName).concat(":value");
    }
}
