/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.stats;

import android.support.v4.util.SimpleArrayMap;

public class zzd {
    private final long zzaGr;
    private final int zzaGs;
    private final SimpleArrayMap<String, Long> zzaGt;

    public zzd() {
        this.zzaGr = 60000L;
        this.zzaGs = 10;
        this.zzaGt = new SimpleArrayMap(10);
    }

    public zzd(int n, long l) {
        this.zzaGr = l;
        this.zzaGs = n;
        this.zzaGt = new SimpleArrayMap();
    }
}
