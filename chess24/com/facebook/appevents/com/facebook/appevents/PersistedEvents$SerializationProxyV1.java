/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEvent;
import com.facebook.appevents.PersistedEvents;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

static class PersistedEvents.SerializationProxyV1
implements Serializable {
    private static final long serialVersionUID = 20160629001L;
    private final HashMap<AccessTokenAppIdPair, List<AppEvent>> proxyEvents;

    private PersistedEvents.SerializationProxyV1(HashMap<AccessTokenAppIdPair, List<AppEvent>> hashMap) {
        this.proxyEvents = hashMap;
    }

    private Object readResolve() {
        return new PersistedEvents(this.proxyEvents);
    }
}
