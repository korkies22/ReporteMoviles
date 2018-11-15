/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tagmanager.zza;
import com.google.android.gms.tagmanager.zzbo;
import java.io.IOException;

class zza
implements zza.zza {
    zza() {
    }

    @Override
    public AdvertisingIdClient.Info zzOv() {
        void var1_7;
        String string;
        try {
            AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo(zza.this.mContext);
            return info;
        }
        catch (Exception exception) {
            string = "Unknown exception. Could not get the Advertising Id Info.";
        }
        catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
            string = "GooglePlayServicesNotAvailableException getting Advertising Id Info";
        }
        catch (IOException iOException) {
            string = "IOException getting Ad Id Info";
        }
        catch (GooglePlayServicesRepairableException googlePlayServicesRepairableException) {
            string = "GooglePlayServicesRepairableException getting Advertising Id Info";
        }
        catch (IllegalStateException illegalStateException) {
            string = "IllegalStateException getting Advertising Id Info";
        }
        zzbo.zzc(string, (Throwable)var1_7);
        return null;
    }
}
