/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.zzdm;
import com.google.android.gms.tagmanager.zzu;
import java.util.Map;

private class Container.zzb
implements zzu.zza {
    private Container.zzb() {
    }

    @Override
    public Object zze(String string, Map<String, Object> map) {
        Container.FunctionCallTagCallback functionCallTagCallback = Container.this.zzgW(string);
        if (functionCallTagCallback != null) {
            functionCallTagCallback.execute(string, map);
        }
        return zzdm.zzQl();
    }
}
