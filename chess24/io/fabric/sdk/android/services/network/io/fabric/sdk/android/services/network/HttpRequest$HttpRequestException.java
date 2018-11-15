/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;

public static class HttpRequest.HttpRequestException
extends RuntimeException {
    private static final long serialVersionUID = -1170466989781746231L;

    protected HttpRequest.HttpRequestException(IOException iOException) {
        super(iOException);
    }

    @Override
    public IOException getCause() {
        return (IOException)super.getCause();
    }
}
