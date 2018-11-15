/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 */
package com.google.android.gms.tagmanager;

import android.os.Build;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zzac
extends zzam {
    private static final String ID = zzag.zzdn.toString();

    public zzac() {
        super(ID, new String[0]);
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        String string = Build.MANUFACTURER;
        String string2 = Build.MODEL;
        object = string2;
        if (!string2.startsWith(string)) {
            object = string2;
            if (!string.equals("unknown")) {
                object = new StringBuilder(1 + String.valueOf(string).length() + String.valueOf(string2).length());
                object.append(string);
                object.append(" ");
                object.append(string2);
                object = object.toString();
            }
        }
        return zzdm.zzR(object);
    }
}
