/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import java.security.PrivilegedAction;

static final class HttpRequest
implements PrivilegedAction<String> {
    final /* synthetic */ String val$name;
    final /* synthetic */ String val$value;

    HttpRequest(String string, String string2) {
        this.val$name = string;
        this.val$value = string2;
    }

    @Override
    public String run() {
        return System.setProperty(this.val$name, this.val$value);
    }
}
