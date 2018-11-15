/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.common.images;

import android.net.Uri;
import com.google.android.gms.common.images.zza;
import com.google.android.gms.common.internal.zzaa;

static final class zza.zza {
    public final Uri uri;

    public zza.zza(Uri uri) {
        this.uri = uri;
    }

    public boolean equals(Object object) {
        if (!(object instanceof zza.zza)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        return zzaa.equal((Object)((zza.zza)object).uri, (Object)this.uri);
    }

    public int hashCode() {
        return zzaa.hashCode(new Object[]{this.uri});
    }
}
