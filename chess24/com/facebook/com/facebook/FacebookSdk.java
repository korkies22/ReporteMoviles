/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.Signature
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.util.Base64
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.AccessTokenManager;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.ProfileManager;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.internal.ActivityLifecycleTracker;
import com.facebook.appevents.internal.AppEventsLoggerUtility;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.BoltsMeasurementEventListener;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.LockOnGetVariable;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;
import org.json.JSONObject;

public final class FacebookSdk {
    public static final String APPLICATION_ID_PROPERTY = "com.facebook.sdk.ApplicationId";
    public static final String APPLICATION_NAME_PROPERTY = "com.facebook.sdk.ApplicationName";
    private static final String ATTRIBUTION_PREFERENCES = "com.facebook.sdk.attributionTracking";
    public static final String AUTO_LOG_APP_EVENTS_ENABLED_PROPERTY = "com.facebook.sdk.AutoLogAppEventsEnabled";
    static final String CALLBACK_OFFSET_CHANGED_AFTER_INIT = "The callback request code offset can't be updated once the SDK is initialized. Call FacebookSdk.setCallbackRequestCodeOffset inside your Application.onCreate method";
    static final String CALLBACK_OFFSET_NEGATIVE = "The callback request code offset can't be negative.";
    public static final String CALLBACK_OFFSET_PROPERTY = "com.facebook.sdk.CallbackOffset";
    public static final String CLIENT_TOKEN_PROPERTY = "com.facebook.sdk.ClientToken";
    private static final int DEFAULT_CALLBACK_REQUEST_CODE_OFFSET = 64206;
    private static final int DEFAULT_CORE_POOL_SIZE = 5;
    private static final int DEFAULT_KEEP_ALIVE = 1;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
    private static final ThreadFactory DEFAULT_THREAD_FACTORY;
    private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE;
    private static final String FACEBOOK_COM = "facebook.com";
    private static final Object LOCK;
    private static final int MAX_REQUEST_CODE_RANGE = 100;
    private static final String PUBLISH_ACTIVITY_PATH = "%s/activities";
    private static final String TAG;
    public static final String WEB_DIALOG_THEME = "com.facebook.sdk.WebDialogTheme";
    private static volatile String appClientToken;
    private static Context applicationContext;
    private static volatile String applicationId;
    private static volatile String applicationName;
    private static volatile Boolean autoLogAppEventsEnabled;
    private static LockOnGetVariable<File> cacheDir;
    private static int callbackRequestCodeOffset = 64206;
    private static Executor executor;
    private static volatile String facebookDomain = "facebook.com";
    private static String graphApiVersion;
    private static volatile boolean isDebugEnabled = false;
    private static boolean isLegacyTokenUpgradeSupported = false;
    private static final HashSet<LoggingBehavior> loggingBehaviors;
    private static AtomicLong onProgressThreshold;
    private static Boolean sdkInitialized;

