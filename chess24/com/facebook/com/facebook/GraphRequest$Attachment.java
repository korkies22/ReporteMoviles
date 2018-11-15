/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.GraphRequest;

private static class GraphRequest.Attachment {
    private final GraphRequest request;
    private final Object value;

    public GraphRequest.Attachment(GraphRequest graphRequest, Object object) {
        this.request = graphRequest;
        this.value = object;
    }

    public GraphRequest getRequest() {
        return this.request;
    }

    public Object getValue() {
        return this.value;
    }
}
