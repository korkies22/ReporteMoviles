/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.CharBuffer;

class HttpRequest
extends HttpRequest.CloseOperation<io.fabric.sdk.android.services.network.HttpRequest> {
    final /* synthetic */ Appendable val$appendable;
    final /* synthetic */ BufferedReader val$reader;

    HttpRequest(Closeable closeable, boolean bl, BufferedReader bufferedReader, Appendable appendable) {
        this.val$reader = bufferedReader;
        this.val$appendable = appendable;
        super(closeable, bl);
    }

    @Override
    public io.fabric.sdk.android.services.network.HttpRequest run() throws IOException {
        int n;
        CharBuffer charBuffer = CharBuffer.allocate(HttpRequest.this.bufferSize);
        while ((n = this.val$reader.read(charBuffer)) != -1) {
            charBuffer.rewind();
            this.val$appendable.append(charBuffer, 0, n);
            charBuffer.rewind();
        }
        return HttpRequest.this;
    }
}
