/*
 * Decompiled with CFR 0_134.
 */
package com.google.firebase;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzac;

public class FirebaseException
extends Exception {
    @Deprecated
    protected FirebaseException() {
    }

    public FirebaseException(@NonNull String string) {
        super(zzac.zzh(string, "Detail message must not be empty"));
    }

    public FirebaseException(@NonNull String string, Throwable throwable) {
        super(zzac.zzh(string, "Detail message must not be empty"), throwable);
    }
}
