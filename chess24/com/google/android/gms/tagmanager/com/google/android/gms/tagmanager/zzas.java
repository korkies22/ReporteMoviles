/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.tagmanager;

import android.text.TextUtils;

class zzas {
    private final long zzafh;
    private final long zzbEk;
    private final long zzbEl;
    private String zzbEm;

    zzas(long l, long l2, long l3) {
        this.zzbEk = l;
        this.zzafh = l2;
        this.zzbEl = l3;
    }

    long zzPi() {
        return this.zzbEk;
    }

    long zzPj() {
        return this.zzbEl;
    }

    String zzPk() {
        return this.zzbEm;
    }

    void zzhl(String string) {
        if (string != null) {
            if (TextUtils.isEmpty((CharSequence)string.trim())) {
                return;
            }
            this.zzbEm = string;
        }
    }
}
