/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.text.TextUtils
 */
package com.google.android.gms.common.util;

import android.database.CharArrayBuffer;
import android.text.TextUtils;

public final class zzg {
    public static void zzb(String string, CharArrayBuffer charArrayBuffer) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            charArrayBuffer.sizeCopied = 0;
        } else if (charArrayBuffer.data != null && charArrayBuffer.data.length >= string.length()) {
            string.getChars(0, string.length(), charArrayBuffer.data, 0);
        } else {
            charArrayBuffer.data = string.toCharArray();
        }
        charArrayBuffer.sizeCopied = string.length();
    }
}
