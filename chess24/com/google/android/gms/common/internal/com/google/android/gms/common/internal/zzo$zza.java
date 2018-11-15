/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Intent
 */
package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Intent;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzo;

private static final class zzo.zza {
    private final String zzaEI;
    private final ComponentName zzaEJ;
    private final String zzaca;

    public zzo.zza(ComponentName componentName) {
        this.zzaca = null;
        this.zzaEI = null;
        this.zzaEJ = zzac.zzw(componentName);
    }

    public zzo.zza(String string, String string2) {
        this.zzaca = zzac.zzdv(string);
        this.zzaEI = zzac.zzdv(string2);
        this.zzaEJ = null;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof zzo.zza)) {
            return false;
        }
        object = (zzo.zza)object;
        if (zzaa.equal(this.zzaca, object.zzaca) && zzaa.equal((Object)this.zzaEJ, (Object)object.zzaEJ)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return zzaa.hashCode(new Object[]{this.zzaca, this.zzaEJ});
    }

    public String toString() {
        if (this.zzaca == null) {
            return this.zzaEJ.flattenToString();
        }
        return this.zzaca;
    }

    public Intent zzxs() {
        if (this.zzaca != null) {
            return new Intent(this.zzaca).setPackage(this.zzaEI);
        }
        return new Intent().setComponent(this.zzaEJ);
    }
}
