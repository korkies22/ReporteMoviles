/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 */
package com.google.android.gms.tagmanager;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zzbs
extends zzam {
    private static final String ID = zzag.zzdQ.toString();
    private final Context mContext;

    public zzbs(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        object = this.zzbC(this.mContext);
        if (object == null) {
            return zzdm.zzQm();
        }
        return zzdm.zzR(object);
    }

    protected String zzbC(Context context) {
        return Settings.Secure.getString((ContentResolver)context.getContentResolver(), (String)"android_id");
    }
}
