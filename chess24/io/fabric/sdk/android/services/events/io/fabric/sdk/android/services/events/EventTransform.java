/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.events;

import java.io.IOException;

public interface EventTransform<T> {
    public byte[] toBytes(T var1) throws IOException;
}
