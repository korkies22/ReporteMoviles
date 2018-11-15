/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import java.util.Map;

public class zzi {
    public final byte[] data;
    public final int statusCode;
    public final long zzA;
    public final Map<String, String> zzy;
    public final boolean zzz;

    public zzi(int n, byte[] arrby, Map<String, String> map, boolean bl, long l) {
        this.statusCode = n;
        this.data = arrby;
        this.zzy = map;
        this.zzz = bl;
        this.zzA = l;
    }

    public zzi(byte[] arrby, Map<String, String> map) {
        this(200, arrby, map, false, 0L);
    }
}
