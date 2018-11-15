/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsh;

public class zzss
implements zzsh {
    public String zzabK;
    public String zzabL;
    public String zzafc;
    public int zzafd = -1;
    public int zzafe = -1;

    public String zzmx() {
        return this.zzabK;
    }

    public String zzmy() {
        return this.zzabL;
    }

    public boolean zzpg() {
        if (this.zzabK != null) {
            return true;
        }
        return false;
    }

    public boolean zzph() {
        if (this.zzabL != null) {
            return true;
        }
        return false;
    }

    public boolean zzpi() {
        if (this.zzafc != null) {
            return true;
        }
        return false;
    }

    public String zzpj() {
        return this.zzafc;
    }

    public boolean zzpk() {
        if (this.zzafd >= 0) {
            return true;
        }
        return false;
    }

    public int zzpl() {
        return this.zzafd;
    }

    public boolean zzpm() {
        if (this.zzafe != -1) {
            return true;
        }
        return false;
    }

    public boolean zzpn() {
        if (this.zzafe == 1) {
            return true;
        }
        return false;
    }
}
