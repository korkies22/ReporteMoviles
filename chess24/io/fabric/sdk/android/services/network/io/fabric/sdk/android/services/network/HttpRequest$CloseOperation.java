/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

protected static abstract class HttpRequest.CloseOperation<V>
extends HttpRequest.Operation<V> {
    private final Closeable closeable;
    private final boolean ignoreCloseExceptions;

    protected HttpRequest.CloseOperation(Closeable closeable, boolean bl) {
        this.closeable = closeable;
        this.ignoreCloseExceptions = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void done() throws IOException {
        if (this.closeable instanceof Flushable) {
            ((Flushable)((Object)this.closeable)).flush();
        }
        if (!this.ignoreCloseExceptions) {
            this.closeable.close();
            return;
        }
        try {
            this.closeable.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }
}
