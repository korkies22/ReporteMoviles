/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

class HttpRequest
extends HttpRequest.CloseOperation<io.fabric.sdk.android.services.network.HttpRequest> {
    final /* synthetic */ BufferedReader val$reader;
    final /* synthetic */ Writer val$writer;

    HttpRequest(Closeable closeable, boolean bl, BufferedReader bufferedReader, Writer writer) {
        this.val$reader = bufferedReader;
        this.val$writer = writer;
        super(closeable, bl);
    }

    @Override
    public io.fabric.sdk.android.services.network.HttpRequest run() throws IOException {
        return HttpRequest.this.copy(this.val$reader, this.val$writer);
    }
}
