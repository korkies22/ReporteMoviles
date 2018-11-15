/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.facebook.appevents;

import android.content.Context;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEvent;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.PersistedEvents;
import com.facebook.appevents.SessionEventsState;
import com.facebook.internal.AttributionIdentifiers;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class AppEventCollection {
    private final HashMap<AccessTokenAppIdPair, SessionEventsState> stateMap = new HashMap();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SessionEventsState getSessionEventsState(AccessTokenAppIdPair accessTokenAppIdPair) {
        synchronized (this) {
            SessionEventsState sessionEventsState;
            SessionEventsState sessionEventsState2 = sessionEventsState = this.stateMap.get(accessTokenAppIdPair);
            if (sessionEventsState == null) {
                sessionEventsState2 = FacebookSdk.getApplicationContext();
                sessionEventsState2 = new SessionEventsState(AttributionIdentifiers.getAttributionIdentifiers((Context)sessionEventsState2), AppEventsLogger.getAnonymousAppDeviceGUID((Context)sessionEventsState2));
            }
            this.stateMap.put(accessTokenAppIdPair, sessionEventsState2);
            return sessionEventsState2;
        }
    }

    public void addEvent(AccessTokenAppIdPair accessTokenAppIdPair, AppEvent appEvent) {
        synchronized (this) {
            this.getSessionEventsState(accessTokenAppIdPair).addEvent(appEvent);
            return;
        }
    }

    public void addPersistedEvents(PersistedEvents persistedEvents) {
        synchronized (this) {
            if (persistedEvents == null) {
                return;
            }
            for (AccessTokenAppIdPair accessTokenAppIdPair : persistedEvents.keySet()) {
                SessionEventsState sessionEventsState = this.getSessionEventsState(accessTokenAppIdPair);
                Iterator<AppEvent> object = persistedEvents.get(accessTokenAppIdPair).iterator();
                while (object.hasNext()) {
                    sessionEventsState.addEvent(object.next());
                }
            }
            return;
        }
    }

    public SessionEventsState get(AccessTokenAppIdPair object) {
        synchronized (this) {
            object = this.stateMap.get(object);
            return object;
        }
    }

    public int getEventCount() {
        synchronized (this) {
            int n;
            int n2 = 0;
            Iterator<SessionEventsState> iterator = this.stateMap.values().iterator();
            while (iterator.hasNext()) {
                n = iterator.next().getAccumulatedEventCount();
            }
            {
                n2 += n;
                continue;
            }
            return n2;
        }
    }

    public Set<AccessTokenAppIdPair> keySet() {
        synchronized (this) {
            Set<AccessTokenAppIdPair> set = this.stateMap.keySet();
            return set;
        }
    }
}
