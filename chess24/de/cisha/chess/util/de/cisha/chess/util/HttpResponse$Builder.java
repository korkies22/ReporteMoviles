/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

import de.cisha.chess.util.HttpResponse;

public static class HttpResponse.Builder {
    private String bBody = "";
    private int bErrorCode = 200;
    private HttpResponse.State bState = HttpResponse.State.OK;

    public HttpResponse create() {
        return new HttpResponse(this.bState, this.bErrorCode, this.bBody, null);
    }

    public HttpResponse.Builder setBody(String string) {
        this.bBody = string;
        return this;
    }

    public HttpResponse.Builder setHttpErrorCode(int n) {
        this.bErrorCode = n;
        return this;
    }

    public HttpResponse.Builder setState(HttpResponse.State state) {
        this.bState = state;
        return this;
    }
}
