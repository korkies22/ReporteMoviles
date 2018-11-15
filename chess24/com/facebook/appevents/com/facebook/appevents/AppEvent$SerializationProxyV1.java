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

static class AppEvent.SerializationProxyV1
implements Serializable {
    private static final long serialVersionUID = -2488473066578201069L;
    private final boolean isImplicit;
    private final String jsonString;

    private AppEvent.SerializationProxyV1(String string, boolean bl) {
        this.jsonString = string;
        this.isImplicit = bl;
    }

    private Object readResolve() throws JSONException {
        return new AppEvent(this.jsonString, this.isImplicit, null, null);
    }
}
