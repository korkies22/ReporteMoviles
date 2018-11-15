/*
 * Decompiled with CFR 0_134.
 */
package com.google.firebase;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzabk;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseException;

public class zza
implements zzabk {
    @Override
    public Exception zzz(Status status) {
        if (status.getStatusCode() == 8) {
            return new FirebaseException(status.zzuU());
        }
        return new FirebaseApiNotAvailableException(status.zzuU());
    }
}
