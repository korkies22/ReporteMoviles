/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

import com.facebook.appevents.AccessTokenAppIdPair;
import java.io.Serializable;

static class AccessTokenAppIdPair.SerializationProxyV1
implements Serializable {
    private static final long serialVersionUID = -2488473066578201069L;
    private final String accessTokenString;
    private final String appId;

    private AccessTokenAppIdPair.SerializationProxyV1(String string, String string2) {
        this.accessTokenString = string;
        this.appId = string2;
    }

    private Object readResolve() {
        return new AccessTokenAppIdPair(this.accessTokenString, this.appId);
    }
}
