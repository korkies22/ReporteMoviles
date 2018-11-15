/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Base64
 */
package com.google.android.gms.common.util;

import android.util.Base64;

public final class zzc {
    public static String zzq(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        return Base64.encodeToString((byte[])arrby, (int)0);
    }

    public static String zzr(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        return Base64.encodeToString((byte[])arrby, (int)10);
    }

    public static String zzs(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        return Base64.encodeToString((byte[])arrby, (int)11);
    }
}
