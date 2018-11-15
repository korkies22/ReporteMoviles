/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.util.concurrent.Callable;

protected static abstract class HttpRequest.Operation<V>
implements Callable<V> {
    protected HttpRequest.Operation() {
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public V call() throws HttpRequest.HttpRequestException {
        void var2_8;
        boolean bl = true;
        V v = this.run();
        try {
            this.done();
        }
        catch (IOException iOException) {
            throw new HttpRequest.HttpRequestException(iOException);
        }
        return v;
        catch (Throwable throwable) {
            bl = false;
        }
        catch (IOException iOException) {
            try {
                throw new HttpRequest.HttpRequestException(iOException);
                catch (HttpRequest.HttpRequestException httpRequestException) {
                    throw httpRequestException;
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        try {
            this.done();
            throw var2_8;
        }
        catch (IOException iOException) {
            if (bl) throw var2_8;
            throw new HttpRequest.HttpRequestException(iOException);
        }
    }

    protected abstract void done() throws IOException;

    protected abstract V run() throws HttpRequest.HttpRequestException, IOException;
}