    static {
        TAG = FacebookSdk.class.getCanonicalName();
        loggingBehaviors = new HashSet<LoggingBehavior>(Arrays.asList(new LoggingBehavior[]{LoggingBehavior.DEVELOPER_ERRORS}));
        onProgressThreshold = new AtomicLong(65536L);
        LOCK = new Object();
        graphApiVersion = ServerProtocol.getDefaultAPIVersion();
        DEFAULT_WORK_QUEUE = new LinkedBlockingQueue<Runnable>(10);
        DEFAULT_THREAD_FACTORY = new ThreadFactory(){
            private final AtomicInteger counter = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable runnable) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("FacebookSdk #");
                stringBuilder.append(this.counter.incrementAndGet());
                return new Thread(runnable, stringBuilder.toString());
            }
        };
        sdkInitialized = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void addLoggingBehavior(LoggingBehavior loggingBehavior) {
        HashSet<LoggingBehavior> hashSet = loggingBehaviors;
        synchronized (hashSet) {
            loggingBehaviors.add(loggingBehavior);
            FacebookSdk.updateGraphDebugBehavior();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void clearLoggingBehaviors() {
        HashSet<LoggingBehavior> hashSet = loggingBehaviors;
        synchronized (hashSet) {
            loggingBehaviors.clear();
            return;
        }
    }

    public static Context getApplicationContext() {
        Validate.sdkInitialized();
        return applicationContext;
    }

    public static String getApplicationId() {
        Validate.sdkInitialized();
        return applicationId;
    }

    public static String getApplicationName() {
        Validate.sdkInitialized();
        return applicationName;
    }

    public static String getApplicationSignature(Context object) {
        block7 : {
            Validate.sdkInitialized();
            if (object == null) {
                return null;
            }
            Object object2 = object.getPackageManager();
            if (object2 == null) {
                return null;
            }
            object = object.getPackageName();
            try {
                object = object2.getPackageInfo((String)object, 64);
                object2 = object.signatures;
                if (object2 == null) break block7;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                return null;
            }
            if (((Signature[])object2).length == 0) {
                return null;
            }
            try {
                object2 = MessageDigest.getInstance("SHA-1");
                object2.update(object.signatures[0].toByteArray());
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                return null;
            }
            return Base64.encodeToString((byte[])object2.digest(), (int)9);
        }
        return null;
    }

    public static boolean getAutoLogAppEventsEnabled() {
        Validate.sdkInitialized();
        return autoLogAppEventsEnabled;
    }

    public static File getCacheDir() {
        Validate.sdkInitialized();
        return cacheDir.getValue();
    }

    public static int getCallbackRequestCodeOffset() {
        Validate.sdkInitialized();
        return callbackRequestCodeOffset;
    }

    public static String getClientToken() {
        Validate.sdkInitialized();
        return appClientToken;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Executor getExecutor() {
        Object object = LOCK;
        synchronized (object) {
            if (executor == null) {
                executor = AsyncTask.THREAD_POOL_EXECUTOR;
            }
            return executor;
        }
    }

    public static String getFacebookDomain() {
        return facebookDomain;
    }

    public static String getGraphApiVersion() {
        return graphApiVersion;
    }

    public static boolean getLimitEventAndDataUsage(Context context) {
        Validate.sdkInitialized();
        return context.getSharedPreferences("com.facebook.sdk.appEventPreferences", 0).getBoolean("limitEventUsage", false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Set<LoggingBehavior> getLoggingBehaviors() {
        HashSet<LoggingBehavior> hashSet = loggingBehaviors;
        synchronized (hashSet) {
            return Collections.unmodifiableSet(new HashSet<LoggingBehavior>(loggingBehaviors));
        }
    }

    public static long getOnProgressThreshold() {
        Validate.sdkInitialized();
        return onProgressThreshold.get();
    }

    public static String getSdkVersion() {
        return "4.31.0";
    }

    public static boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    public static boolean isFacebookRequestCode(int n) {
        if (n >= callbackRequestCodeOffset && n < callbackRequestCodeOffset + 100) {
            return true;
        }
        return false;
    }

    public static boolean isInitialized() {
        synchronized (FacebookSdk.class) {
            boolean bl = sdkInitialized;
            return bl;
        }
    }

    public static boolean isLegacyTokenUpgradeSupported() {
        return isLegacyTokenUpgradeSupported;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean isLoggingBehaviorEnabled(LoggingBehavior loggingBehavior) {
        HashSet<LoggingBehavior> hashSet = loggingBehaviors;
        synchronized (hashSet) {
            if (!FacebookSdk.isDebugEnabled()) return false;
            if (!loggingBehaviors.contains((Object)loggingBehavior)) return false;
            return true;
        }
    }

    static void loadDefaultsFromMetadata(Context context) {
        block11 : {
            block12 : {
                if (context == null) {
                    return;
                }
                try {
                    context = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                    if (context == null) break block11;
                    if (context.metaData == null) {
                        return;
                    }
                    if (applicationId != null) break block12;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    return;
                }
                Object object = context.metaData.get(APPLICATION_ID_PROPERTY);
                if (object instanceof String) {
                    applicationId = (object = (String)object).toLowerCase(Locale.ROOT).startsWith("fb") ? object.substring(2) : object;
                } else if (object instanceof Integer) {
                    throw new FacebookException("App Ids cannot be directly placed in the manifest.They must be prefixed by 'fb' or be placed in the string resource file.");
                }
            }
            if (applicationName == null) {
                applicationName = context.metaData.getString(APPLICATION_NAME_PROPERTY);
            }
            if (appClientToken == null) {
                appClientToken = context.metaData.getString(CLIENT_TOKEN_PROPERTY);
            }
            if (callbackRequestCodeOffset == 64206) {
                callbackRequestCodeOffset = context.metaData.getInt(CALLBACK_OFFSET_PROPERTY, 64206);
            }
            if (autoLogAppEventsEnabled == null) {
                autoLogAppEventsEnabled = context.metaData.getBoolean(AUTO_LOG_APP_EVENTS_ENABLED_PROPERTY, true);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void publishInstallAndWaitForResponse(Context object, String string) {
        if (object == null) throw new IllegalArgumentException("Both context and applicationId must be non-null");
        if (string == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        try {
            AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers((Context)object);
            SharedPreferences sharedPreferences = object.getSharedPreferences(ATTRIBUTION_PREFERENCES, 0);
            CharSequence charSequence = new StringBuilder();
            charSequence.append(string);
            charSequence.append("ping");
            charSequence = charSequence.toString();
            long l = sharedPreferences.getLong((String)charSequence, 0L);
            try {
                object = AppEventsLoggerUtility.getJSONObjectForGraphAPICall(AppEventsLoggerUtility.GraphAPIActivityType.MOBILE_INSTALL_EVENT, attributionIdentifiers, AppEventsLogger.getAnonymousAppDeviceGUID((Context)object), FacebookSdk.getLimitEventAndDataUsage((Context)object), (Context)object);
            }
            catch (JSONException jSONException) {
                throw new FacebookException("An error occurred while publishing install.", (Throwable)jSONException);
            }
            object = GraphRequest.newPostRequest(null, String.format(PUBLISH_ACTIVITY_PATH, string), (JSONObject)object, null);
            if (l != 0L) return;
            object.executeAndWait();
            object = sharedPreferences.edit();
            object.putLong((String)charSequence, System.currentTimeMillis());
            object.apply();
            return;
        }
        catch (Exception exception) {
            Utility.logd("Facebook-publish", exception);
        }
    }

    public static void publishInstallAsync(final Context context, final String string) {
        context = context.getApplicationContext();
        FacebookSdk.getExecutor().execute(new Runnable(){

            @Override
            public void run() {
                FacebookSdk.publishInstallAndWaitForResponse(context, string);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void removeLoggingBehavior(LoggingBehavior loggingBehavior) {
        HashSet<LoggingBehavior> hashSet = loggingBehaviors;
        synchronized (hashSet) {
            loggingBehaviors.remove((Object)loggingBehavior);
            return;
        }
    }

    @Deprecated
    public static void sdkInitialize(Context context) {
        synchronized (FacebookSdk.class) {
            FacebookSdk.sdkInitialize(context, null);
            return;
        }
    }

    @Deprecated
    public static void sdkInitialize(Context context, int n) {
        synchronized (FacebookSdk.class) {
            FacebookSdk.sdkInitialize(context, n, null);
            return;
        }
    }

    @Deprecated
    public static void sdkInitialize(Context context, int n, InitializeCallback initializeCallback) {
        synchronized (FacebookSdk.class) {
            if (sdkInitialized.booleanValue() && n != callbackRequestCodeOffset) {
                throw new FacebookException(CALLBACK_OFFSET_CHANGED_AFTER_INIT);
            }
            if (n < 0) {
                throw new FacebookException(CALLBACK_OFFSET_NEGATIVE);
            }
            callbackRequestCodeOffset = n;
            FacebookSdk.sdkInitialize(context, initializeCallback);
            return;
        }
    }

    @Deprecated
    public static void sdkInitialize(final Context object, final InitializeCallback initializeCallback) {
        synchronized (FacebookSdk.class) {
            block8 : {
                block9 : {
                    if (!sdkInitialized.booleanValue()) break block8;
                    if (initializeCallback == null) break block9;
                    initializeCallback.onInitialized();
                }
                return;
            }
            Validate.notNull(object, "applicationContext");
            Validate.hasFacebookActivity(object, false);
            Validate.hasInternetPermissions(object, false);
            applicationContext = object.getApplicationContext();
            FacebookSdk.loadDefaultsFromMetadata(applicationContext);
            if (Utility.isNullOrEmpty(applicationId)) {
                throw new FacebookException("A valid Facebook app id must be set in the AndroidManifest.xml or set by calling FacebookSdk.setApplicationId before initializing the sdk.");
            }
            if (applicationContext instanceof Application && autoLogAppEventsEnabled.booleanValue()) {
                ActivityLifecycleTracker.startTracking((Application)applicationContext, applicationId);
            }
            sdkInitialized = true;
            FetchedAppSettingsManager.loadAppSettingsAsync();
            NativeProtocol.updateAllAvailableProtocolVersionsAsync();
            BoltsMeasurementEventListener.getInstance(applicationContext);
            cacheDir = new LockOnGetVariable(new Callable<File>(){

                @Override
                public File call() throws Exception {
                    return applicationContext.getCacheDir();
                }
            });
            object = new FutureTask(new Callable<Void>(){

                @Override
                public Void call() throws Exception {
                    AccessTokenManager.getInstance().loadCurrentAccessToken();
                    ProfileManager.getInstance().loadCurrentProfile();
                    if (AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() == null) {
                        Profile.fetchProfileForCurrentAccessToken();
                    }
                    if (initializeCallback != null) {
                        initializeCallback.onInitialized();
                    }
                    AppEventsLogger.initializeLib(applicationContext, applicationId);
                    AppEventsLogger.newLogger(object.getApplicationContext()).flush();
                    return null;
                }
            });
            FacebookSdk.getExecutor().execute((Runnable)object);
            return;
            finally {
            }
        }
    }

    public static void setApplicationId(String string) {
        applicationId = string;
    }

    public static void setApplicationName(String string) {
        applicationName = string;
    }

    public static void setAutoLogAppEventsEnabled(boolean bl) {
        autoLogAppEventsEnabled = bl;
    }

    public static void setCacheDir(File file) {
        cacheDir = new LockOnGetVariable<File>(file);
    }

    public static void setClientToken(String string) {
        appClientToken = string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setExecutor(Executor executor) {
        Validate.notNull(executor, "executor");
        Object object = LOCK;
        synchronized (object) {
            FacebookSdk.executor = executor;
            return;
        }
    }

    public static void setFacebookDomain(String string) {
        Log.w((String)TAG, (String)"WARNING: Calling setFacebookDomain from non-DEBUG code.");
        facebookDomain = string;
    }

    public static void setGraphApiVersion(String string) {
        if (!Utility.isNullOrEmpty(string) && !graphApiVersion.equals(string)) {
            graphApiVersion = string;
        }
    }

    public static void setIsDebugEnabled(boolean bl) {
        isDebugEnabled = bl;
    }

    public static void setLegacyTokenUpgradeSupported(boolean bl) {
        isLegacyTokenUpgradeSupported = bl;
    }

    public static void setLimitEventAndDataUsage(Context context, boolean bl) {
        context.getSharedPreferences("com.facebook.sdk.appEventPreferences", 0).edit().putBoolean("limitEventUsage", bl).apply();
    }

    public static void setOnProgressThreshold(long l) {
        onProgressThreshold.set(l);
    }

    private static void updateGraphDebugBehavior() {
        if (loggingBehaviors.contains((Object)LoggingBehavior.GRAPH_API_DEBUG_INFO) && !loggingBehaviors.contains((Object)LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            loggingBehaviors.add(LoggingBehavior.GRAPH_API_DEBUG_WARNING);
        }
    }

    public static interface InitializeCallback {
        public void onInitialized();
    }

}
