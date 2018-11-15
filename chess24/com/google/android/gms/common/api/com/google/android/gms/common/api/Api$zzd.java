/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import java.util.Collections;
import java.util.List;

public static abstract class Api.zzd<T extends Api.zzb, O> {
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    public List<Scope> zzp(O o) {
        return Collections.emptyList();
    }
}
