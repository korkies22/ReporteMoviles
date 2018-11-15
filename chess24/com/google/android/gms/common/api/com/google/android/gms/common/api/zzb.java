/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzzs;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class zzb
extends Exception {
    private final ArrayMap<zzzs<?>, ConnectionResult> zzaxy;

    public zzb(ArrayMap<zzzs<?>, ConnectionResult> arrayMap) {
        this.zzaxy = arrayMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public String getMessage() {
        zzzs<?> zzzs2;
        ArrayList<String> arrayList = new ArrayList<String>();
        Object object = this.zzaxy.keySet().iterator();
        boolean bl = true;
        while (object.hasNext()) {
            zzzs2 = object.next();
            Object object2 = (ConnectionResult)this.zzaxy.get(zzzs2);
            if (object2.isSuccess()) {
                bl = false;
            }
            zzzs2 = String.valueOf(zzzs2.zzuV());
            object2 = String.valueOf(object2);
            StringBuilder stringBuilder = new StringBuilder(2 + String.valueOf(zzzs2).length() + String.valueOf(object2).length());
            stringBuilder.append((String)((Object)zzzs2));
            stringBuilder.append(": ");
            stringBuilder.append((String)object2);
            arrayList.add(stringBuilder.toString());
        }
        zzzs2 = new StringBuilder();
        object = bl ? "None of the queried APIs are available. " : "Some of the queried APIs are unavailable. ";
        zzzs2.append((String)object);
        zzzs2.append(TextUtils.join((CharSequence)"; ", arrayList));
        return zzzs2.toString();
    }

    public ArrayMap<zzzs<?>, ConnectionResult> zzuK() {
        return this.zzaxy;
    }
}
