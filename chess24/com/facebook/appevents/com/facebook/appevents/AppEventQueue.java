/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.appevents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEvent;
import com.facebook.appevents.AppEventCollection;
import com.facebook.appevents.AppEventStore;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.FlushReason;
import com.facebook.appevents.FlushResult;
import com.facebook.appevents.FlushStatistics;
import com.facebook.appevents.PersistedEvents;
import com.facebook.appevents.SessionEventsState;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Logger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class AppEventQueue {
    private static final int FLUSH_PERIOD_IN_SECONDS = 15;
    private static final int NUM_LOG_EVENTS_TO_TRY_TO_FLUSH_AFTER = 100;
    private static final String TAG = "com.facebook.appevents.AppEventQueue";
    private static volatile AppEventCollection appEventCollection = new AppEventCollection();
    private static final Runnable flushRunnable;
    private static ScheduledFuture scheduledFuture;
    private static final ScheduledExecutorService singleThreadExecutor;

    static {
        singleThreadExecutor = Executors.newSingleThreadScheduledExecutor();
        flushRunnable = new Runnable(){

            @Override
            public void run() {
                AppEventQueue.scheduledFuture = null;
                if (AppEventsLogger.getFlushBehavior() != AppEventsLogger.FlushBehavior.EXPLICIT_ONLY) {
                    AppEventQueue.flushAndWait(FlushReason.TIMER);
                }
            }
        };
    }

    AppEventQueue() {
    }

    public static void add(final AccessTokenAppIdPair accessTokenAppIdPair, final AppEvent appEvent) {
        singleThreadExecutor.execute(new Runnable(){

            @Override
            public void run() {
                appEventCollection.addEvent(accessTokenAppIdPair, appEvent);
                if (AppEventsLogger.getFlushBehavior() != AppEventsLogger.FlushBehavior.EXPLICIT_ONLY && appEventCollection.getEventCount() > 100) {
                    AppEventQueue.flushAndWait(FlushReason.EVENT_THRESHOLD);
                    return;
                }
                if (scheduledFuture == null) {
                    AppEventQueue.scheduledFuture = singleThreadExecutor.schedule(flushRunnable, 15L, TimeUnit.SECONDS);
                }
            }
        });
    }

    private static GraphRequest buildRequestForSession(final AccessTokenAppIdPair accessTokenAppIdPair, final SessionEventsState sessionEventsState, boolean bl, final FlushStatistics flushStatistics) {
        int n;
        String string = accessTokenAppIdPair.getApplicationId();
        boolean bl2 = false;
        FetchedAppSettings fetchedAppSettings = FetchedAppSettingsManager.queryAppSettings(string, false);
        final GraphRequest graphRequest = GraphRequest.newPostRequest(null, String.format("%s/activities", string), null, null);
        Object object = graphRequest.getParameters();
        string = object;
        if (object == null) {
            string = new Bundle();
        }
        string.putString("access_token", accessTokenAppIdPair.getAccessTokenString());
        object = AppEventsLogger.getPushNotificationsRegistrationId();
        if (object != null) {
            string.putString("device_token", (String)object);
        }
        graphRequest.setParameters((Bundle)string);
        if (fetchedAppSettings != null) {
            bl2 = fetchedAppSettings.supportsImplicitLogging();
        }
        if ((n = sessionEventsState.populateRequest(graphRequest, FacebookSdk.getApplicationContext(), bl2, bl)) == 0) {
            return null;
        }
        flushStatistics.numEvents += n;
        graphRequest.setCallback(new GraphRequest.Callback(){

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                AppEventQueue.handleResponse(accessTokenAppIdPair, graphRequest, graphResponse, sessionEventsState, flushStatistics);
            }
        });
        return graphRequest;
    }

    public static void flush(final FlushReason flushReason) {
        singleThreadExecutor.execute(new Runnable(){

            @Override
            public void run() {
                AppEventQueue.flushAndWait(flushReason);
            }
        });
    }

    static void flushAndWait(FlushReason object) {
        block2 : {
            PersistedEvents persistedEvents = AppEventStore.readAndClearStore();
            appEventCollection.addPersistedEvents(persistedEvents);
            try {
                object = AppEventQueue.sendEventsToServer(object, appEventCollection);
                if (object == null) break block2;
            }
            catch (Exception exception) {
                Log.w((String)TAG, (String)"Caught unexpected exception while flushing app events: ", (Throwable)exception);
                return;
            }
            persistedEvents = new Intent("com.facebook.sdk.APP_EVENTS_FLUSHED");
            persistedEvents.putExtra("com.facebook.sdk.APP_EVENTS_NUM_EVENTS_FLUSHED", object.numEvents);
            persistedEvents.putExtra("com.facebook.sdk.APP_EVENTS_FLUSH_RESULT", (Serializable)((Object)object.result));
            LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()).sendBroadcast((Intent)persistedEvents);
        }
    }

    public static Set<AccessTokenAppIdPair> getKeySet() {
        return appEventCollection.keySet();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void handleResponse(final AccessTokenAppIdPair accessTokenAppIdPair, GraphRequest graphRequest, GraphResponse object, final SessionEventsState sessionEventsState, FlushStatistics flushStatistics) {
        FacebookRequestError facebookRequestError = object.getError();
        String string = "Success";
        FlushResult flushResult = FlushResult.SUCCESS;
        boolean bl = true;
        if (facebookRequestError != null) {
            if (facebookRequestError.getErrorCode() == -1) {
                string = "Failed: No Connectivity";
                flushResult = FlushResult.NO_CONNECTIVITY;
            } else {
                string = String.format("Failed:\n  Response: %s\n  Error %s", object.toString(), facebookRequestError.toString());
                flushResult = FlushResult.SERVER_ERROR;
            }
        }
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.APP_EVENTS)) {
            block9 : {
                object = (String)graphRequest.getTag();
                try {
                    object = new JSONArray((String)object).toString(2);
                    break block9;
                }
                catch (JSONException jSONException) {}
                object = "<Can't encode events for debug logging>";
            }
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Flush completed\nParams: %s\n  Result: %s\n  Events JSON: %s", graphRequest.getGraphObject().toString(), string, object);
        }
        if (facebookRequestError == null) {
            bl = false;
        }
        sessionEventsState.clearInFlightAndStats(bl);
        if (flushResult == FlushResult.NO_CONNECTIVITY) {
            FacebookSdk.getExecutor().execute(new Runnable(){

                @Override
                public void run() {
                    AppEventStore.persistEvents(accessTokenAppIdPair, sessionEventsState);
                }
            });
        }
        if (flushResult != FlushResult.SUCCESS && flushStatistics.result != FlushResult.NO_CONNECTIVITY) {
            flushStatistics.result = flushResult;
        }
    }

    public static void persistToDisk() {
        singleThreadExecutor.execute(new Runnable(){

            @Override
            public void run() {
                AppEventStore.persistEvents(appEventCollection);
                AppEventQueue.appEventCollection = new AppEventCollection();
            }
        });
    }

    private static FlushStatistics sendEventsToServer(FlushReason object, AppEventCollection appEventCollection) {
        FlushStatistics flushStatistics = new FlushStatistics();
        boolean bl = FacebookSdk.getLimitEventAndDataUsage(FacebookSdk.getApplicationContext());
        ArrayList<GraphRequest> arrayList = new ArrayList<GraphRequest>();
        for (AccessTokenAppIdPair accessTokenAppIdPair : appEventCollection.keySet()) {
            GraphRequest object2 = AppEventQueue.buildRequestForSession(accessTokenAppIdPair, appEventCollection.get(accessTokenAppIdPair), bl, flushStatistics);
            if (object2 == null) continue;
            arrayList.add(object2);
        }
        if (arrayList.size() > 0) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Flushing %d events due to %s.", flushStatistics.numEvents, object.toString());
            object = arrayList.iterator();
            while (object.hasNext()) {
                ((GraphRequest)object.next()).executeAndWait();
            }
            return flushStatistics;
        }
        return null;
    }

}
