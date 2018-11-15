/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.services.common.QueueFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

private final class QueueFile.ElementInputStream
extends InputStream {
    private int position;
    private int remaining;

    private QueueFile.ElementInputStream(QueueFile.Element element) {
        this.position = QueueFile.this.wrapPosition(element.position + 4);
        this.remaining = element.length;
    }

    @Override
    public int read() throws IOException {
        if (this.remaining == 0) {
            return -1;
        }
        QueueFile.this.raf.seek(this.position);
        int n = QueueFile.this.raf.read();
        this.position = QueueFile.this.wrapPosition(this.position + 1);
        --this.remaining;
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        QueueFile.nonNull(arrby, "buffer");
        if ((n | n2) >= 0 && n2 <= arrby.length - n) {
            if (this.remaining > 0) {
                int n3 = n2;
                if (n2 > this.remaining) {
                    n3 = this.remaining;
                }
                QueueFile.this.ringRead(this.position, arrby, n, n3);
                this.position = QueueFile.this.wrapPosition(this.position + n3);
                this.remaining -= n3;
                return n3;
            }
            return -1;
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
