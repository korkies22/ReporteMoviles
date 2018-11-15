/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.AdvertisingInfo;
import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;

class AdvertisingInfoProvider
extends BackgroundPriorityRunnable {
    final /* synthetic */ AdvertisingInfo val$advertisingInfo;

    AdvertisingInfoProvider(AdvertisingInfo advertisingInfo) {
        this.val$advertisingInfo = advertisingInfo;
    }

    @Override
    public void onRun() {
        AdvertisingInfo advertisingInfo = AdvertisingInfoProvider.this.getAdvertisingInfoFromStrategies();
        if (!this.val$advertisingInfo.equals(advertisingInfo)) {
            Fabric.getLogger().d("Fabric", "Asychronously getting Advertising Info and storing it to preferences");
            AdvertisingInfoProvider.this.storeInfoToPreferences(advertisingInfo);
        }
    }
}
