/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.appevents;

import android.content.Context;
import android.os.Bundle;
import com.facebook.GraphRequest;
import com.facebook.appevents.AppEvent;
import com.facebook.appevents.internal.AppEventsLoggerUtility;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Utility;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class SessionEventsState {
    private final int MAX_ACCUMULATED_LOG_EVENTS = 1000;
    private List<AppEvent> accumulatedEvents = new ArrayList<AppEvent>();
    private String anonymousAppDeviceGUID;
    private AttributionIdentifiers attributionIdentifiers;
    private List<AppEvent> inFlightEvents = new ArrayList<AppEvent>();
    private int numSkippedEventsDueToFullBuffer;

    public SessionEventsState(AttributionIdentifiers attributionIdentifiers, String string) {
        this.attributionIdentifiers = attributionIdentifiers;
        this.anonymousAppDeviceGUID = string;
    }

    private byte[] getStringAsByteArray(String arrby) {
        try {
            arrby = arrby.getBytes("UTF-8");
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Utility.logd("Encoding exception: ", unsupportedEncodingException);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void populateRequest(GraphRequest graphRequest, Context context, int n, JSONArray object, boolean bl) {
        String string;
        JSONObject jSONObject;
        block5 : {
            try {
                void var5_7;
                jSONObject = AppEventsLoggerUtility.getJSONObjectForGraphAPICall(AppEventsLoggerUtility.GraphAPIActivityType.CUSTOM_APP_EVENTS, this.attributionIdentifiers, this.anonymousAppDeviceGUID, (boolean)var5_7, context);
                context = jSONObject;
                if (this.numSkippedEventsDueToFullBuffer > 0) {
                    jSONObject.put("num_skipped_events", n);
                    context = jSONObject;
                }
                break block5;
            }
            catch (JSONException jSONException) {}
            context = new JSONObject();
        }
        graphRequest.setGraphObject((JSONObject)context);
        jSONObject = graphRequest.getParameters();
        context = jSONObject;
        if (jSONObject == null) {
            context = new Bundle();
        }
        if ((string = object.toString()) != null) {
            context.putByteArray("custom_events_file", this.getStringAsByteArray(string));
            graphRequest.setTag(string);
        }
        graphRequest.setParameters((Bundle)context);
    }

    public void accumulatePersistedEvents(List<AppEvent> list) {
        synchronized (this) {
            this.accumulatedEvents.addAll(list);
            return;
        }
    }

    public void addEvent(AppEvent appEvent) {
        synchronized (this) {
            if (this.accumulatedEvents.size() + this.inFlightEvents.size() >= 1000) {
                ++this.numSkippedEventsDueToFullBuffer;
            } else {
                this.accumulatedEvents.add(appEvent);
            }
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void clearInFlightAndStats(boolean var1_1) {
        // MONITORENTER : this
        if (!var1_1) ** GOTO lbl5
        this.accumulatedEvents.addAll(this.inFlightEvents);
lbl5: // 2 sources:
        this.inFlightEvents.clear();
        this.numSkippedEventsDueToFullBuffer = 0;
        // MONITOREXIT : this
        return;
    }

    public int getAccumulatedEventCount() {
        synchronized (this) {
            int n = this.accumulatedEvents.size();
            return n;
        }
    }

    public List<AppEvent> getEventsToPersist() {
        synchronized (this) {
            List<AppEvent> list = this.accumulatedEvents;
            this.accumulatedEvents = new ArrayList<AppEvent>();
            return list;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int populateRequest(GraphRequest graphRequest, Context context, boolean bl, boolean bl2) {
        JSONArray jSONArray;
        int n;
        synchronized (this) {
            n = this.numSkippedEventsDueToFullBuffer;
            this.inFlightEvents.addAll(this.accumulatedEvents);
            this.accumulatedEvents.clear();
            jSONArray = new JSONArray();
            for (AppEvent appEvent : this.inFlightEvents) {
                if (appEvent.isChecksumValid()) {
                    if (!bl && appEvent.getIsImplicit()) continue;
                    jSONArray.put((Object)appEvent.getJSONObject());
                    continue;
                }
                Utility.logd("Event with invalid checksum: %s", appEvent.toString());
            }
            if (jSONArray.length() == 0) {
                return 0;
            }
        }
        this.populateRequest(graphRequest, context, n, jSONArray, bl2);
        return jSONArray.length();
    }
}
