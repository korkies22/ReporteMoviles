/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Context
 *  android.os.Bundle
 *  android.util.Log
 */
package com.facebook.appevents.internal;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.internal.AppEventUtility;
import com.facebook.appevents.internal.AutomaticAnalyticsLogger;
import com.facebook.appevents.internal.Constants;
import com.facebook.appevents.internal.SessionInfo;
import com.facebook.appevents.internal.SessionLogger;
import com.facebook.appevents.internal.SourceApplicationInfo;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ActivityLifecycleTracker {
    private static final String INCORRECT_IMPL_WARNING = "Unexpected activity pause without a matching activity resume. Logging data may be incorrect. Make sure you call activateApp from your Application's onCreate method";
    private static final long INTERRUPTION_THRESHOLD_MILLISECONDS = 1000L;
    private static final String TAG = ActivityLifecycleTracker.class.getCanonicalName();
    private static String appId;
    private static long currentActivityAppearTime;
    private static volatile ScheduledFuture currentFuture;
    private static final Object currentFutureLock;
    private static volatile SessionInfo currentSession;
    private static AtomicInteger foregroundActivityCount;
    private static final ScheduledExecutorService singleThreadExecutor;
    private static AtomicBoolean tracking;

    static {
        singleThreadExecutor = Executors.newSingleThreadScheduledExecutor();
        currentFutureLock = new Object();
        foregroundActivityCount = new AtomicInteger(0);
        tracking = new AtomicBoolean(false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void cancelCurrentTask() {
        Object object = currentFutureLock;
        synchronized (object) {
            if (currentFuture != null) {
                currentFuture.cancel(false);
            }
            currentFuture = null;
            return;
        }
    }

    public static UUID getCurrentSessionGuid() {
        if (currentSession != null) {
            return currentSession.getSessionId();
        }
        return null;
    }

    private static int getSessionTimeoutInSeconds() {
        FetchedAppSettings fetchedAppSettings = FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
        if (fetchedAppSettings == null) {
            return Constants.getDefaultAppEventsSessionTimeoutInSeconds();
        }
        return fetchedAppSettings.getSessionTimeoutInSeconds();
    }

    public static boolean isTracking() {
        return tracking.get();
    }

    public static void onActivityCreated(Activity object) {
        long l = System.currentTimeMillis();
        object = new Runnable(object.getApplicationContext(), Utility.getActivityName((Context)object), l, SourceApplicationInfo.Factory.create(object)){
            final /* synthetic */ String val$activityName;
            final /* synthetic */ Context val$applicationContext;
            final /* synthetic */ long val$currentTime;
            final /* synthetic */ SourceApplicationInfo val$sourceApplicationInfo;
            {
                this.val$applicationContext = context;
                this.val$activityName = string;
                this.val$currentTime = l;
                this.val$sourceApplicationInfo = sourceApplicationInfo;
            }

            @Override
            public void run() {
                if (currentSession == null) {
                    SessionInfo sessionInfo = SessionInfo.getStoredSessionInfo();
                    if (sessionInfo != null) {
                        SessionLogger.logDeactivateApp(this.val$applicationContext, this.val$activityName, sessionInfo, appId);
                    }
                    currentSession = new SessionInfo(this.val$currentTime, null);
                    currentSession.setSourceApplicationInfo(this.val$sourceApplicationInfo);
                    SessionLogger.logActivateApp(this.val$applicationContext, this.val$activityName, this.val$sourceApplicationInfo, appId);
                }
            }
        };
        singleThreadExecutor.execute((Runnable)object);
    }

    private static void onActivityPaused(Activity object) {
        if (foregroundActivityCount.decrementAndGet() < 0) {
            foregroundActivityCount.set(0);
            Log.w((String)TAG, (String)INCORRECT_IMPL_WARNING);
        }
        ActivityLifecycleTracker.cancelCurrentTask();
        object = new Runnable(System.currentTimeMillis(), object.getApplicationContext(), Utility.getActivityName((Context)object)){
            final /* synthetic */ String val$activityName;
            final /* synthetic */ Context val$applicationContext;
            final /* synthetic */ long val$currentTime;
            {
                this.val$currentTime = l;
                this.val$applicationContext = context;
                this.val$activityName = string;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                if (currentSession == null) {
                    currentSession = new SessionInfo(this.val$currentTime, null);
                }
                currentSession.setSessionLastEventTime(this.val$currentTime);
                if (foregroundActivityCount.get() <= 0) {
                    Runnable runnable = new Runnable(){

                        /*
                         * Enabled aggressive block sorting
                         * Enabled unnecessary exception pruning
                         * Enabled aggressive exception aggregation
                         */
                        @Override
                        public void run() {
                            if (foregroundActivityCount.get() <= 0) {
                                SessionLogger.logDeactivateApp(4.this.val$applicationContext, 4.this.val$activityName, currentSession, appId);
                                SessionInfo.clearSavedSessionFromDisk();
                                currentSession = null;
                            }
                            Object object = currentFutureLock;
                            synchronized (object) {
                                currentFuture = null;
                                return;
                            }
                        }
                    };
                    Object object = currentFutureLock;
                    synchronized (object) {
                        currentFuture = singleThreadExecutor.schedule(runnable, (long)ActivityLifecycleTracker.getSessionTimeoutInSeconds(), TimeUnit.SECONDS);
                    }
                }
                long l = currentActivityAppearTime;
                long l2 = 0L;
                if (l > 0L) {
                    l2 = (this.val$currentTime - l) / 1000L;
                }
                AutomaticAnalyticsLogger.logActivityTimeSpentEvent(this.val$activityName, l2);
                currentSession.writeSessionToDisk();
            }

        };
        singleThreadExecutor.execute((Runnable)object);
    }

    public static void onActivityResumed(Activity object) {
        long l;
        foregroundActivityCount.incrementAndGet();
        ActivityLifecycleTracker.cancelCurrentTask();
        currentActivityAppearTime = l = System.currentTimeMillis();
        object = new Runnable(object.getApplicationContext(), Utility.getActivityName((Context)object)){
            final /* synthetic */ String val$activityName;
            final /* synthetic */ Context val$applicationContext;
            {
                this.val$applicationContext = context;
                this.val$activityName = string;
            }

            @Override
            public void run() {
                if (currentSession == null) {
                    currentSession = new SessionInfo(l, null);
                    SessionLogger.logActivateApp(this.val$applicationContext, this.val$activityName, null, appId);
                } else if (currentSession.getSessionLastEventTime() != null) {
                    long l2 = l - currentSession.getSessionLastEventTime();
                    if (l2 > (long)(ActivityLifecycleTracker.getSessionTimeoutInSeconds() * 1000)) {
                        SessionLogger.logDeactivateApp(this.val$applicationContext, this.val$activityName, currentSession, appId);
                        SessionLogger.logActivateApp(this.val$applicationContext, this.val$activityName, null, appId);
                        currentSession = new SessionInfo(l, null);
                    } else if (l2 > 1000L) {
                        currentSession.incrementInterruptionCount();
                    }
                }
                currentSession.setSessionLastEventTime(l);
                currentSession.writeSessionToDisk();
            }
        };
        singleThreadExecutor.execute((Runnable)object);
    }

    public static void startTracking(Application application, String string) {
        if (!tracking.compareAndSet(false, true)) {
            return;
        }
        appId = string;
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks(){

            public void onActivityCreated(Activity activity, Bundle bundle) {
                Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivityCreated");
                AppEventUtility.assertIsMainThread();
                ActivityLifecycleTracker.onActivityCreated(activity);
            }

            public void onActivityDestroyed(Activity activity) {
                Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivityDestroyed");
            }

            public void onActivityPaused(Activity activity) {
                Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivityPaused");
                AppEventUtility.assertIsMainThread();
                ActivityLifecycleTracker.onActivityPaused(activity);
            }

            public void onActivityResumed(Activity activity) {
                Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivityResumed");
                AppEventUtility.assertIsMainThread();
                ActivityLifecycleTracker.onActivityResumed(activity);
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivitySaveInstanceState");
            }

            public void onActivityStarted(Activity activity) {
                Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivityStarted");
            }

            public void onActivityStopped(Activity activity) {
                Logger.log(LoggingBehavior.APP_EVENTS, TAG, "onActivityStopped");
                AppEventsLogger.onContextStop();
            }
        });
    }

}
