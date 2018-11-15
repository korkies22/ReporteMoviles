/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.services.common.QueueFile;

static class QueueFile.Element {
    static final int HEADER_LENGTH = 4;
    static final QueueFile.Element NULL = new QueueFile.Element(0, 0);
    final int length;
    final int position;

    QueueFile.Element(int n, int n2) {
        this.position = n;
        this.length = n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("[position = ");
        stringBuilder.append(this.position);
        stringBuilder.append(", length = ");
        stringBuilder.append(this.length);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
