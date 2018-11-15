/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.google.android.gms.tagmanager;

import android.content.SharedPreferences;

final class zzdd
implements Runnable {
    zzdd() {
    }

    @Override
    public void run() {
        Editor.this.commit();
    }
}
