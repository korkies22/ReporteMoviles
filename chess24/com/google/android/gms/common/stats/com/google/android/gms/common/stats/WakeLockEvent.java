/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.stats.StatsEvent;
import com.google.android.gms.common.stats.zzf;
import java.util.List;

public final class WakeLockEvent
extends StatsEvent {
    public static final Parcelable.Creator<WakeLockEvent> CREATOR = new zzf();
    private final long mTimeout;
    final int mVersionCode;
    private final List<String> zzaGA;
    private final String zzaGB;
    private final long zzaGC;
    private int zzaGD;
    private final String zzaGE;
    private final float zzaGF;
    private long zzaGG;
    private final long zzaGu;
    private int zzaGv;
    private final String zzaGw;
    private final String zzaGx;
    private final String zzaGy;
    private final int zzaGz;

    WakeLockEvent(int n, long l, int n2, String string, int n3, List<String> list, String string2, long l2, int n4, String string3, String string4, float f, long l3, String string5) {
        this.mVersionCode = n;
        this.zzaGu = l;
        this.zzaGv = n2;
        this.zzaGw = string;
        this.zzaGx = string3;
        this.zzaGy = string5;
        this.zzaGz = n3;
        this.zzaGG = -1L;
        this.zzaGA = list;
        this.zzaGB = string2;
        this.zzaGC = l2;
        this.zzaGD = n4;
        this.zzaGE = string4;
        this.zzaGF = f;
        this.mTimeout = l3;
    }

    public WakeLockEvent(long l, int n, String string, int n2, List<String> list, String string2, long l2, int n3, String string3, String string4, float f, long l3, String string5) {
        this(2, l, n, string, n2, list, string2, l2, n3, string3, string4, f, l3, string5);
    }

    @Override
    public int getEventType() {
        return this.zzaGv;
    }

    @Override
    public long getTimeMillis() {
        return this.zzaGu;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzf.zza(this, parcel, n);
    }

    @Override
    public long zzye() {
        return this.zzaGG;
    }

    @Override
    public String zzyf() {
        String string = String.valueOf("\t");
        String string2 = String.valueOf(this.zzyg());
        String string3 = String.valueOf("\t");
        int n = this.zzyj();
        String string4 = String.valueOf("\t");
        String string5 = this.zzyk() == null ? "" : TextUtils.join((CharSequence)",", this.zzyk());
        String string6 = String.valueOf("\t");
        int n2 = this.zzyn();
        String string7 = String.valueOf("\t");
        String string8 = this.zzyh() == null ? "" : this.zzyh();
        String string9 = String.valueOf("\t");
        String string10 = this.zzyo() == null ? "" : this.zzyo();
        String string11 = String.valueOf("\t");
        float f = this.zzyp();
        String string12 = String.valueOf("\t");
        String string13 = this.zzyi() == null ? "" : this.zzyi();
        StringBuilder stringBuilder = new StringBuilder(37 + String.valueOf(string).length() + String.valueOf(string2).length() + String.valueOf(string3).length() + String.valueOf(string4).length() + String.valueOf(string5).length() + String.valueOf(string6).length() + String.valueOf(string7).length() + String.valueOf(string8).length() + String.valueOf(string9).length() + String.valueOf(string10).length() + String.valueOf(string11).length() + String.valueOf(string12).length() + String.valueOf(string13).length());
        stringBuilder.append(string);
        stringBuilder.append(string2);
        stringBuilder.append(string3);
        stringBuilder.append(n);
        stringBuilder.append(string4);
        stringBuilder.append(string5);
        stringBuilder.append(string6);
        stringBuilder.append(n2);
        stringBuilder.append(string7);
        stringBuilder.append(string8);
        stringBuilder.append(string9);
        stringBuilder.append(string10);
        stringBuilder.append(string11);
        stringBuilder.append(f);
        stringBuilder.append(string12);
        stringBuilder.append(string13);
        return stringBuilder.toString();
    }

    public String zzyg() {
        return this.zzaGw;
    }

    public String zzyh() {
        return this.zzaGx;
    }

    public String zzyi() {
        return this.zzaGy;
    }

    public int zzyj() {
        return this.zzaGz;
    }

    public List<String> zzyk() {
        return this.zzaGA;
    }

    public String zzyl() {
        return this.zzaGB;
    }

    public long zzym() {
        return this.zzaGC;
    }

    public int zzyn() {
        return this.zzaGD;
    }

    public String zzyo() {
        return this.zzaGE;
    }

    public float zzyp() {
        return this.zzaGF;
    }

    public long zzyq() {
        return this.mTimeout;
    }
}
