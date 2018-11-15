/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public static class HttpRequest.RequestOutputStream
extends BufferedOutputStream {
    private final CharsetEncoder encoder;

    public HttpRequest.RequestOutputStream(OutputStream outputStream, String string, int n) {
        super(outputStream, n);
        this.encoder = Charset.forName(HttpRequest.getValidCharset(string)).newEncoder();
    }

    static /* synthetic */ CharsetEncoder access$200(HttpRequest.RequestOutputStream requestOutputStream) {
        return requestOutputStream.encoder;
    }

    public HttpRequest.RequestOutputStream write(String object) throws IOException {
        object = this.encoder.encode(CharBuffer.wrap((CharSequence)object));
        super.write(object.array(), 0, object.limit());
        return this;
    }
}
