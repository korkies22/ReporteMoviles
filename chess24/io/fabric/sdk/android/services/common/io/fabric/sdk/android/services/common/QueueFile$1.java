/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.services.common.QueueFile;
import java.io.IOException;
import java.io.InputStream;

class QueueFile
implements QueueFile.ElementReader {
    boolean first = true;
    final /* synthetic */ StringBuilder val$builder;

    QueueFile(StringBuilder stringBuilder) {
        this.val$builder = stringBuilder;
    }

    @Override
    public void read(InputStream inputStream, int n) throws IOException {
        if (this.first) {
            this.first = false;
        } else {
            this.val$builder.append(", ");
        }
        this.val$builder.append(n);
    }
}
