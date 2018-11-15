/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.zzu;
import java.util.Map;

private class Container.zza
implements zzu.zza {
    private Container.zza() {
    }

    @Override
    public Object zze(String string, Map<String, Object> map) {
        Container.FunctionCallMacroCallback functionCallMacroCallback = Container.this.zzgV(string);
        if (functionCallMacroCallback == null) {
            return null;
        }
        return functionCallMacroCallback.getValue(string, map);
    }
}
