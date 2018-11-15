/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import java.security.PrivilegedAction;

static final class HttpRequest
implements PrivilegedAction<String> {
    final /* synthetic */ String val$name;

    HttpRequest(String string) {
        this.val$name = string;
    }

    @Override
    public String run() {
        return System.clearProperty(this.val$name);
    }
}
