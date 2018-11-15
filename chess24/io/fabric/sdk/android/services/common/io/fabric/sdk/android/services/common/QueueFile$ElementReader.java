/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.services.common.QueueFile;
import java.io.IOException;
import java.io.InputStream;

public static interface QueueFile.ElementReader {
    public void read(InputStream var1, int var2) throws IOException;
}
