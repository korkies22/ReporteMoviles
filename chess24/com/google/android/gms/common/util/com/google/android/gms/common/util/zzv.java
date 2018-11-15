/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.util;

import java.util.regex.Pattern;

public class zzv {
    private static final Pattern zzaHb = Pattern.compile("\\$\\{(.*?)\\}");

    public static boolean zzdD(String string) {
        if (string != null && !string.trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
