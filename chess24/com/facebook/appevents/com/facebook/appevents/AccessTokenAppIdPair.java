/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import java.io.Serializable;

class AccessTokenAppIdPair
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String accessTokenString;
    private final String applicationId;

    public AccessTokenAppIdPair(AccessToken accessToken) {
        this(accessToken.getToken(), FacebookSdk.getApplicationId());
    }

    public AccessTokenAppIdPair(String string, String string2) {
        String string3 = string;
        if (Utility.isNullOrEmpty(string)) {
            string3 = null;
        }
        this.accessTokenString = string3;
        this.applicationId = string2;
    }

    private Object writeReplace() {
        return new SerializationProxyV1(this.accessTokenString, this.applicationId);
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof AccessTokenAppIdPair;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (AccessTokenAppIdPair)object;
        bl = bl2;
        if (Utility.areObjectsEqual(object.accessTokenString, this.accessTokenString)) {
            bl = bl2;
            if (Utility.areObjectsEqual(object.applicationId, this.applicationId)) {
                bl = true;
            }
        }
        return bl;
    }

    public String getAccessTokenString() {
        return this.accessTokenString;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public int hashCode() {
        String string = this.accessTokenString;
        int n = 0;
        int n2 = string == null ? 0 : this.accessTokenString.hashCode();
        if (this.applicationId != null) {
            n = this.applicationId.hashCode();
        }
        return n2 ^ n;
    }

    static class SerializationProxyV1
    implements Serializable {
        private static final long serialVersionUID = -2488473066578201069L;
        private final String accessTokenString;
        private final String appId;

        private SerializationProxyV1(String string, String string2) {
            this.accessTokenString = string;
            this.appId = string2;
        }

        private Object readResolve() {
            return new AccessTokenAppIdPair(this.accessTokenString, this.appId);
        }
    }

}
