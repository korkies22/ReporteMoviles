/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.QueueFileLogStore;

public class QueueFileLogStore.LogBytes {
    public final byte[] bytes;
    public final int offset;

    public QueueFileLogStore.LogBytes(byte[] arrby, int n) {
        this.bytes = arrby;
        this.offset = n;
    }
}
