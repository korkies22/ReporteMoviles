/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 */
package com.crashlytics.android.core;

import android.content.Context;
import android.util.Log;
import com.crashlytics.android.answers.AppMeasurementEventLogger;
import com.crashlytics.android.answers.EventLogger;
import com.crashlytics.android.core.AppData;
import com.crashlytics.android.core.AppMeasurementEventListenerRegistrar;
import com.crashlytics.android.core.CrashTest;
import com.crashlytics.android.core.CrashlyticsBackgroundWorker;
import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.CrashlyticsFileMarker;
import com.crashlytics.android.core.CrashlyticsListener;
import com.crashlytics.android.core.CrashlyticsNdkData;
import com.crashlytics.android.core.CrashlyticsNdkDataProvider;
import com.crashlytics.android.core.CrashlyticsPinningInfoProvider;
import com.crashlytics.android.core.DefaultAppMeasurementEventListenerRegistrar;
import com.crashlytics.android.core.ManifestUnityVersionProvider;
import com.crashlytics.android.core.PinningInfoProvider;
import com.crashlytics.android.core.PreferenceManager;
import com.crashlytics.android.core.UnityVersionProvider;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.common.FirebaseInfo;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityCallable;
import io.fabric.sdk.android.services.concurrency.Task;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStore;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.FeaturesSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.HttpsURLConnection;

