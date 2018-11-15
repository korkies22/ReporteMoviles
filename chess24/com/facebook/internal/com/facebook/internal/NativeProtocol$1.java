/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.NativeProtocol;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

static final class NativeProtocol
implements Runnable {
    NativeProtocol() {
    }

    @Override
    public void run() {
        try {
            Iterator iterator = facebookAppInfoList.iterator();
            while (iterator.hasNext()) {
                ((NativeProtocol.NativeAppInfo)iterator.next()).fetchAvailableVersions(true);
            }
            return;
        }
        finally {
            protocolVersionsAsyncUpdating.set(false);
        }
    }
}
