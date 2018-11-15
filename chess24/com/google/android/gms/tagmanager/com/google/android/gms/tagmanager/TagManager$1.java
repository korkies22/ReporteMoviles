/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.DataLayer;
import java.util.Map;

class TagManager
implements DataLayer.zzb {
    TagManager() {
    }

    @Override
    public void zzZ(Map<String, Object> object) {
        if ((object = object.get("event")) != null) {
            TagManager.this.zzhv(object.toString());
        }
    }
}
