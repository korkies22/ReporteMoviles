/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.Flushable;
import java.io.IOException;

protected static abstract class HttpRequest.FlushOperation<V>
extends HttpRequest.Operation<V> {
    private final Flushable flushable;

    protected HttpRequest.FlushOperation(Flushable flushable) {
        this.flushable = flushable;
    }

    @Override
    protected void done() throws IOException {
        this.flushable.flush();
    }
}
