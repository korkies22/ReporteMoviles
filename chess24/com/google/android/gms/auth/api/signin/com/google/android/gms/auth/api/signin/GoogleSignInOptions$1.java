/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.auth.api.signin;

import com.google.android.gms.common.api.Scope;
import java.util.Comparator;

final class GoogleSignInOptions
implements Comparator<Scope> {
    GoogleSignInOptions() {
    }

    @Override
    public /* synthetic */ int compare(Object object, Object object2) {
        return this.zza((Scope)object, (Scope)object2);
    }

    public int zza(Scope scope, Scope scope2) {
        return scope.zzuS().compareTo(scope2.zzuS());
    }
}
