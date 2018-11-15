/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zzi
extends zzam {
    private static final String ID = zzag.zzfr.toString();
    private final Context mContext;

    public zzi(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        try {
            object = zzdm.zzR(this.mContext.getPackageManager().getPackageInfo((String)this.mContext.getPackageName(), (int)0).versionName);
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            object = String.valueOf(this.mContext.getPackageName());
            String string = String.valueOf(nameNotFoundException.getMessage());
            StringBuilder stringBuilder = new StringBuilder(25 + String.valueOf(object).length() + String.valueOf(string).length());
            stringBuilder.append("Package name ");
            stringBuilder.append((String)object);
            stringBuilder.append(" not found. ");
            stringBuilder.append(string);
            zzbo.e(stringBuilder.toString());
            return zzdm.zzQm();
        }
    }
}
