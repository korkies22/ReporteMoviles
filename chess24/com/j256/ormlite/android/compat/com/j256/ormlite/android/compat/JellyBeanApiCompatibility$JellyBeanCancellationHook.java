/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.CancellationSignal
 */
package com.j256.ormlite.android.compat;

import android.os.CancellationSignal;
import com.j256.ormlite.android.compat.ApiCompatibility;
import com.j256.ormlite.android.compat.JellyBeanApiCompatibility;

protected static class JellyBeanApiCompatibility.JellyBeanCancellationHook
implements ApiCompatibility.CancellationHook {
    private final CancellationSignal cancellationSignal = new CancellationSignal();

    static /* synthetic */ CancellationSignal access$000(JellyBeanApiCompatibility.JellyBeanCancellationHook jellyBeanCancellationHook) {
        return jellyBeanCancellationHook.cancellationSignal;
    }

    @Override
    public void cancel() {
        this.cancellationSignal.cancel();
    }
}
