/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import io.fabric.sdk.android.services.common.QueueFile;
import java.io.IOException;
import java.io.InputStream;

class QueueFileLogStore
implements QueueFile.ElementReader {
    final /* synthetic */ byte[] val$logBytes;
    final /* synthetic */ int[] val$offsetHolder;

    QueueFileLogStore(byte[] arrby, int[] arrn) {
        this.val$logBytes = arrby;
        this.val$offsetHolder = arrn;
    }

    @Override
    public void read(InputStream inputStream, int n) throws IOException {
        int[] arrn;
        try {
            inputStream.read(this.val$logBytes, this.val$offsetHolder[0], n);
            arrn = this.val$offsetHolder;
        }
        catch (Throwable throwable) {
            inputStream.close();
            throw throwable;
        }
        arrn[0] = arrn[0] + n;
        inputStream.close();
    }
}
