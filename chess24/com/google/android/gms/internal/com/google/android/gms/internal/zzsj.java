/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zzt;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsd;
import com.google.android.gms.internal.zzsg;
import com.google.android.gms.internal.zzsq;
import com.google.android.gms.internal.zzsx;
import java.util.HashSet;
import java.util.Set;

public class zzsj {
    private final zzrw zzaam;
    private volatile Boolean zzadY;
    private String zzadZ;
    private Set<Integer> zzaea;

    protected zzsj(zzrw zzrw2) {
        zzac.zzw(zzrw2);
        this.zzaam = zzrw2;
    }

    public int zzoA() {
        return zzsq.zzaeH.get();
    }

    public int zzoB() {
        return zzsq.zzaeI.get();
    }

    public long zzoC() {
        return zzsq.zzaer.get();
    }

    public long zzoD() {
        return zzsq.zzaeq.get();
    }

    public long zzoE() {
        return zzsq.zzaeu.get();
    }

    public long zzoF() {
        return zzsq.zzaev.get();
    }

    public int zzoG() {
        return zzsq.zzaew.get();
    }

    public int zzoH() {
        return zzsq.zzaex.get();
    }

    public long zzoI() {
        return zzsq.zzaeK.get().intValue();
    }

    public String zzoJ() {
        return zzsq.zzaez.get();
    }

    public String zzoK() {
        return zzsq.zzaey.get();
    }

    public String zzoL() {
        return zzsq.zzaeA.get();
    }

    public String zzoM() {
        return zzsq.zzaeB.get();
    }

    public zzsd zzoN() {
        return zzsd.zzbX(zzsq.zzaeD.get());
    }

    public zzsg zzoO() {
        return zzsg.zzbY(zzsq.zzaeE.get());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Set<Integer> zzoP() {
        String string = zzsq.zzaeJ.get();
        if (this.zzaea != null && this.zzadZ != null) {
            if (this.zzadZ.equals(string)) return this.zzaea;
        }
        String[] arrstring = TextUtils.split((String)string, (String)",");
        HashSet<Integer> hashSet = new HashSet<Integer>();
        int n = arrstring.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.zzadZ = string;
                this.zzaea = hashSet;
                return this.zzaea;
            }
            String string2 = arrstring[n2];
            try {
                hashSet.add(Integer.parseInt(string2));
            }
            catch (NumberFormatException numberFormatException) {}
            ++n2;
        } while (true);
    }

    public long zzoQ() {
        return zzsq.zzaeS.get();
    }

    public long zzoR() {
        return zzsq.zzaeT.get();
    }

    public long zzoS() {
        return zzsq.zzaeW.get();
    }

    public int zzoT() {
        return zzsq.zzaen.get();
    }

    public int zzoU() {
        return zzsq.zzaep.get();
    }

    public String zzoV() {
        return "google_analytics_v4.db";
    }

    public int zzoW() {
        return zzsq.zzaeM.get();
    }

    public int zzoX() {
        return zzsq.zzaeN.get();
    }

    public long zzoY() {
        return zzsq.zzaeO.get();
    }

    public long zzoZ() {
        return zzsq.zzaeX.get();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean zzow() {
        if (this.zzadY == null) {
            synchronized (this) {
                if (this.zzadY == null) {
                    Object object = this.zzaam.getContext().getApplicationInfo();
                    String string = zzt.zzyK();
                    if (object != null) {
                        object = object.processName;
                        boolean bl = object != null && object.equals(string);
                        this.zzadY = bl;
                    }
                    if ((this.zzadY == null || !this.zzadY.booleanValue()) && "com.google.android.gms.analytics".equals(string)) {
                        this.zzadY = Boolean.TRUE;
                    }
                    if (this.zzadY == null) {
                        this.zzadY = Boolean.TRUE;
                        this.zzaam.zznr().zzbS("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzadY;
    }

    public boolean zzox() {
        return zzsq.zzaej.get();
    }

    public int zzoy() {
        return zzsq.zzaeC.get();
    }

    public int zzoz() {
        return zzsq.zzaeG.get();
    }
}
