/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.PlatformServiceClient;

final class LoginStatusClient
extends PlatformServiceClient {
    static final long DEFAULT_TOAST_DURATION_MS = 5000L;
    private final String graphApiVersion;
    private final String loggerRef;
    private final long toastDurationMs;

    LoginStatusClient(Context context, String string, String string2, String string3, long l) {
        super(context, 65546, 65547, 20170411, string);
        this.loggerRef = string2;
        this.graphApiVersion = string3;
        this.toastDurationMs = l;
    }

    @Override
    protected void populateRequestBundle(Bundle bundle) {
        bundle.putString("com.facebook.platform.extra.LOGGER_REF", this.loggerRef);
        bundle.putString("com.facebook.platform.extra.GRAPH_API_VERSION", this.graphApiVersion);
        bundle.putLong("com.facebook.platform.extra.EXTRA_TOAST_DURATION_MS", this.toastDurationMs);
    }
}
