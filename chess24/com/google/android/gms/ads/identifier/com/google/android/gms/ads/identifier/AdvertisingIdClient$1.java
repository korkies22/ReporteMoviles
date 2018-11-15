/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.ads.identifier;

import com.google.android.gms.ads.identifier.zza;

class AdvertisingIdClient
extends Thread {
    final /* synthetic */ String zzsk;

    AdvertisingIdClient(com.google.android.gms.ads.identifier.AdvertisingIdClient advertisingIdClient, String string) {
        this.zzsk = string;
    }

    @Override
    public void run() {
        new zza().zzu(this.zzsk);
    }
}
