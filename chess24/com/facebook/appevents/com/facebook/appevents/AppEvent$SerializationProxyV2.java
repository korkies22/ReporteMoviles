/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 */
package com.facebook.appevents;

import com.facebook.appevents.AppEvent;
import java.io.Serializable;
import org.json.JSONException;

static class AppEvent.SerializationProxyV2
implements Serializable {
    private static final long serialVersionUID = 20160803001L;
    private final String checksum;
    private final boolean isImplicit;
    private final String jsonString;

    private AppEvent.SerializationProxyV2(String string, boolean bl, String string2) {
        this.jsonString = string;
        this.isImplicit = bl;
        this.checksum = string2;
    }

    private Object readResolve() throws JSONException {
        return new AppEvent(this.jsonString, this.isImplicit, this.checksum, null);
    }
}
