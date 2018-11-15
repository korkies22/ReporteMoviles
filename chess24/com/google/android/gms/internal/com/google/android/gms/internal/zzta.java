/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.text.TextUtils
 *  android.util.Pair
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zztd;
import java.util.UUID;

public class zzta
extends zzru {
    private SharedPreferences zzafC;
    private long zzafD;
    private long zzafE = -1L;
    private final zza zzafF = new zza("monitoring", this.zzns().zzoZ());

    protected zzta(zzrw zzrw2) {
        super(zzrw2);
    }

    public void zzca(String string) {
        this.zzmq();
        this.zznA();
        SharedPreferences.Editor editor = this.zzafC.edit();
        if (TextUtils.isEmpty((CharSequence)string)) {
            editor.remove("installation_campaign");
        } else {
            editor.putString("installation_campaign", string);
        }
        if (!editor.commit()) {
            this.zzbR("Failed to commit campaign data");
        }
    }

    @Override
    protected void zzmr() {
        this.zzafC = this.getContext().getSharedPreferences("com.google.android.gms.analytics.prefs", 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public long zzpE() {
        this.zzmq();
        this.zznA();
        if (this.zzafD != 0L) return this.zzafD;
        long l = this.zzafC.getLong("first_run", 0L);
        if (l == 0L) {
            long l2 = this.zznq().currentTimeMillis();
            SharedPreferences.Editor editor = this.zzafC.edit();
            editor.putLong("first_run", l2);
            l = l2;
            if (!editor.commit()) {
                this.zzbR("Failed to commit first run time");
                l = l2;
            }
        }
        this.zzafD = l;
        return this.zzafD;
    }

    public zztd zzpF() {
        return new zztd(this.zznq(), this.zzpE());
    }

    public long zzpG() {
        this.zzmq();
        this.zznA();
        if (this.zzafE == -1L) {
            this.zzafE = this.zzafC.getLong("last_dispatch", 0L);
        }
        return this.zzafE;
    }

    public void zzpH() {
        this.zzmq();
        this.zznA();
        long l = this.zznq().currentTimeMillis();
        SharedPreferences.Editor editor = this.zzafC.edit();
        editor.putLong("last_dispatch", l);
        editor.apply();
        this.zzafE = l;
    }

    public String zzpI() {
        this.zzmq();
        this.zznA();
        String string = this.zzafC.getString("installation_campaign", null);
        if (TextUtils.isEmpty((CharSequence)string)) {
            return null;
        }
        return string;
    }

    public zza zzpJ() {
        return this.zzafF;
    }

    public final class zza {
        private final String mName;
        private final long zzafG;

        private zza(String string, long l) {
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

}
