/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.appevents;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import bolts.AppLinks;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AnalyticsUserIDStore;
import com.facebook.appevents.AppEvent;
import com.facebook.appevents.AppEventQueue;
import com.facebook.appevents.FacebookTimeSpentData;
import com.facebook.appevents.FlushReason;
import com.facebook.appevents.internal.ActivityLifecycleTracker;
import com.facebook.appevents.internal.AutomaticAnalyticsLogger;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.BundleJSONConverter;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppEventsLogger {
    public static final String ACTION_APP_EVENTS_FLUSHED = "com.facebook.sdk.APP_EVENTS_FLUSHED";
    public static final String APP_EVENTS_EXTRA_FLUSH_RESULT = "com.facebook.sdk.APP_EVENTS_FLUSH_RESULT";
    public static final String APP_EVENTS_EXTRA_NUM_EVENTS_FLUSHED = "com.facebook.sdk.APP_EVENTS_NUM_EVENTS_FLUSHED";
    private static final String APP_EVENT_NAME_PUSH_OPENED = "fb_mobile_push_opened";
    public static final String APP_EVENT_PREFERENCES = "com.facebook.sdk.appEventPreferences";
    private static final String APP_EVENT_PUSH_PARAMETER_ACTION = "fb_push_action";
    private static final String APP_EVENT_PUSH_PARAMETER_CAMPAIGN = "fb_push_campaign";
    private static final int APP_SUPPORTS_ATTRIBUTION_ID_RECHECK_PERIOD_IN_SECONDS = 86400;
    private static final int FLUSH_APP_SESSION_INFO_IN_SECONDS = 30;
    private static final String PUSH_PAYLOAD_CAMPAIGN_KEY = "campaign";
    private static final String PUSH_PAYLOAD_KEY = "fb_push_payload";
    private static final String SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT = "_fbSourceApplicationHasBeenSet";
    private static final String TAG = AppEventsLogger.class.getCanonicalName();
    private static String anonymousAppDeviceGUID;
    private static ScheduledThreadPoolExecutor backgroundExecutor;
    private static FlushBehavior flushBehavior;
    private static boolean isActivateAppEventRequested;
    private static boolean isOpenedByAppLink;
    private static String pushNotificationsRegistrationId;
    private static String sourceApplication;
    private static Object staticLock;
    private final AccessTokenAppIdPair accessTokenAppId;
    private final String contextName;

    static {
        flushBehavior = FlushBehavior.AUTO;
        staticLock = new Object();
    }

    private AppEventsLogger(Context context, String string, AccessToken accessToken) {
        this(Utility.getActivityName(context), string, accessToken);
    }

    protected AppEventsLogger(String object, String string, AccessToken accessToken) {
        Validate.sdkInitialized();
        this.contextName = object;
        object = accessToken;
        if (accessToken == null) {
            object = AccessToken.getCurrentAccessToken();
        }
        if (object != null && (string == null || string.equals(object.getApplicationId()))) {
            this.accessTokenAppId = new AccessTokenAppIdPair((AccessToken)object);
        } else {
            object = string;
            if (string == null) {
                object = Utility.getMetadataApplicationId(FacebookSdk.getApplicationContext());
            }
            this.accessTokenAppId = new AccessTokenAppIdPair(null, (String)object);
        }
        AppEventsLogger.initializeTimersIfNeeded();
    }

    public static void activateApp(Application application) {
        AppEventsLogger.activateApp(application, null);
    }

    public static void activateApp(Application application, String string) {
        if (!FacebookSdk.isInitialized()) {
            throw new FacebookException("The Facebook sdk must be initialized before calling activateApp");
        }
        AnalyticsUserIDStore.initStore();
        String string2 = string;
        if (string == null) {
            string2 = FacebookSdk.getApplicationId();
        }
        FacebookSdk.publishInstallAsync((Context)application, string2);
        ActivityLifecycleTracker.startTracking(application, string2);
    }

    @Deprecated
    public static void activateApp(Context context) {
        if (ActivityLifecycleTracker.isTracking()) {
            Log.w((String)TAG, (String)"activateApp events are being logged automatically. There's no need to call activateApp explicitly, this is safe to remove.");
            return;
        }
        FacebookSdk.sdkInitialize(context);
        AppEventsLogger.activateApp(context, Utility.getMetadataApplicationId(context));
    }

    @Deprecated
    public static void activateApp(Context object, String string) {
        if (ActivityLifecycleTracker.isTracking()) {
            Log.w((String)TAG, (String)"activateApp events are being logged automatically. There's no need to call activateApp explicitly, this is safe to remove.");
            return;
        }
        if (object != null && string != null) {
            AnalyticsUserIDStore.initStore();
            if (object instanceof Activity) {
                AppEventsLogger.setSourceApplication((Activity)object);
            } else {
                AppEventsLogger.resetSourceApplication();
                Log.d((String)AppEventsLogger.class.getName(), (String)"To set source application the context of activateApp must be an instance of Activity");
            }
            FacebookSdk.publishInstallAsync(object, string);
            object = new AppEventsLogger((Context)object, string, null);
            long l = System.currentTimeMillis();
            string = AppEventsLogger.getSourceApplication();
            backgroundExecutor.execute(new Runnable((AppEventsLogger)object, l, string){
                final /* synthetic */ long val$eventTime;
                final /* synthetic */ AppEventsLogger val$logger;
                final /* synthetic */ String val$sourceApplicationInfo;
                {
                    this.val$logger = appEventsLogger;
                    this.val$eventTime = l;
                    this.val$sourceApplicationInfo = string;
                }

                @Override
                public void run() {
                    this.val$logger.logAppSessionResumeEvent(this.val$eventTime, this.val$sourceApplicationInfo);
                }
            });
            return;
        }
        throw new IllegalArgumentException("Both context and applicationId must be non-null");
    }

    public static void clearUserID() {
        AnalyticsUserIDStore.setUserID(null);
    }

    @Deprecated
    public static void deactivateApp(Context context) {
        if (ActivityLifecycleTracker.isTracking()) {
            Log.w((String)TAG, (String)"deactivateApp events are being logged automatically. There's no need to call deactivateApp, this is safe to remove.");
            return;
        }
        AppEventsLogger.deactivateApp(context, Utility.getMetadataApplicationId(context));
    }

    @Deprecated
    public static void deactivateApp(Context object, String string) {
        if (ActivityLifecycleTracker.isTracking()) {
            Log.w((String)TAG, (String)"deactivateApp events are being logged automatically. There's no need to call deactivateApp, this is safe to remove.");
            return;
        }
        if (object != null && string != null) {
            AppEventsLogger.resetSourceApplication();
            object = new AppEventsLogger((Context)object, string, null);
            long l = System.currentTimeMillis();
            backgroundExecutor.execute(new Runnable((AppEventsLogger)object, l){
                final /* synthetic */ long val$eventTime;
                final /* synthetic */ AppEventsLogger val$logger;
                {
                    this.val$logger = appEventsLogger;
                    this.val$eventTime = l;
                }

                @Override
                public void run() {
                    this.val$logger.logAppSessionSuspendEvent(this.val$eventTime);
                }
            });
            return;
        }
        throw new IllegalArgumentException("Both context and applicationId must be non-null");
    }

    static void eagerFlush() {
        if (AppEventsLogger.getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY) {
            AppEventQueue.flush(FlushReason.EAGER_FLUSHING_EVENT);
        }
    }

    static Executor getAnalyticsExecutor() {
        if (backgroundExecutor == null) {
            AppEventsLogger.initializeTimersIfNeeded();
        }
        return backgroundExecutor;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getAnonymousAppDeviceGUID(Context context) {
        if (anonymousAppDeviceGUID != null) return anonymousAppDeviceGUID;
        Object object = staticLock;
        synchronized (object) {
            if (anonymousAppDeviceGUID != null) return anonymousAppDeviceGUID;
            anonymousAppDeviceGUID = context.getSharedPreferences(APP_EVENT_PREFERENCES, 0).getString("anonymousAppDeviceGUID", null);
            if (anonymousAppDeviceGUID != null) return anonymousAppDeviceGUID;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("XZ");
            stringBuilder.append(UUID.randomUUID().toString());
            anonymousAppDeviceGUID = stringBuilder.toString();
            context.getSharedPreferences(APP_EVENT_PREFERENCES, 0).edit().putString("anonymousAppDeviceGUID", anonymousAppDeviceGUID).apply();
            return anonymousAppDeviceGUID;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static FlushBehavior getFlushBehavior() {
        Object object = staticLock;
        synchronized (object) {
            return flushBehavior;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static String getPushNotificationsRegistrationId() {
        Object object = staticLock;
        synchronized (object) {
            return pushNotificationsRegistrationId;
        }
    }

    static String getSourceApplication() {
        String string = "Unclassified";
        if (isOpenedByAppLink) {
            string = "Applink";
        }
        if (sourceApplication != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("(");
            stringBuilder.append(sourceApplication);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
        return string;
    }

    public static String getUserID() {
        return AnalyticsUserIDStore.getUserID();
    }

    public static void initializeLib(Context object, String string) {
        object = new AppEventsLogger((Context)object, string, null);
        backgroundExecutor.execute(new Runnable((AppEventsLogger)object){
            final /* synthetic */ AppEventsLogger val$logger;
            {
                this.val$logger = appEventsLogger;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Bundle bundle;
                bundle = new Bundle();
                try {
                    Class.forName("com.facebook.core.Core");
                    bundle.putInt("core_lib_included", 1);
                }
                catch (ClassNotFoundException classNotFoundException) {}
                try {
                    Class.forName("com.facebook.login.Login");
                    bundle.putInt("login_lib_included", 1);
                }
                catch (ClassNotFoundException classNotFoundException) {}
                try {
                    Class.forName("com.facebook.share.Share");
                    bundle.putInt("share_lib_included", 1);
                }
                catch (ClassNotFoundException classNotFoundException) {}
                try {
                    Class.forName("com.facebook.places.Places");
                    bundle.putInt("places_lib_included", 1);
                }
                catch (ClassNotFoundException classNotFoundException) {}
                try {
                    Class.forName("com.facebook.messenger.Messenger");
                    bundle.putInt("messenger_lib_included", 1);
                }
                catch (ClassNotFoundException classNotFoundException) {}
                try {
                    Class.forName("com.facebook.applinks.AppLinks");
                    bundle.putInt("applinks_lib_included", 1);
                }
                catch (ClassNotFoundException classNotFoundException) {}
                try {
                    Class.forName("com.facebook.all.All");
                    bundle.putInt("all_lib_included", 1);
                }
                catch (ClassNotFoundException classNotFoundException) {}
                this.val$logger.logSdkEvent("fb_sdk_initialize", null, bundle);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void initializeTimersIfNeeded() {
        Object object = staticLock;
        synchronized (object) {
            if (backgroundExecutor != null) {
                return;
            }
            backgroundExecutor = new ScheduledThreadPoolExecutor(1);
        }
        object = new Runnable(){

            @Override
            public void run() {
                Object object = new HashSet<String>();
                Iterator<AccessTokenAppIdPair> iterator = AppEventQueue.getKeySet().iterator();
                while (iterator.hasNext()) {
                    object.add(iterator.next().getApplicationId());
                }
                object = object.iterator();
                while (object.hasNext()) {
                    FetchedAppSettingsManager.queryAppSettings((String)object.next(), true);
                }
            }
        };
        backgroundExecutor.scheduleAtFixedRate((Runnable)object, 0L, 86400L, TimeUnit.SECONDS);
    }

    private void logAppSessionResumeEvent(long l, String string) {
        PersistedAppSessionInfo.onResume(FacebookSdk.getApplicationContext(), this.accessTokenAppId, this, l, string);
    }

    private void logAppSessionSuspendEvent(long l) {
        PersistedAppSessionInfo.onSuspend(FacebookSdk.getApplicationContext(), this.accessTokenAppId, this, l);
    }

    private static void logEvent(Context context, AppEvent appEvent, AccessTokenAppIdPair accessTokenAppIdPair) {
        AppEventQueue.add(accessTokenAppIdPair, appEvent);
        if (!appEvent.getIsImplicit() && !isActivateAppEventRequested) {
            if (appEvent.getName() == "fb_mobile_activate_app") {
                isActivateAppEventRequested = true;
                return;
            }
            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Warning: Please call AppEventsLogger.activateApp(...)from the long-lived activity's onResume() methodbefore logging other app events.");
        }
    }

    private void logEvent(String object, Double d, Bundle bundle, boolean bl, @Nullable UUID uUID) {
        try {
            object = new AppEvent(this.contextName, (String)object, d, bundle, bl, uUID);
            AppEventsLogger.logEvent(FacebookSdk.getApplicationContext(), (AppEvent)object, this.accessTokenAppId);
            return;
        }
        catch (FacebookException facebookException) {
            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Invalid app event: %s", facebookException.toString());
            return;
        }
        catch (JSONException jSONException) {
            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "JSON encoding for app event failed: '%s'", jSONException.toString());
            return;
        }
    }

    private void logPurchase(BigDecimal bigDecimal, Currency currency, Bundle bundle, boolean bl) {
        if (bigDecimal == null) {
            AppEventsLogger.notifyDeveloperError("purchaseAmount cannot be null");
            return;
        }
        if (currency == null) {
            AppEventsLogger.notifyDeveloperError("currency cannot be null");
            return;
        }
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = new Bundle();
        }
        bundle2.putString("fb_currency", currency.getCurrencyCode());
        this.logEvent("fb_mobile_purchase", bigDecimal.doubleValue(), bundle2, bl, ActivityLifecycleTracker.getCurrentSessionGuid());
        AppEventsLogger.eagerFlush();
    }

    public static AppEventsLogger newLogger(Context context) {
        return new AppEventsLogger(context, null, null);
    }

    public static AppEventsLogger newLogger(Context context, AccessToken accessToken) {
        return new AppEventsLogger(context, null, accessToken);
    }

    public static AppEventsLogger newLogger(Context context, String string) {
        return new AppEventsLogger(context, string, null);
    }

    public static AppEventsLogger newLogger(Context context, String string, AccessToken accessToken) {
        return new AppEventsLogger(context, string, accessToken);
    }

    private static void notifyDeveloperError(String string) {
        Logger.log(LoggingBehavior.DEVELOPER_ERRORS, "AppEvents", string);
    }

    public static void onContextStop() {
        AppEventQueue.persistToDisk();
    }

    static void resetSourceApplication() {
        sourceApplication = null;
        isOpenedByAppLink = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setFlushBehavior(FlushBehavior flushBehavior) {
        Object object = staticLock;
        synchronized (object) {
            AppEventsLogger.flushBehavior = flushBehavior;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setPushNotificationsRegistrationId(String object) {
        Object object2 = staticLock;
        synchronized (object2) {
            if (!Utility.stringsEqualOrEmpty(pushNotificationsRegistrationId, (String)object)) {
                pushNotificationsRegistrationId = object;
                object = AppEventsLogger.newLogger(FacebookSdk.getApplicationContext());
                object.logEvent("fb_mobile_obtain_push_token");
                if (AppEventsLogger.getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY) {
                    object.flush();
                }
            }
            return;
        }
    }

    private static void setSourceApplication(Activity activity) {
        Object object = activity.getCallingActivity();
        if (object != null) {
            if ((object = object.getPackageName()).equals(activity.getPackageName())) {
                AppEventsLogger.resetSourceApplication();
                return;
            }
            sourceApplication = object;
        }
        if ((activity = activity.getIntent()) != null && !activity.getBooleanExtra(SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, false)) {
            object = AppLinks.getAppLinkData((Intent)activity);
            if (object == null) {
                AppEventsLogger.resetSourceApplication();
                return;
            }
            isOpenedByAppLink = true;
            if ((object = object.getBundle("referer_app_link")) == null) {
                sourceApplication = null;
                return;
            }
            sourceApplication = object.getString("package");
            activity.putExtra(SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, true);
            return;
        }
        AppEventsLogger.resetSourceApplication();
    }

    static void setSourceApplication(String string, boolean bl) {
        sourceApplication = string;
        isOpenedByAppLink = bl;
    }

    public static void setUserID(String string) {
        AnalyticsUserIDStore.setUserID(string);
    }

    public static void updateUserProperties(Bundle bundle, GraphRequest.Callback callback) {
        AppEventsLogger.updateUserProperties(bundle, FacebookSdk.getApplicationId(), callback);
    }

    public static void updateUserProperties(final Bundle bundle, final String string, final GraphRequest.Callback callback) {
        final String string2 = AppEventsLogger.getUserID();
        if (string2 != null && !string2.isEmpty()) {
            AppEventsLogger.getAnalyticsExecutor().execute(new Runnable(){

                @Override
                public void run() {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("user_unique_id", string2);
                    bundle2.putBundle("custom_data", bundle);
                    Object object = AttributionIdentifiers.getAttributionIdentifiers(FacebookSdk.getApplicationContext());
                    if (object != null && object.getAndroidAdvertiserId() != null) {
                        bundle2.putString("advertiser_id", object.getAndroidAdvertiserId());
                    }
                    object = new Bundle();
                    try {
                        bundle2 = BundleJSONConverter.convertToJSON(bundle2);
                        JSONArray jSONArray = new JSONArray();
                        jSONArray.put((Object)bundle2);
                        object.putString("data", jSONArray.toString());
                    }
                    catch (JSONException jSONException) {
                        throw new FacebookException("Failed to construct request", (Throwable)jSONException);
                    }
                    object = new GraphRequest(AccessToken.getCurrentAccessToken(), String.format(Locale.US, "%s/user_properties", string), (Bundle)object, HttpMethod.POST, callback);
                    object.setSkipClientToken(true);
                    object.executeAsync();
                }
            });
            return;
        }
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "AppEventsLogger userID cannot be null or empty");
    }

    public void flush() {
        AppEventQueue.flush(FlushReason.EXPLICIT);
    }

    public String getApplicationId() {
        return this.accessTokenAppId.getApplicationId();
    }

    public boolean isValidForAccessToken(AccessToken object) {
        object = new AccessTokenAppIdPair((AccessToken)object);
        return this.accessTokenAppId.equals(object);
    }

    public void logEvent(String string) {
        this.logEvent(string, null);
    }

    public void logEvent(String string, double d) {
        this.logEvent(string, d, null);
    }

    public void logEvent(String string, double d, Bundle bundle) {
        this.logEvent(string, d, bundle, false, ActivityLifecycleTracker.getCurrentSessionGuid());
    }

    public void logEvent(String string, Bundle bundle) {
        this.logEvent(string, null, bundle, false, ActivityLifecycleTracker.getCurrentSessionGuid());
    }

    public void logPurchase(BigDecimal bigDecimal, Currency currency) {
        if (AutomaticAnalyticsLogger.isImplicitPurchaseLoggingEnabled()) {
            Log.w((String)TAG, (String)"You are logging purchase events while auto-logging of in-app purchase is enabled in the SDK. Make sure you don't log duplicate events");
        }
        this.logPurchase(bigDecimal, currency, null, false);
    }

    public void logPurchase(BigDecimal bigDecimal, Currency currency, Bundle bundle) {
        if (AutomaticAnalyticsLogger.isImplicitPurchaseLoggingEnabled()) {
            Log.w((String)TAG, (String)"You are logging purchase events while auto-logging of in-app purchase is enabled in the SDK. Make sure you don't log duplicate events");
        }
        this.logPurchase(bigDecimal, currency, bundle, false);
    }

    public void logPurchaseImplicitly(BigDecimal bigDecimal, Currency currency, Bundle bundle) {
        this.logPurchase(bigDecimal, currency, bundle, true);
    }

    public void logPushNotificationOpen(Bundle bundle) {
        this.logPushNotificationOpen(bundle, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void logPushNotificationOpen(Bundle object, String string) {
        void var2_7;
        void var1_6;
        block5 : {
            try {
                String string2 = object.getString(PUSH_PAYLOAD_KEY);
                if (Utility.isNullOrEmpty(string2)) {
                    return;
                }
                String string3 = new JSONObject(string2).getString(PUSH_PAYLOAD_CAMPAIGN_KEY);
                break block5;
            }
            catch (JSONException jSONException) {}
            Object var1_5 = null;
        }
        if (var1_6 == null) {
            Logger.log(LoggingBehavior.DEVELOPER_ERRORS, TAG, "Malformed payload specified for logging a push notification open.");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(APP_EVENT_PUSH_PARAMETER_CAMPAIGN, (String)var1_6);
        if (var2_7 != null) {
            bundle.putString(APP_EVENT_PUSH_PARAMETER_ACTION, (String)var2_7);
        }
        this.logEvent(APP_EVENT_NAME_PUSH_OPENED, bundle);
    }

    public void logSdkEvent(String string, Double d, Bundle bundle) {
        this.logEvent(string, d, bundle, true, ActivityLifecycleTracker.getCurrentSessionGuid());
    }

    public static enum FlushBehavior {
        AUTO,
        EXPLICIT_ONLY;
        

        private FlushBehavior() {
        }
    }

    static class PersistedAppSessionInfo {
        private static final String PERSISTED_SESSION_INFO_FILENAME = "AppEventsLogger.persistedsessioninfo";
        private static final Runnable appSessionInfoFlushRunnable;
        private static Map<AccessTokenAppIdPair, FacebookTimeSpentData> appSessionInfoMap;
        private static boolean hasChanges = false;
        private static boolean isLoaded = false;
        private static final Object staticLock;

        static {
            staticLock = new Object();
            appSessionInfoFlushRunnable = new Runnable(){

                @Override
                public void run() {
                    PersistedAppSessionInfo.saveAppSessionInformation(FacebookSdk.getApplicationContext());
                }
            };
        }

        PersistedAppSessionInfo() {
        }

        private static FacebookTimeSpentData getTimeSpentData(Context object, AccessTokenAppIdPair accessTokenAppIdPair) {
            PersistedAppSessionInfo.restoreAppSessionInformation(object);
            FacebookTimeSpentData facebookTimeSpentData = appSessionInfoMap.get(accessTokenAppIdPair);
            object = facebookTimeSpentData;
            if (facebookTimeSpentData == null) {
                object = new FacebookTimeSpentData();
                appSessionInfoMap.put(accessTokenAppIdPair, (FacebookTimeSpentData)object);
            }
            return object;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static void onResume(Context context, AccessTokenAppIdPair accessTokenAppIdPair, AppEventsLogger appEventsLogger, long l, String string) {
            Object object = staticLock;
            synchronized (object) {
                PersistedAppSessionInfo.getTimeSpentData(context, accessTokenAppIdPair).onResume(appEventsLogger, l, string);
                PersistedAppSessionInfo.onTimeSpentDataUpdate();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static void onSuspend(Context context, AccessTokenAppIdPair accessTokenAppIdPair, AppEventsLogger appEventsLogger, long l) {
            Object object = staticLock;
            synchronized (object) {
                PersistedAppSessionInfo.getTimeSpentData(context, accessTokenAppIdPair).onSuspend(appEventsLogger, l);
                PersistedAppSessionInfo.onTimeSpentDataUpdate();
                return;
            }
        }

        private static void onTimeSpentDataUpdate() {
            if (!hasChanges) {
                hasChanges = true;
                backgroundExecutor.schedule(appSessionInfoFlushRunnable, 30L, TimeUnit.SECONDS);
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        private static void restoreAppSessionInformation(Context context) {
            block22 : {
                ObjectInputStream objectInputStream;
                block21 : {
                    block20 : {
                        ObjectInputStream objectInputStream2;
                        void var3_5;
                        block19 : {
                            block18 : {
                                Object object = staticLock;
                                // MONITORENTER : object
                                boolean bl = isLoaded;
                                if (bl) {
                                    // MONITOREXIT : object
                                    return;
                                }
                                objectInputStream2 = objectInputStream = new ObjectInputStream(context.openFileInput(PERSISTED_SESSION_INFO_FILENAME));
                                try {
                                    appSessionInfoMap = (HashMap)objectInputStream.readObject();
                                    objectInputStream2 = objectInputStream;
                                    Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "App session info loaded");
                                }
                                catch (Exception exception) {
                                    break block18;
                                }
                                Utility.closeQuietly(objectInputStream);
                                context.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                                if (appSessionInfoMap == null) {
                                    appSessionInfoMap = new HashMap<AccessTokenAppIdPair, FacebookTimeSpentData>();
                                }
                                isLoaded = true;
                                break block22;
                                catch (Throwable throwable) {
                                    objectInputStream2 = null;
                                    break block19;
                                }
                                catch (Exception exception) {
                                    objectInputStream = null;
                                }
                            }
                            objectInputStream2 = objectInputStream;
                            try {
                                void var4_12;
                                String string = TAG;
                                objectInputStream2 = objectInputStream;
                                StringBuilder stringBuilder = new StringBuilder();
                                objectInputStream2 = objectInputStream;
                                stringBuilder.append("Got unexpected exception restoring app session info: ");
                                objectInputStream2 = objectInputStream;
                                stringBuilder.append(var4_12.toString());
                                objectInputStream2 = objectInputStream;
                                Log.w((String)string, (String)stringBuilder.toString());
                            }
                            catch (Throwable throwable) {}
                            Utility.closeQuietly(objectInputStream);
                            context.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                            if (appSessionInfoMap == null) {
                                appSessionInfoMap = new HashMap<AccessTokenAppIdPair, FacebookTimeSpentData>();
                            }
                            isLoaded = true;
                            break block22;
                            catch (FileNotFoundException fileNotFoundException) {
                                break block20;
                            }
                            catch (FileNotFoundException fileNotFoundException) {
                                break block21;
                            }
                        }
                        Utility.closeQuietly(objectInputStream2);
                        context.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                        if (appSessionInfoMap == null) {
                            appSessionInfoMap = new HashMap<AccessTokenAppIdPair, FacebookTimeSpentData>();
                        }
                        isLoaded = true;
                        hasChanges = false;
                        throw var3_5;
                    }
                    objectInputStream = null;
                }
                Utility.closeQuietly(objectInputStream);
                context.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                if (appSessionInfoMap == null) {
                    appSessionInfoMap = new HashMap<AccessTokenAppIdPair, FacebookTimeSpentData>();
                }
                isLoaded = true;
            }
            hasChanges = false;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        static void saveAppSessionInformation(Context object) {
            Object object3;
            block12 : {
                String string;
                block13 : {
                    Object object2 = staticLock;
                    // MONITORENTER : object2
                    boolean bl = hasChanges;
                    if (!bl) {
                        // MONITOREXIT : object2
                        return;
                    }
                    string = null;
                    object3 = null;
                    object = new ObjectOutputStream(new BufferedOutputStream(object.openFileOutput(PERSISTED_SESSION_INFO_FILENAME, 0)));
                    try {
                        object.writeObject(appSessionInfoMap);
                        hasChanges = false;
                        Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "App session info saved");
                    }
                    catch (Throwable throwable) {
                        object3 = object;
                        object = throwable;
                        break block12;
                    }
                    catch (Exception exception) {
                        break block13;
                    }
                    Utility.closeQuietly((Closeable)object);
                    return;
                    catch (Throwable throwable) {
                        break block12;
                    }
                    catch (Exception exception) {
                        object = string;
                    }
                }
                object3 = object;
                {
                    void var3_9;
                    string = TAG;
                    object3 = object;
                    StringBuilder stringBuilder = new StringBuilder();
                    object3 = object;
                    stringBuilder.append("Got unexpected exception while writing app session info: ");
                    object3 = object;
                    stringBuilder.append(var3_9.toString());
                    object3 = object;
                    Log.w((String)string, (String)stringBuilder.toString());
                }
                Utility.closeQuietly((Closeable)object);
                return;
            }
            Utility.closeQuietly(object3);
            throw object;
        }

    }

}
