/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzbf;
import java.util.Map;

class zzd
implements DataLayer.zzb {
    private final Context zzqr;

    public zzd(Context context) {
        this.zzqr = context;
    }

    @Override
    public void zzZ(Map<String, Object> object) {
        Object object2;
        Object object3 = object2 = object.get("gtm.url");
        if (object2 == null) {
            object = object.get("gtm");
            object3 = object2;
            if (object != null) {
                object3 = object2;
                if (object instanceof Map) {
                    object3 = ((Map)object).get("url");
                }
            }
        }
        if (object3 != null) {
            if (!(object3 instanceof String)) {
                return;
            }
            object = Uri.parse((String)((String)object3)).getQueryParameter("referrer");
            if (object != null) {
                zzbf.zzI(this.zzqr, (String)object);
            }
        }
    }
}
