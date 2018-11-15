/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zzg
extends zzam {
    private static final String ID = zzag.zzdf.toString();
    private final Context mContext;

    public zzg(Context context) {
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
            object = this.mContext.getPackageManager();
            object = zzdm.zzR(object.getApplicationLabel(object.getApplicationInfo(this.mContext.getPackageName(), 0)).toString());
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            zzbo.zzb("App name is not found.", (Throwable)nameNotFoundException);
            return zzdm.zzQm();
        }
    }
}
