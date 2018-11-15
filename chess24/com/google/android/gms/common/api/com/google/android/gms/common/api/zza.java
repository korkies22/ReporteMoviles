/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Status;

public class zza
extends Exception {
    protected final Status zzahq;

    public zza(@NonNull Status status) {
        super(status.getStatusMessage());
        this.zzahq = status;
    }
}
