/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.tagmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

class zzdd {
    static void zza(SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT >= 9) {
            editor.apply();
            return;
        }
        new Thread(new Runnable(){

            @Override
            public void run() {
                Editor.this.commit();
            }
        }).start();
    }

    @SuppressLint(value={"CommitPrefEdits"})
    static void zzc(Context context, String string, String string2, String string3) {
        context = context.getSharedPreferences(string, 0).edit();
        context.putString(string2, string3);
        zzdd.zza((SharedPreferences.Editor)context);
    }

}
