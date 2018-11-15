/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaaz;

public static final class zzaaz.zzb<L> {
    private final L mListener;
    private final String zzaBB;

    zzaaz.zzb(L l, String string) {
        this.mListener = l;
        this.zzaBB = string;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof zzaaz.zzb)) {
            return false;
        }
        object = (zzaaz.zzb)object;
        if (this.mListener == object.mListener && this.zzaBB.equals(object.zzaBB)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 31 * System.identityHashCode(this.mListener) + this.zzaBB.hashCode();
    }
}
