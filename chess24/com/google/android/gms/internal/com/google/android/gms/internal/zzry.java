/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzac;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzry {
    private final Map<String, String> zzFs;
    private final String zzacg;
    private final long zzadg;
    private final String zzadh;
    private final boolean zzadi;
    private long zzadj;

    /*
     * Enabled aggressive block sorting
     */
    public zzry(long l, String map, String string, boolean bl, long l2, Map<String, String> map2) {
        zzac.zzdv(map);
        zzac.zzdv(string);
        this.zzadg = l;
        this.zzacg = map;
        this.zzadh = string;
        this.zzadi = bl;
        this.zzadj = l2;
        map = map2 != null ? new HashMap<String, String>(map2) : Collections.emptyMap();
        this.zzFs = map;
    }

    public Map<String, String> zzfz() {
        return this.zzFs;
    }

    public String zzlX() {
        return this.zzacg;
    }

    public long zznI() {
        return this.zzadg;
    }

    public String zznJ() {
        return this.zzadh;
    }

    public boolean zznK() {
        return this.zzadi;
    }

    public long zznL() {
        return this.zzadj;
    }

    public void zzr(long l) {
        this.zzadj = l;
    }
}
