/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.zzac;

public class zzam {
    private final Resources zzaFn;
    private final String zzaFo;

    public zzam(Context context) {
        zzac.zzw(context);
        this.zzaFn = context.getResources();
        this.zzaFo = this.zzaFn.getResourcePackageName(R.string.common_google_play_services_unknown_issue);
    }

    public String getString(String string2) {
        int n = this.zzaFn.getIdentifier(string2, "string", this.zzaFo);
        if (n == 0) {
            return null;
        }
        return this.zzaFn.getString(n);
    }
}
