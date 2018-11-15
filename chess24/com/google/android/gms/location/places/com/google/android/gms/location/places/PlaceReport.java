/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.location.places.zzl;

public class PlaceReport
extends zza
implements ReflectedParcelable {
    public static final Parcelable.Creator<PlaceReport> CREATOR = new zzl();
    private final String mTag;
    final int mVersionCode;
    private final String zzabN;
    private final String zzblg;

    PlaceReport(int n, String string, String string2, String string3) {
        this.mVersionCode = n;
        this.zzblg = string;
        this.mTag = string2;
        this.zzabN = string3;
    }

    public static PlaceReport create(String string, String string2) {
        return PlaceReport.zzj(string, string2, "unknown");
    }

    private static boolean zzeY(String string) {
        int n;
        block11 : {
            switch (string.hashCode()) {
                default: {
                    break;
                }
                case 1287171955: {
                    if (!string.equals("inferredRadioSignals")) break;
                    n = 3;
                    break block11;
                }
                case 1164924125: {
                    if (!string.equals("inferredSnappedToRoad")) break;
                    n = 5;
                    break block11;
                }
                case -262743844: {
                    if (!string.equals("inferredReverseGeocoding")) break;
                    n = 4;
                    break block11;
                }
                case -284840886: {
                    if (!string.equals("unknown")) break;
                    n = 0;
                    break block11;
                }
                case -1194968642: {
                    if (!string.equals("userReported")) break;
                    n = 1;
                    break block11;
                }
                case -1436706272: {
                    if (!string.equals("inferredGeofencing")) break;
                    n = 2;
                    break block11;
                }
            }
            n = -1;
        }
        switch (n) {
            default: {
                return false;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
        }
        return true;
    }

    public static PlaceReport zzj(String string, String string2, String string3) {
        zzac.zzw(string);
        zzac.zzdv(string2);
        zzac.zzdv(string3);
        zzac.zzb(PlaceReport.zzeY(string3), (Object)"Invalid source");
        return new PlaceReport(1, string, string2, string3);
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof PlaceReport;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (PlaceReport)object;
        bl = bl2;
        if (zzaa.equal(this.zzblg, object.zzblg)) {
            bl = bl2;
            if (zzaa.equal(this.mTag, object.mTag)) {
                bl = bl2;
                if (zzaa.equal(this.zzabN, object.zzabN)) {
                    bl = true;
                }
            }
        }
        return bl;
    }

    public String getPlaceId() {
        return this.zzblg;
    }

    public String getSource() {
        return this.zzabN;
    }

    public String getTag() {
        return this.mTag;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzblg, this.mTag, this.zzabN);
    }

    public String toString() {
        zzaa.zza zza2 = zzaa.zzv(this);
        zza2.zzg("placeId", this.zzblg);
        zza2.zzg("tag", this.mTag);
        if (!"unknown".equals(this.zzabN)) {
            zza2.zzg("source", this.zzabN);
        }
        return zza2.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzl.zza(this, parcel, n);
    }
}
