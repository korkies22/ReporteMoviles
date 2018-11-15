/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.tagmanager.TagManager;
import com.google.android.gms.tagmanager.zzp;
import com.google.android.gms.tagmanager.zzt;

final class TagManager
implements TagManager.zza {
    TagManager() {
    }

    @Override
    public zzp zza(Context context, com.google.android.gms.tagmanager.TagManager tagManager, Looper looper, String string, int n, zzt zzt2) {
        return new zzp(context, tagManager, looper, string, n, zzt2);
    }
}
