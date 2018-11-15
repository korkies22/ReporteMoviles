/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import java.util.Comparator;

final class zzu
implements Comparator<byte[]> {
    zzu() {
    }

    @Override
    public /* synthetic */ int compare(Object object, Object object2) {
        return this.zza((byte[])object, (byte[])object2);
    }

    public int zza(byte[] arrby, byte[] arrby2) {
        return arrby.length - arrby2.length;
    }
}
