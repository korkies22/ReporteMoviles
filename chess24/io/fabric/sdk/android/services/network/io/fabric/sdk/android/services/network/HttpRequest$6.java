/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class HttpRequest
extends HttpRequest.CloseOperation<io.fabric.sdk.android.services.network.HttpRequest> {
    final /* synthetic */ InputStream val$input;
    final /* synthetic */ OutputStream val$output;

    HttpRequest(Closeable closeable, boolean bl, InputStream inputStream, OutputStream outputStream) {
        this.val$input = inputStream;
        this.val$output = outputStream;
        super(closeable, bl);
    }

    @Override
    public io.fabric.sdk.android.services.network.HttpRequest run() throws IOException {
        int n;
        byte[] arrby = new byte[HttpRequest.this.bufferSize];
        while ((n = this.val$input.read(arrby)) != -1) {
            this.val$output.write(arrby, 0, n);
        }
        return HttpRequest.this;
    }
}
