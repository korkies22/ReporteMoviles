/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import java.io.InputStream;

public interface PinningInfoProvider {
    public static final long PIN_CREATION_TIME_UNDEFINED = -1L;

    public String getKeyStorePassword();

    public InputStream getKeyStoreStream();

    public long getPinCreationTimeInMillis();

    public String[] getPins();
}
