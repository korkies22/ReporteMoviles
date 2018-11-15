/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.Flushable;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

class HttpRequest
extends HttpRequest.FlushOperation<io.fabric.sdk.android.services.network.HttpRequest> {
    final /* synthetic */ Reader val$input;
    final /* synthetic */ Writer val$writer;

    HttpRequest(Flushable flushable, Reader reader, Writer writer) {
        this.val$input = reader;
        this.val$writer = writer;
        super(flushable);
    }

    @Override
    protected io.fabric.sdk.android.services.network.HttpRequest run() throws IOException {
        return HttpRequest.this.copy(this.val$input, this.val$writer);
    }
}
