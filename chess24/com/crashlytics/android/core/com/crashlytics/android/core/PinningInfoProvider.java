/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import java.io.InputStream;

public interface PinningInfoProvider {
    public String getKeyStorePassword();

    public InputStream getKeyStoreStream();

    public String[] getPins();
}
