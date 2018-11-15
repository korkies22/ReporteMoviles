/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

class HttpRequest
extends HttpRequest.CloseOperation<io.fabric.sdk.android.services.network.HttpRequest> {
    final /* synthetic */ OutputStream val$output;

    HttpRequest(Closeable closeable, boolean bl, OutputStream outputStream) {
        this.val$output = outputStream;
        super(closeable, bl);
    }

    @Override
    protected io.fabric.sdk.android.services.network.HttpRequest run() throws HttpRequest.HttpRequestException, IOException {
        return HttpRequest.this.receive(this.val$output);
    }
}
