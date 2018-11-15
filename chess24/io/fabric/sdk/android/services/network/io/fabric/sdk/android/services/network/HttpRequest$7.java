/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

class HttpRequest
extends HttpRequest.CloseOperation<io.fabric.sdk.android.services.network.HttpRequest> {
    final /* synthetic */ Reader val$input;
    final /* synthetic */ Writer val$output;

    HttpRequest(Closeable closeable, boolean bl, Reader reader, Writer writer) {
        this.val$input = reader;
        this.val$output = writer;
        super(closeable, bl);
    }

    @Override
    public io.fabric.sdk.android.services.network.HttpRequest run() throws IOException {
        int n;
        char[] arrc = new char[HttpRequest.this.bufferSize];
        while ((n = this.val$input.read(arrc)) != -1) {
            this.val$output.write(arrc, 0, n);
        }
        return HttpRequest.this;
    }
}
