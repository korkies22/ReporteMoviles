/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.google.android.gms.internal;

import android.app.Activity;
import com.google.android.gms.internal.zzsh;
import java.util.HashMap;
import java.util.Map;

public class zztf
implements zzsh {
    public String zzaar;
    public double zzafN = -1.0;
    public int zzafO = -1;
    public int zzafP = -1;
    public int zzafQ = -1;
    public int zzafR = -1;
    public Map<String, String> zzafS = new HashMap<String, String>();

    public int getSessionTimeout() {
        return this.zzafO;
    }

    public String getTrackingId() {
        return this.zzaar;
    }

    public String zzcc(String string) {
        String string2 = this.zzafS.get(string);
        if (string2 != null) {
            string = string2;
        }
        return string;
    }

    public boolean zzpS() {
        if (this.zzaar != null) {
            return true;
        }
        return false;
    }

    public boolean zzpT() {
        if (this.zzafN >= 0.0) {
            return true;
        }
        return false;
    }

    public double zzpU() {
        return this.zzafN;
    }

    public boolean zzpV() {
        if (this.zzafO >= 0) {
            return true;
        }
        return false;
    }

    public boolean zzpW() {
        if (this.zzafP != -1) {
            return true;
        }
        return false;
    }

    public boolean zzpX() {
        if (this.zzafP == 1) {
            return true;
        }
        return false;
    }

    public boolean zzpY() {
        if (this.zzafQ != -1) {
            return true;
        }
        return false;
    }

    public boolean zzpZ() {
        if (this.zzafQ == 1) {
            return true;
        }
        return false;
    }

    public boolean zzqa() {
        if (this.zzafR == 1) {
            return true;
        }
        return false;
    }

    public String zzr(Activity activity) {
        return this.zzcc(activity.getClass().getCanonicalName());
    }
}
