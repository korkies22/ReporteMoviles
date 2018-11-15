/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.WindowManager
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zzco
extends zzam {
    private static final String ID = zzag.zzdD.toString();
    private final Context mContext;

    public zzco(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        object = new DisplayMetrics();
        ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics((DisplayMetrics)object);
        int n = object.widthPixels;
        int n2 = object.heightPixels;
        object = new StringBuilder(23);
        object.append(n);
        object.append("x");
        object.append(n2);
        return zzdm.zzR(object.toString());
    }
}