@DependsOn(value={CrashlyticsNdkDataProvider.class})
public class CrashlyticsCore
extends Kit<Void> {
    static final float CLS_DEFAULT_PROCESS_DELAY = 1.0f;
    static final String CRASHLYTICS_REQUIRE_BUILD_ID = "com.crashlytics.RequireBuildId";
    static final boolean CRASHLYTICS_REQUIRE_BUILD_ID_DEFAULT = true;
    static final String CRASH_MARKER_FILE_NAME = "crash_marker";
    static final int DEFAULT_MAIN_HANDLER_TIMEOUT_SEC = 4;
    private static final String INITIALIZATION_MARKER_FILE_NAME = "initialization_marker";
    static final int MAX_ATTRIBUTES = 64;
    static final int MAX_ATTRIBUTE_SIZE = 1024;
    private static final String MISSING_BUILD_ID_MSG = "This app relies on Crashlytics. Please sign up for access at https://fabric.io/sign_up,\ninstall an Android build tool and ask a team member to invite you to this app's organization.";
    private static final String PREFERENCE_STORE_NAME = "com.crashlytics.android.core.CrashlyticsCore";
    public static final String TAG = "CrashlyticsCore";
    private final ConcurrentHashMap<String, String> attributes;
    private CrashlyticsBackgroundWorker backgroundWorker;
    private CrashlyticsController controller;
    private CrashlyticsFileMarker crashMarker;
    private CrashlyticsNdkDataProvider crashlyticsNdkDataProvider;
    private float delay;
    private boolean disabled;
    private HttpRequestFactory httpRequestFactory;
    private CrashlyticsFileMarker initializationMarker;
    private CrashlyticsListener listener;
    private final PinningInfoProvider pinningInfo;
    private final long startTime;
    private String userEmail = null;
    private String userId = null;
    private String userName = null;

    public CrashlyticsCore() {
        this(1.0f, null, null, false);
    }

    CrashlyticsCore(float f, CrashlyticsListener crashlyticsListener, PinningInfoProvider pinningInfoProvider, boolean bl) {
        this(f, crashlyticsListener, pinningInfoProvider, bl, ExecutorUtils.buildSingleThreadExecutorService("Crashlytics Exception Handler"));
    }

    CrashlyticsCore(float f, CrashlyticsListener crashlyticsListener, PinningInfoProvider pinningInfoProvider, boolean bl, ExecutorService executorService) {
        this.delay = f;
        if (crashlyticsListener == null) {
            crashlyticsListener = new NoOpListener();
        }
        this.listener = crashlyticsListener;
        this.pinningInfo = pinningInfoProvider;
        this.disabled = bl;
        this.backgroundWorker = new CrashlyticsBackgroundWorker(executorService);
        this.attributes = new ConcurrentHashMap();
        this.startTime = System.currentTimeMillis();
    }

    private void checkForPreviousCrash() {
        Boolean bl = (Boolean)this.backgroundWorker.submitAndWait(new CrashMarkerCheck(this.crashMarker));
        if (!Boolean.TRUE.equals(bl)) {
            return;
        }
        try {
            this.listener.crashlyticsDidDetectCrashDuringPreviousExecution();
            return;
        }
        catch (Exception exception) {
            Fabric.getLogger().e(TAG, "Exception thrown by CrashlyticsListener while notifying of previous crash.", exception);
            return;
        }
    }

    private void doLog(int n, String string, String string2) {
        if (this.disabled) {
            return;
        }
        if (!CrashlyticsCore.ensureFabricWithCalled("prior to logging messages.")) {
            return;
        }
        long l = System.currentTimeMillis();
        long l2 = this.startTime;
        this.controller.writeToLog(l - l2, CrashlyticsCore.formatLogMessage(n, string, string2));
    }

    private static boolean ensureFabricWithCalled(String string) {
        Object object = CrashlyticsCore.getInstance();
        if (object != null && object.controller != null) {
            return true;
        }
        object = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Crashlytics must be initialized by calling Fabric.with(Context) ");
        stringBuilder.append(string);
        object.e(TAG, stringBuilder.toString(), null);
        return false;
    }

    private void finishInitSynchronously() {
        Object object = new PriorityCallable<Void>(){

            @Override
            public Void call() throws Exception {
                return CrashlyticsCore.this.doInBackground();
            }

            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };
        Iterator<Task> iterator = this.getDependencies().iterator();
        while (iterator.hasNext()) {
            object.addDependency(iterator.next());
        }
        object = this.getFabric().getExecutorService().submit(object);
        Fabric.getLogger().d(TAG, "Crashlytics detected incomplete initialization on previous app launch. Will initialize synchronously.");
        try {
            object.get(4L, TimeUnit.SECONDS);
            return;
        }
        catch (TimeoutException timeoutException) {
            Fabric.getLogger().e(TAG, "Crashlytics timed out during initialization.", timeoutException);
            return;
        }
        catch (ExecutionException executionException) {
            Fabric.getLogger().e(TAG, "Problem encountered during Crashlytics initialization.", executionException);
            return;
        }
        catch (InterruptedException interruptedException) {
            Fabric.getLogger().e(TAG, "Crashlytics was interrupted during initialization.", interruptedException);
            return;
        }
    }

    private static String formatLogMessage(int n, String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CommonUtils.logPriorityToString(n));
        stringBuilder.append("/");
        stringBuilder.append(string);
        stringBuilder.append(" ");
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    public static CrashlyticsCore getInstance() {
        return Fabric.getKit(CrashlyticsCore.class);
    }

    static boolean isBuildIdValid(String string, boolean bl) {
        if (!bl) {
            Fabric.getLogger().d(TAG, "Configured not to require a build ID.");
            return true;
        }
        if (!CommonUtils.isNullOrEmpty(string)) {
            return true;
        }
        Log.e((String)TAG, (String)".");
        Log.e((String)TAG, (String)".     |  | ");
        Log.e((String)TAG, (String)".     |  |");
        Log.e((String)TAG, (String)".     |  |");
        Log.e((String)TAG, (String)".   \\ |  | /");
        Log.e((String)TAG, (String)".    \\    /");
        Log.e((String)TAG, (String)".     \\  /");
        Log.e((String)TAG, (String)".      \\/");
        Log.e((String)TAG, (String)".");
        Log.e((String)TAG, (String)MISSING_BUILD_ID_MSG);
        Log.e((String)TAG, (String)".");
        Log.e((String)TAG, (String)".      /\\");
        Log.e((String)TAG, (String)".     /  \\");
        Log.e((String)TAG, (String)".    /    \\");
        Log.e((String)TAG, (String)".   / |  | \\");
        Log.e((String)TAG, (String)".     |  |");
        Log.e((String)TAG, (String)".     |  |");
        Log.e((String)TAG, (String)".     |  |");
        Log.e((String)TAG, (String)".");
        return false;
    }

    private static String sanitizeAttribute(String string) {
        String string2 = string;
        if (string != null) {
            string2 = string = string.trim();
            if (string.length() > 1024) {
                string2 = string.substring(0, 1024);
            }
        }
        return string2;
    }

    public void crash() {
        new CrashTest().indexOutOfBounds();
    }

    void createCrashMarker() {
        this.crashMarker.create();
    }

    boolean didPreviousInitializationFail() {
        return this.initializationMarker.isPresent();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected Void doInBackground() {
        Throwable throwable2;
        block11 : {
            this.markInitializationStarted();
            this.controller.cleanInvalidTempFiles();
            try {
                this.controller.registerDevicePowerStateListener();
                SettingsData settingsData = Settings.getInstance().awaitSettingsData();
                if (settingsData == null) {
                    Fabric.getLogger().w(TAG, "Received null settings, skipping report submission!");
                    this.markInitializationComplete();
                    return null;
                }
                this.controller.registerAnalyticsEventListener(settingsData);
                if (!settingsData.featuresData.collectReports) {
                    Fabric.getLogger().d(TAG, "Collection of crash reports disabled in Crashlytics settings.");
                    this.markInitializationComplete();
                    return null;
                }
                CrashlyticsNdkData crashlyticsNdkData = this.getNativeCrashData();
                if (crashlyticsNdkData != null && !this.controller.finalizeNativeReport(crashlyticsNdkData)) {
                    Fabric.getLogger().d(TAG, "Could not finalize previous NDK sessions.");
                }
                if (!this.controller.finalizeSessions(settingsData.sessionData)) {
                    Fabric.getLogger().d(TAG, "Could not finalize previous sessions.");
                }
                this.controller.submitAllReports(this.delay, settingsData);
            }
            catch (Throwable throwable2) {
                break block11;
            }
            catch (Exception exception) {
                Fabric.getLogger().e(TAG, "Crashlytics encountered a problem during asynchronous initialization.", exception);
            }
            this.markInitializationComplete();
            return null;
        }
        this.markInitializationComplete();
        throw throwable2;
    }

    Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    CrashlyticsController getController() {
        return this.controller;
    }

    @Override
    public String getIdentifier() {
        return "com.crashlytics.sdk.android.crashlytics-core";
    }

    CrashlyticsNdkData getNativeCrashData() {
        if (this.crashlyticsNdkDataProvider != null) {
            return this.crashlyticsNdkDataProvider.getCrashlyticsNdkData();
        }
        return null;
    }

    public PinningInfoProvider getPinningInfoProvider() {
        if (!this.disabled) {
            return this.pinningInfo;
        }
        return null;
    }

    String getUserEmail() {
        if (this.getIdManager().canCollectUserIds()) {
            return this.userEmail;
        }
        return null;
    }

    String getUserIdentifier() {
        if (this.getIdManager().canCollectUserIds()) {
            return this.userId;
        }
        return null;
    }

    String getUserName() {
        if (this.getIdManager().canCollectUserIds()) {
            return this.userName;
        }
        return null;
    }

    @Override
    public String getVersion() {
        return "2.6.1.23";
    }

    boolean internalVerifyPinning(URL object) {
        if (this.getPinningInfoProvider() != null) {
            object = this.httpRequestFactory.buildHttpRequest(HttpMethod.GET, object.toString());
            ((HttpsURLConnection)object.getConnection()).setInstanceFollowRedirects(false);
            object.code();
            return true;
        }
        return false;
    }

    public void log(int n, String string, String string2) {
        this.doLog(n, string, string2);
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string);
        string = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(string2);
        logger.log(n, string, stringBuilder.toString(), true);
    }

    public void log(String string) {
        this.doLog(3, TAG, string);
    }

    public void logException(Throwable throwable) {
        if (this.disabled) {
            return;
        }
        if (!CrashlyticsCore.ensureFabricWithCalled("prior to logging exceptions.")) {
            return;
        }
        if (throwable == null) {
            Fabric.getLogger().log(5, TAG, "Crashlytics is ignoring a request to log a null exception.");
            return;
        }
        this.controller.writeNonFatalException(Thread.currentThread(), throwable);
    }

    void markInitializationComplete() {
        this.backgroundWorker.submit(new Callable<Boolean>(){

            @Override
            public Boolean call() throws Exception {
                boolean bl;
                try {
                    bl = CrashlyticsCore.this.initializationMarker.remove();
                    Logger logger = Fabric.getLogger();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Initialization marker file removed: ");
                    stringBuilder.append(bl);
                    logger.d(CrashlyticsCore.TAG, stringBuilder.toString());
                }
                catch (Exception exception) {
                    Fabric.getLogger().e(CrashlyticsCore.TAG, "Problem encountered deleting Crashlytics initialization marker.", exception);
                    return false;
                }
                return bl;
            }
        });
    }

    void markInitializationStarted() {
        this.backgroundWorker.submitAndWait(new Callable<Void>(){

            @Override
            public Void call() throws Exception {
                CrashlyticsCore.this.initializationMarker.create();
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Initialization marker file created.");
                return null;
            }
        });
    }

    @Override
    protected boolean onPreExecute() {
        return this.onPreExecute(super.getContext());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean onPreExecute(Context context) {
        if (this.disabled) {
            return false;
        }
        Object object = new ApiKey().getValue(context);
        if (object == null) {
            return false;
        }
        Object object2 = CommonUtils.resolveBuildId(context);
        if (!CrashlyticsCore.isBuildIdValid((String)object2, CommonUtils.getBooleanResourceValue(context, CRASHLYTICS_REQUIRE_BUILD_ID, true))) {
            throw new UnmetDependencyException(MISSING_BUILD_ID_MSG);
        }
        try {
            Object object3 = Fabric.getLogger();
            Object object4 = new StringBuilder();
            object4.append("Initializing Crashlytics ");
            object4.append(this.getVersion());
            object3.i(TAG, object4.toString());
            object4 = new FileStoreImpl(this);
            this.crashMarker = new CrashlyticsFileMarker(CRASH_MARKER_FILE_NAME, (FileStore)object4);
            this.initializationMarker = new CrashlyticsFileMarker(INITIALIZATION_MARKER_FILE_NAME, (FileStore)object4);
            PreferenceManager preferenceManager = PreferenceManager.create(new PreferenceStoreImpl(this.getContext(), PREFERENCE_STORE_NAME), this);
            object3 = this.pinningInfo != null ? new CrashlyticsPinningInfoProvider(this.pinningInfo) : null;
            this.httpRequestFactory = new DefaultHttpRequestFactory(Fabric.getLogger());
            this.httpRequestFactory.setPinningInfoProvider((io.fabric.sdk.android.services.network.PinningInfoProvider)object3);
            object3 = this.getIdManager();
            object = AppData.create(context, (IdManager)object3, (String)object, (String)object2);
            object2 = new ManifestUnityVersionProvider(context, object.packageName);
            AppMeasurementEventListenerRegistrar appMeasurementEventListenerRegistrar = DefaultAppMeasurementEventListenerRegistrar.instanceFrom(this);
            EventLogger eventLogger = AppMeasurementEventLogger.getEventLogger(context);
            Logger logger = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Installer package name is: ");
            stringBuilder.append(object.installerPackageName);
            logger.d(TAG, stringBuilder.toString());
            this.controller = new CrashlyticsController(this, this.backgroundWorker, this.httpRequestFactory, (IdManager)object3, preferenceManager, (FileStore)object4, (AppData)object, (UnityVersionProvider)object2, appMeasurementEventListenerRegistrar, eventLogger);
            boolean bl = this.didPreviousInitializationFail();
            this.checkForPreviousCrash();
            boolean bl2 = new FirebaseInfo().isFirebaseCrashlyticsEnabled(context);
            this.controller.enableExceptionHandling(Thread.getDefaultUncaughtExceptionHandler(), bl2);
            if (bl && CommonUtils.canTryConnection(context)) {
                Fabric.getLogger().d(TAG, "Crashlytics did not finish previous background initialization. Initializing synchronously.");
                this.finishInitSynchronously();
                return false;
            }
            Fabric.getLogger().d(TAG, "Exception handling initialization successful");
            return true;
        }
        catch (Exception exception) {
            Fabric.getLogger().e(TAG, "Crashlytics was not started due to an exception during initialization", exception);
            this.controller = null;
            return false;
        }
    }

    public void setBool(String string, boolean bl) {
        this.setString(string, Boolean.toString(bl));
    }

    void setCrashlyticsNdkDataProvider(CrashlyticsNdkDataProvider crashlyticsNdkDataProvider) {
        this.crashlyticsNdkDataProvider = crashlyticsNdkDataProvider;
    }

    public void setDouble(String string, double d) {
        this.setString(string, Double.toString(d));
    }

    public void setFloat(String string, float f) {
        this.setString(string, Float.toString(f));
    }

    public void setInt(String string, int n) {
        this.setString(string, Integer.toString(n));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void setListener(CrashlyticsListener crashlyticsListener) {
        synchronized (this) {
            Fabric.getLogger().w(TAG, "Use of setListener is deprecated.");
            if (crashlyticsListener == null) {
                throw new IllegalArgumentException("listener must not be null.");
            }
            this.listener = crashlyticsListener;
            return;
        }
    }

    public void setLong(String string, long l) {
        this.setString(string, Long.toString(l));
    }

    public void setString(String string, String string2) {
        if (this.disabled) {
            return;
        }
        if (!CrashlyticsCore.ensureFabricWithCalled("prior to setting keys.")) {
            return;
        }
        if (string == null) {
            string = this.getContext();
            if (string != null && CommonUtils.isAppDebuggable((Context)string)) {
                throw new IllegalArgumentException("Custom attribute key must not be null.");
            }
            Fabric.getLogger().e(TAG, "Attempting to set custom attribute with null key, ignoring.", null);
            return;
        }
        String string3 = CrashlyticsCore.sanitizeAttribute(string);
        if (this.attributes.size() >= 64 && !this.attributes.containsKey(string3)) {
            Fabric.getLogger().d(TAG, "Exceeded maximum number of custom attributes (64)");
            return;
        }
        string = string2 == null ? "" : CrashlyticsCore.sanitizeAttribute(string2);
        this.attributes.put(string3, string);
        this.controller.cacheKeyData(this.attributes);
    }

    public void setUserEmail(String string) {
        if (this.disabled) {
            return;
        }
        if (!CrashlyticsCore.ensureFabricWithCalled("prior to setting user data.")) {
            return;
        }
        this.userEmail = CrashlyticsCore.sanitizeAttribute(string);
        this.controller.cacheUserData(this.userId, this.userName, this.userEmail);
    }

    public void setUserIdentifier(String string) {
        if (this.disabled) {
            return;
        }
        if (!CrashlyticsCore.ensureFabricWithCalled("prior to setting user data.")) {
            return;
        }
        this.userId = CrashlyticsCore.sanitizeAttribute(string);
        this.controller.cacheUserData(this.userId, this.userName, this.userEmail);
    }

    public void setUserName(String string) {
        if (this.disabled) {
            return;
        }
        if (!CrashlyticsCore.ensureFabricWithCalled("prior to setting user data.")) {
            return;
        }
        this.userName = CrashlyticsCore.sanitizeAttribute(string);
        this.controller.cacheUserData(this.userId, this.userName, this.userEmail);
    }

    public boolean verifyPinning(URL uRL) {
        try {
            boolean bl = this.internalVerifyPinning(uRL);
            return bl;
        }
        catch (Exception exception) {
            Fabric.getLogger().e(TAG, "Could not verify SSL pinning", exception);
            return false;
        }
    }

    public static class Builder {
        private float delay = -1.0f;
        private boolean disabled = false;
        private CrashlyticsListener listener;
        private PinningInfoProvider pinningInfoProvider;

        public CrashlyticsCore build() {
            if (this.delay < 0.0f) {
                this.delay = 1.0f;
            }
            return new CrashlyticsCore(this.delay, this.listener, this.pinningInfoProvider, this.disabled);
        }

        public Builder delay(float f) {
            if (f <= 0.0f) {
                throw new IllegalArgumentException("delay must be greater than 0");
            }
            if (this.delay > 0.0f) {
                throw new IllegalStateException("delay already set.");
            }
            this.delay = f;
            return this;
        }

        public Builder disabled(boolean bl) {
            this.disabled = bl;
            return this;
        }

        public Builder listener(CrashlyticsListener crashlyticsListener) {
            if (crashlyticsListener == null) {
                throw new IllegalArgumentException("listener must not be null.");
            }
            if (this.listener != null) {
                throw new IllegalStateException("listener already set.");
            }
            this.listener = crashlyticsListener;
            return this;
        }

        @Deprecated
        public Builder pinningInfo(PinningInfoProvider pinningInfoProvider) {
            if (pinningInfoProvider == null) {
                throw new IllegalArgumentException("pinningInfoProvider must not be null.");
            }
            if (this.pinningInfoProvider != null) {
                throw new IllegalStateException("pinningInfoProvider already set.");
            }
            this.pinningInfoProvider = pinningInfoProvider;
            return this;
        }
    }

    private static final class CrashMarkerCheck
    implements Callable<Boolean> {
        private final CrashlyticsFileMarker crashMarker;

        public CrashMarkerCheck(CrashlyticsFileMarker crashlyticsFileMarker) {
            this.crashMarker = crashlyticsFileMarker;
        }

        @Override
        public Boolean call() throws Exception {
            if (!this.crashMarker.isPresent()) {
                return Boolean.FALSE;
            }
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Found previous crash marker.");
            this.crashMarker.remove();
            return Boolean.TRUE;
        }
    }

    private static final class NoOpListener
    implements CrashlyticsListener {
        private NoOpListener() {
        }

        @Override
        public void crashlyticsDidDetectCrashDuringPreviousExecution() {
        }
    }

}
