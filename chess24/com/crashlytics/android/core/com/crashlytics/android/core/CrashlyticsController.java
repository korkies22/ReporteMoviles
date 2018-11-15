/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.StatFs
 *  android.text.TextUtils
 *  org.json.JSONObject
 */
package com.crashlytics.android.core;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.EventLogger;
import com.crashlytics.android.core.AppData;
import com.crashlytics.android.core.AppMeasurementEventListenerRegistrar;
import com.crashlytics.android.core.CLSUUID;
import com.crashlytics.android.core.ClsFileOutputStream;
import com.crashlytics.android.core.CodedOutputStream;
import com.crashlytics.android.core.CompositeCreateReportSpiCall;
import com.crashlytics.android.core.CrashPromptDialog;
import com.crashlytics.android.core.CrashlyticsBackgroundWorker;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsNdkData;
import com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler;
import com.crashlytics.android.core.CreateReportSpiCall;
import com.crashlytics.android.core.DefaultCreateReportSpiCall;
import com.crashlytics.android.core.DevicePowerStateListener;
import com.crashlytics.android.core.LogFileManager;
import com.crashlytics.android.core.MetaDataStore;
import com.crashlytics.android.core.MiddleOutFallbackStrategy;
import com.crashlytics.android.core.NativeCreateReportSpiCall;
import com.crashlytics.android.core.NativeFileUtils;
import com.crashlytics.android.core.PreferenceManager;
import com.crashlytics.android.core.RemoveRepeatsStrategy;
import com.crashlytics.android.core.Report;
import com.crashlytics.android.core.ReportUploader;
import com.crashlytics.android.core.SessionProtobufHelper;
import com.crashlytics.android.core.SessionReport;
import com.crashlytics.android.core.StackTraceTrimmingStrategy;
import com.crashlytics.android.core.TrimmedThrowableData;
import com.crashlytics.android.core.UnityVersionProvider;
import com.crashlytics.android.core.UserMetaData;
import com.crashlytics.android.core.Utils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStore;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import io.fabric.sdk.android.services.settings.FeaturesSettingsData;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import org.json.JSONObject;

class CrashlyticsController {
    private static final int ANALYZER_VERSION = 1;
    private static final String COLLECT_CUSTOM_KEYS = "com.crashlytics.CollectCustomKeys";
    private static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
    private static final String EVENT_TYPE_CRASH = "crash";
    private static final String EVENT_TYPE_LOGGED = "error";
    static final String FATAL_SESSION_DIR = "fatal-sessions";
    static final String FIREBASE_ANALYTICS_ORIGIN_CRASHLYTICS = "clx";
    static final String FIREBASE_APPLICATION_EXCEPTION = "_ae";
    static final String FIREBASE_CRASH_TYPE = "fatal";
    static final int FIREBASE_CRASH_TYPE_FATAL = 1;
    static final String FIREBASE_REALTIME = "_r";
    static final String FIREBASE_TIMESTAMP = "timestamp";
    private static final String GENERATOR_FORMAT = "Crashlytics Android SDK/%s";
    private static final String[] INITIAL_SESSION_PART_TAGS;
    static final String INVALID_CLS_CACHE_DIR = "invalidClsFiles";
    static final Comparator<File> LARGEST_FILE_NAME_FIRST;
    static final int MAX_INVALID_SESSIONS = 4;
    private static final int MAX_LOCAL_LOGGED_EXCEPTIONS = 64;
    static final int MAX_OPEN_SESSIONS = 8;
    static final int MAX_STACK_SIZE = 1024;
    static final String NONFATAL_SESSION_DIR = "nonfatal-sessions";
    static final int NUM_STACK_REPETITIONS_ALLOWED = 10;
    private static final Map<String, String> SEND_AT_CRASHTIME_HEADER;
    static final String SESSION_APP_TAG = "SessionApp";
    static final FilenameFilter SESSION_BEGIN_FILE_FILTER;
    static final String SESSION_BEGIN_TAG = "BeginSession";
    static final String SESSION_DEVICE_TAG = "SessionDevice";
    static final FileFilter SESSION_DIRECTORY_FILTER;
    static final String SESSION_EVENT_MISSING_BINARY_IMGS_TAG = "SessionMissingBinaryImages";
    static final String SESSION_FATAL_TAG = "SessionCrash";
    static final FilenameFilter SESSION_FILE_FILTER;
    private static final Pattern SESSION_FILE_PATTERN;
    private static final int SESSION_ID_LENGTH = 35;
    static final String SESSION_JSON_SUFFIX = ".json";
    static final String SESSION_NON_FATAL_TAG = "SessionEvent";
    static final String SESSION_OS_TAG = "SessionOS";
    static final String SESSION_USER_TAG = "SessionUser";
    private static final boolean SHOULD_PROMPT_BEFORE_SENDING_REPORTS_DEFAULT = false;
    static final Comparator<File> SMALLEST_FILE_NAME_FIRST;
    private final AppData appData;
    private final AppMeasurementEventListenerRegistrar appMeasurementEventListenerRegistrar;
    private final CrashlyticsBackgroundWorker backgroundWorker;
    private CrashlyticsUncaughtExceptionHandler crashHandler;
    private final CrashlyticsCore crashlyticsCore;
    private final DevicePowerStateListener devicePowerStateListener;
    private final AtomicInteger eventCounter = new AtomicInteger(0);
    private final FileStore fileStore;
    private final EventLogger firebaseAnalyticsLogger;
    private final ReportUploader.HandlingExceptionCheck handlingExceptionCheck;
    private final HttpRequestFactory httpRequestFactory;
    private final IdManager idManager;
    private final LogFileDirectoryProvider logFileDirectoryProvider;
    private final LogFileManager logFileManager;
    private final PreferenceManager preferenceManager;
    private final ReportUploader.ReportFilesProvider reportFilesProvider;
    private final StackTraceTrimmingStrategy stackTraceTrimmingStrategy;
    private final String unityVersion;

    static {
        SESSION_BEGIN_FILE_FILTER = new FileNameContainsFilter(SESSION_BEGIN_TAG){

            @Override
            public boolean accept(File file, String string) {
                if (super.accept(file, string) && string.endsWith(".cls")) {
                    return true;
                }
                return false;
            }
        };
        SESSION_FILE_FILTER = new FilenameFilter(){

            @Override
            public boolean accept(File file, String string) {
                if (string.length() == 35 + ".cls".length() && string.endsWith(".cls")) {
                    return true;
                }
                return false;
            }
        };
        SESSION_DIRECTORY_FILTER = new FileFilter(){

            @Override
            public boolean accept(File file) {
                if (file.isDirectory() && file.getName().length() == 35) {
                    return true;
                }
                return false;
            }
        };
        LARGEST_FILE_NAME_FIRST = new Comparator<File>(){

            @Override
            public int compare(File file, File file2) {
                return file2.getName().compareTo(file.getName());
            }
        };
        SMALLEST_FILE_NAME_FIRST = new Comparator<File>(){

            @Override
            public int compare(File file, File file2) {
                return file.getName().compareTo(file2.getName());
            }
        };
        SESSION_FILE_PATTERN = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
        SEND_AT_CRASHTIME_HEADER = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", "1");
        INITIAL_SESSION_PART_TAGS = new String[]{SESSION_USER_TAG, SESSION_APP_TAG, SESSION_OS_TAG, SESSION_DEVICE_TAG};
    }

    CrashlyticsController(CrashlyticsCore crashlyticsCore, CrashlyticsBackgroundWorker crashlyticsBackgroundWorker, HttpRequestFactory httpRequestFactory, IdManager idManager, PreferenceManager preferenceManager, FileStore fileStore, AppData appData, UnityVersionProvider unityVersionProvider, AppMeasurementEventListenerRegistrar appMeasurementEventListenerRegistrar, EventLogger eventLogger) {
        this.crashlyticsCore = crashlyticsCore;
        this.backgroundWorker = crashlyticsBackgroundWorker;
        this.httpRequestFactory = httpRequestFactory;
        this.idManager = idManager;
        this.preferenceManager = preferenceManager;
        this.fileStore = fileStore;
        this.appData = appData;
        this.unityVersion = unityVersionProvider.getUnityVersion();
        this.appMeasurementEventListenerRegistrar = appMeasurementEventListenerRegistrar;
        this.firebaseAnalyticsLogger = eventLogger;
        crashlyticsCore = crashlyticsCore.getContext();
        this.logFileDirectoryProvider = new LogFileDirectoryProvider(fileStore);
        this.logFileManager = new LogFileManager((Context)crashlyticsCore, this.logFileDirectoryProvider);
        this.reportFilesProvider = new ReportUploaderFilesProvider();
        this.handlingExceptionCheck = new ReportUploaderHandlingExceptionCheck();
        this.devicePowerStateListener = new DevicePowerStateListener((Context)crashlyticsCore);
        this.stackTraceTrimmingStrategy = new MiddleOutFallbackStrategy(1024, new RemoveRepeatsStrategy(10));
    }

    static /* synthetic */ AppData access$1900(CrashlyticsController crashlyticsController) {
        return crashlyticsController.appData;
    }

    private void closeOpenSessions(File[] arrfile, int n, int n2) {
        Fabric.getLogger().d("CrashlyticsCore", "Closing open sessions.");
        while (n < arrfile.length) {
            File file = arrfile[n];
            String string = CrashlyticsController.getSessionIdFromSessionFile(file);
            Logger logger = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Closing session: ");
            stringBuilder.append(string);
            logger.d("CrashlyticsCore", stringBuilder.toString());
            this.writeSessionPartsToSessionFile(file, string, n2);
            ++n;
        }
    }

    private void closeWithoutRenamingOrLog(ClsFileOutputStream clsFileOutputStream) {
        if (clsFileOutputStream == null) {
            return;
        }
        try {
            clsFileOutputStream.closeInProgressStream();
            return;
        }
        catch (IOException iOException) {
            Fabric.getLogger().e("CrashlyticsCore", "Error closing session file stream in the presence of an exception", iOException);
            return;
        }
    }

    private static void copyToCodedOutputStream(InputStream inputStream, CodedOutputStream codedOutputStream, int n) throws IOException {
        int n2;
        byte[] arrby = new byte[n];
        for (n = 0; n < arrby.length && (n2 = inputStream.read(arrby, n, arrby.length - n)) >= 0; n += n2) {
        }
        codedOutputStream.writeRawBytes(arrby);
    }

    private void deleteSessionPartFilesFor(String arrfile) {
        arrfile = this.listSessionPartFilesFor((String)arrfile);
        int n = arrfile.length;
        for (int i = 0; i < n; ++i) {
            arrfile[i].delete();
        }
    }

    private void doCloseSessions(SessionSettingsData sessionSettingsData, boolean bl) throws Exception {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    private void doOpenSession() throws Exception {
        Date date = new Date();
        String string = new CLSUUID(this.idManager).toString();
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Opening a new session with ID ");
        stringBuilder.append(string);
        logger.d("CrashlyticsCore", stringBuilder.toString());
        this.writeBeginSession(string, date);
        this.writeSessionApp(string);
        this.writeSessionOS(string);
        this.writeSessionDevice(string);
        this.logFileManager.setCurrentSession(string);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void doWriteNonFatal(Date object, Thread thread, Throwable throwable) {
        Object object2;
        Object object3;
        void var1_6;
        block13 : {
            String string;
            Object object4;
            block15 : {
                void var2_11;
                block14 : {
                    string = this.getCurrentSessionId();
                    object4 = null;
                    object3 = null;
                    Object var8_16 = null;
                    if (string == null) {
                        Fabric.getLogger().e("CrashlyticsCore", "Tried to write a non-fatal exception while no session was open.", null);
                        return;
                    }
                    CrashlyticsController.recordLoggedExceptionAnswersEvent(string, throwable.getClass().getName());
                    object2 = Fabric.getLogger();
                    Object object5 = new StringBuilder();
                    object5.append("Crashlytics is logging non-fatal exception \"");
                    object5.append(throwable);
                    object5.append("\" from thread ");
                    object5.append(thread.getName());
                    object2.d("CrashlyticsCore", object5.toString());
                    object2 = CommonUtils.padWithZerosToMaxIntWidth(this.eventCounter.getAndIncrement());
                    object5 = new StringBuilder();
                    object5.append(string);
                    object5.append(SESSION_NON_FATAL_TAG);
                    object5.append((String)object2);
                    object2 = object5.toString();
                    object2 = new ClsFileOutputStream(this.getFilesDir(), (String)object2);
                    object3 = object4;
                    object4 = object2;
                    object5 = CodedOutputStream.newInstance((OutputStream)object2);
                    try {
                        this.writeSessionEvent((CodedOutputStream)object5, (Date)object, thread, throwable, EVENT_TYPE_LOGGED, false);
                    }
                    catch (Throwable throwable2) {
                        object3 = object5;
                        break block13;
                    }
                    catch (Exception exception) {
                        object = object5;
                        break block14;
                    }
                    CommonUtils.flushOrLog((Flushable)object5, "Failed to flush to non-fatal file.");
                    break block15;
                    catch (Exception exception) {
                        object = var8_16;
                        break block14;
                    }
                    catch (Throwable throwable3) {
                        object2 = null;
                        break block13;
                    }
                    catch (Exception exception) {
                        object2 = null;
                        object = var8_16;
                    }
                }
                object3 = object;
                object4 = object2;
                Fabric.getLogger().e("CrashlyticsCore", "An error occurred in the non-fatal exception logger", (Throwable)var2_11);
                CommonUtils.flushOrLog((Flushable)object, "Failed to flush to non-fatal file.");
            }
            CommonUtils.closeOrLog((Closeable)object2, "Failed to close non-fatal file output stream.");
            try {
                this.trimSessionEventFiles(string, 64);
                return;
            }
            catch (Exception exception) {
                Fabric.getLogger().e("CrashlyticsCore", "An error occurred when trimming non-fatal files.", exception);
                return;
            }
            catch (Throwable throwable4) {
                object2 = object4;
            }
        }
        CommonUtils.flushOrLog(object3, "Failed to flush to non-fatal file.");
        CommonUtils.closeOrLog((Closeable)object2, "Failed to close non-fatal file output stream.");
        throw var1_6;
    }

    private File[] ensureFileArrayNotNull(File[] arrfile) {
        File[] arrfile2 = arrfile;
        if (arrfile == null) {
            arrfile2 = new File[]{};
        }
        return arrfile2;
    }

    private void finalizeMostRecentNativeCrash(Context object, File arrby, String object2) throws IOException {
        byte[] arrby2 = NativeFileUtils.minidumpFromDirectory((File)arrby);
        byte[] arrby3 = NativeFileUtils.metadataJsonFromDirectory((File)arrby);
        object = NativeFileUtils.binaryImagesJsonFromDirectory((File)arrby, (Context)object);
        if (arrby2 != null && arrby2.length != 0) {
            CrashlyticsController.recordFatalExceptionAnswersEvent((String)object2, "<native-crash: minidump>");
            arrby = this.readFile((String)object2, "BeginSession.json");
            byte[] arrby4 = this.readFile((String)object2, "SessionApp.json");
            byte[] arrby5 = this.readFile((String)object2, "SessionDevice.json");
            byte[] arrby6 = this.readFile((String)object2, "SessionOS.json");
            byte[] arrby7 = NativeFileUtils.readFile(new MetaDataStore(this.getFilesDir()).getUserDataFileForSession((String)object2));
            byte[] arrby8 = new byte[](this.crashlyticsCore.getContext(), this.logFileDirectoryProvider, (String)object2);
            byte[] arrby9 = arrby8.getBytesForLog();
            arrby8.clearLog();
            arrby8 = NativeFileUtils.readFile(new MetaDataStore(this.getFilesDir()).getKeysFileForSession((String)object2));
            object2 = new File(this.fileStore.getFilesDir(), (String)object2);
            if (!object2.mkdir()) {
                Fabric.getLogger().d("CrashlyticsCore", "Couldn't create native sessions directory");
                return;
            }
            this.gzipIfNotEmpty(arrby2, new File((File)object2, "minidump"));
            this.gzipIfNotEmpty(arrby3, new File((File)object2, "metadata"));
            this.gzipIfNotEmpty((byte[])object, new File((File)object2, "binaryImages"));
            this.gzipIfNotEmpty(arrby, new File((File)object2, "session"));
            this.gzipIfNotEmpty(arrby4, new File((File)object2, "app"));
            this.gzipIfNotEmpty(arrby5, new File((File)object2, "device"));
            this.gzipIfNotEmpty(arrby6, new File((File)object2, "os"));
            this.gzipIfNotEmpty(arrby7, new File((File)object2, "user"));
            this.gzipIfNotEmpty(arrby9, new File((File)object2, "logs"));
            this.gzipIfNotEmpty(arrby8, new File((File)object2, "keys"));
            return;
        }
        object = Fabric.getLogger();
        object2 = new StringBuilder();
        object2.append("No minidump data found in directory ");
        object2.append(arrby);
        object.w("CrashlyticsCore", object2.toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean firebaseCrashExists() {
        try {
            Class.forName("com.google.firebase.crash.FirebaseCrash");
            return true;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    private CreateReportSpiCall getCreateReportSpiCall(String string, String string2) {
        String string3 = CommonUtils.getStringsFileValue(this.crashlyticsCore.getContext(), CRASHLYTICS_API_ENDPOINT);
        return new CompositeCreateReportSpiCall(new DefaultCreateReportSpiCall(this.crashlyticsCore, string3, string, this.httpRequestFactory), new NativeCreateReportSpiCall(this.crashlyticsCore, string3, string2, this.httpRequestFactory));
    }

    private String getCurrentSessionId() {
        File[] arrfile = this.listSortedSessionBeginFiles();
        if (arrfile.length > 0) {
            return CrashlyticsController.getSessionIdFromSessionFile(arrfile[0]);
        }
        return null;
    }

    private String getPreviousSessionId() {
        File[] arrfile = this.listSortedSessionBeginFiles();
        if (arrfile.length > 1) {
            return CrashlyticsController.getSessionIdFromSessionFile(arrfile[1]);
        }
        return null;
    }

    static String getSessionIdFromSessionFile(File file) {
        return file.getName().substring(0, 35);
    }

    private File[] getTrimmedNonFatalFiles(String string, File[] object, int n) {
        File[] arrfile = object;
        if (((File[])object).length > n) {
            Fabric.getLogger().d("CrashlyticsCore", String.format(Locale.US, "Trimming down to %d logged exceptions.", n));
            this.trimSessionEventFiles(string, n);
            object = new StringBuilder();
            object.append(string);
            object.append(SESSION_NON_FATAL_TAG);
            arrfile = this.listFilesMatching(new FileNameContainsFilter(object.toString()));
        }
        return arrfile;
    }

    private UserMetaData getUserMetaData(String string) {
        if (this.isHandlingException()) {
            return new UserMetaData(this.crashlyticsCore.getUserIdentifier(), this.crashlyticsCore.getUserName(), this.crashlyticsCore.getUserEmail());
        }
        return new MetaDataStore(this.getFilesDir()).readUserData(string);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void gzip(byte[] object, File object2) throws IOException {
        block4 : {
            Object var3_4 = null;
            object2 = new GZIPOutputStream(new FileOutputStream((File)object2));
            try {
                object2.write((byte[])object);
                object2.finish();
            }
            catch (Throwable throwable) {
                object = object2;
                object2 = throwable;
            }
            CommonUtils.closeQuietly((Closeable)object2);
            return;
            break block4;
            catch (Throwable throwable) {
                object = var3_4;
            }
        }
        CommonUtils.closeQuietly((Closeable)object);
        throw object2;
    }

    private void gzipIfNotEmpty(byte[] arrby, File file) throws IOException {
        if (arrby != null && arrby.length > 0) {
            this.gzip(arrby, file);
        }
    }

    private File[] listFiles(File file) {
        return this.ensureFileArrayNotNull(file.listFiles());
    }

    private File[] listFilesMatching(File file, FilenameFilter filenameFilter) {
        return this.ensureFileArrayNotNull(file.listFiles(filenameFilter));
    }

    private File[] listFilesMatching(FileFilter fileFilter) {
        return this.ensureFileArrayNotNull(this.getFilesDir().listFiles(fileFilter));
    }

    private File[] listFilesMatching(FilenameFilter filenameFilter) {
        return this.listFilesMatching(this.getFilesDir(), filenameFilter);
    }

    private File[] listSessionPartFilesFor(String string) {
        return this.listFilesMatching(new SessionPartFileFilter(string));
    }

    private File[] listSortedSessionBeginFiles() {
        File[] arrfile = this.listSessionBeginFiles();
        Arrays.sort(arrfile, LARGEST_FILE_NAME_FIRST);
        return arrfile;
    }

    private byte[] readFile(String string, String string2) {
        File file = this.getFilesDir();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(string2);
        return NativeFileUtils.readFile(new File(file, stringBuilder.toString()));
    }

    private static void recordFatalExceptionAnswersEvent(String string, String string2) {
        Answers answers = Fabric.getKit(Answers.class);
        if (answers == null) {
            Fabric.getLogger().d("CrashlyticsCore", "Answers is not available");
            return;
        }
        answers.onException(new Crash.FatalException(string, string2));
    }

    private void recordFatalFirebaseEvent(long l) {
        if (this.firebaseCrashExists()) {
            Fabric.getLogger().d("CrashlyticsCore", "Skipping logging Crashlytics event to Firebase, FirebaseCrash exists");
            return;
        }
        if (this.firebaseAnalyticsLogger != null) {
            Fabric.getLogger().d("CrashlyticsCore", "Logging Crashlytics event to Firebase");
            Bundle bundle = new Bundle();
            bundle.putInt(FIREBASE_REALTIME, 1);
            bundle.putInt(FIREBASE_CRASH_TYPE, 1);
            bundle.putLong(FIREBASE_TIMESTAMP, l);
            this.firebaseAnalyticsLogger.logEvent(FIREBASE_ANALYTICS_ORIGIN_CRASHLYTICS, FIREBASE_APPLICATION_EXCEPTION, bundle);
            return;
        }
        Fabric.getLogger().d("CrashlyticsCore", "Skipping logging Crashlytics event to Firebase, no Firebase Analytics");
    }

    private static void recordLoggedExceptionAnswersEvent(String string, String string2) {
        Answers answers = Fabric.getKit(Answers.class);
        if (answers == null) {
            Fabric.getLogger().d("CrashlyticsCore", "Answers is not available");
            return;
        }
        answers.onException(new Crash.LoggedException(string, string2));
    }

    private void recursiveDelete(File file) {
        if (file.isDirectory()) {
            File[] arrfile = file.listFiles();
            int n = arrfile.length;
            for (int i = 0; i < n; ++i) {
                this.recursiveDelete(arrfile[i]);
            }
        }
        file.delete();
    }

    private void recursiveDelete(Set<File> object) {
        object = object.iterator();
        while (object.hasNext()) {
            this.recursiveDelete((File)object.next());
        }
    }

    private void retainSessions(File[] arrfile, Set<String> set) {
        for (File file : arrfile) {
            StringBuilder stringBuilder;
            String string = file.getName();
            Object object = SESSION_FILE_PATTERN.matcher(string);
            if (!object.matches()) {
                object = Fabric.getLogger();
                stringBuilder = new StringBuilder();
                stringBuilder.append("Deleting unknown file: ");
                stringBuilder.append(string);
                object.d("CrashlyticsCore", stringBuilder.toString());
                file.delete();
                continue;
            }
            if (set.contains(object.group(1))) continue;
            object = Fabric.getLogger();
            stringBuilder = new StringBuilder();
            stringBuilder.append("Trimming session file: ");
            stringBuilder.append(string);
            object.d("CrashlyticsCore", stringBuilder.toString());
            file.delete();
        }
    }

    private void sendSessionReports(SettingsData object) {
        if (object == null) {
            Fabric.getLogger().w("CrashlyticsCore", "Cannot send reports. Settings are unavailable.");
            return;
        }
        Context context = this.crashlyticsCore.getContext();
        object = this.getCreateReportSpiCall(object.appData.reportsUrl, object.appData.ndkReportsUrl);
        object = new ReportUploader(this.appData.apiKey, (CreateReportSpiCall)object, this.reportFilesProvider, this.handlingExceptionCheck);
        File[] arrfile = this.listCompleteSessionFiles();
        int n = arrfile.length;
        for (int i = 0; i < n; ++i) {
            SessionReport sessionReport = new SessionReport(arrfile[i], SEND_AT_CRASHTIME_HEADER);
            this.backgroundWorker.submit(new SendReportRunnable(context, sessionReport, (ReportUploader)object));
        }
    }

    private boolean shouldPromptUserBeforeSendingCrashReports(SettingsData settingsData) {
        boolean bl = false;
        if (settingsData == null) {
            return false;
        }
        boolean bl2 = bl;
        if (settingsData.featuresData.promptEnabled) {
            bl2 = bl;
            if (!this.preferenceManager.shouldAlwaysSendReports()) {
                bl2 = true;
            }
        }
        return bl2;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void synthesizeSessionFile(File object, String string, File[] arrfile, File object2) {
        void var2_6;
        Object object3;
        block12 : {
            Object object4;
            Object object5;
            Object object6;
            block13 : {
                Object var10_16;
                block11 : {
                    boolean bl = object2 != null;
                    object3 = bl ? this.getFatalSessionFilesDir() : this.getNonFatalSessionFilesDir();
                    if (!object3.exists()) {
                        object3.mkdirs();
                    }
                    object5 = null;
                    var10_16 = null;
                    object6 = object3 = new ClsFileOutputStream((File)object3, string);
                    object4 = CodedOutputStream.newInstance((OutputStream)object3);
                    try {
                        object6 = Fabric.getLogger();
                        object5 = new StringBuilder();
                        object5.append("Collecting SessionStart data for session ID ");
                        object5.append(string);
                        object6.d("CrashlyticsCore", object5.toString());
                        CrashlyticsController.writeToCosFromFile((CodedOutputStream)object4, (File)object);
                        object4.writeUInt64(4, new Date().getTime() / 1000L);
                        object4.writeBool(5, bl);
                        object4.writeUInt32(11, 1);
                        object4.writeEnum(12, 3);
                        this.writeInitialPartsTo((CodedOutputStream)object4, string);
                        CrashlyticsController.writeNonFatalEventsTo((CodedOutputStream)object4, arrfile, string);
                        if (!bl) break block11;
                        CrashlyticsController.writeToCosFromFile((CodedOutputStream)object4, (File)object2);
                    }
                    catch (Throwable throwable) {
                        object = object4;
                        break block12;
                    }
                    catch (Exception exception) {
                        object = object4;
                        break block13;
                    }
                }
                CommonUtils.flushOrLog((Flushable)object4, "Error flushing session file stream");
                CommonUtils.closeOrLog((Closeable)object3, "Failed to close CLS file");
                return;
                catch (Exception exception) {
                    object = var10_16;
                    break block13;
                }
                catch (Throwable throwable) {
                    object3 = object = null;
                    break block12;
                }
                catch (Exception exception) {
                    object3 = null;
                    object = var10_16;
                }
            }
            object5 = object;
            object6 = object3;
            try {
                void var3_11;
                object2 = Fabric.getLogger();
                object5 = object;
                object6 = object3;
                object4 = new StringBuilder();
                object5 = object;
                object6 = object3;
                object4.append("Failed to write session file for session ID: ");
                object5 = object;
                object6 = object3;
                object4.append(string);
                object5 = object;
                object6 = object3;
                object2.e("CrashlyticsCore", object4.toString(), (Throwable)var3_11);
            }
            catch (Throwable throwable) {
                object = object5;
                object3 = object6;
            }
            CommonUtils.flushOrLog((Flushable)object, "Error flushing session file stream");
            this.closeWithoutRenamingOrLog((ClsFileOutputStream)object3);
            return;
        }
        CommonUtils.flushOrLog((Flushable)object, "Error flushing session file stream");
        CommonUtils.closeOrLog((Closeable)object3, "Failed to close CLS file");
        throw var2_6;
    }

    private void trimInvalidSessionFiles() {
        File file = this.getInvalidFilesDir();
        if (!file.exists()) {
            return;
        }
        File[] arrfile = this.listFilesMatching(file, new InvalidPartFileFilter());
        Arrays.sort(arrfile, Collections.reverseOrder());
        HashSet<String> hashSet = new HashSet<String>();
        for (int i = 0; i < arrfile.length && hashSet.size() < 4; ++i) {
            hashSet.add(CrashlyticsController.getSessionIdFromSessionFile(arrfile[i]));
        }
        this.retainSessions(this.listFiles(file), hashSet);
    }

    private void trimOpenSessions(int n) {
        HashSet<String> hashSet = new HashSet<String>();
        File[] arrfile = this.listSortedSessionBeginFiles();
        int n2 = 0;
        int n3 = Math.min(n, arrfile.length);
        for (n = n2; n < n3; ++n) {
            hashSet.add(CrashlyticsController.getSessionIdFromSessionFile(arrfile[n]));
        }
        this.logFileManager.discardOldLogFiles(hashSet);
        this.retainSessions(this.listFilesMatching(new AnySessionPartFileFilter()), hashSet);
    }

    private void trimSessionEventFiles(String string, int n) {
        File file = this.getFilesDir();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(SESSION_NON_FATAL_TAG);
        Utils.capFileCount(file, new FileNameContainsFilter(stringBuilder.toString()), n, SMALLEST_FILE_NAME_FIRST);
    }

    private void writeBeginSession(final String string, Date date) throws Exception {
        final String string2 = String.format(Locale.US, GENERATOR_FORMAT, this.crashlyticsCore.getVersion());
        final long l = date.getTime() / 1000L;
        this.writeSessionPartFile(string, SESSION_BEGIN_TAG, new CodedOutputStreamWriteAction(){

            @Override
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeBeginSession(codedOutputStream, string, string2, l);
            }
        });
        this.writeFile(string, "BeginSession.json", new FileOutputStreamWriteAction(){

            @Override
            public void writeTo(FileOutputStream fileOutputStream) throws Exception {
                fileOutputStream.write(new JSONObject((Map)new HashMap<String, Object>(){
                    {
                        this.put("session_id", string);
                        this.put("generator", string2);
                        this.put("started_at_seconds", l);
                    }
                }).toString().getBytes());
            }

        });
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void writeFatal(Date object, Thread thread, Throwable throwable) {
        Object object2;
        Object object3;
        void var1_5;
        block12 : {
            block14 : {
                Object object4;
                block13 : {
                    Object var8_14;
                    block11 : {
                        object4 = null;
                        object3 = null;
                        var8_14 = null;
                        object2 = this.getCurrentSessionId();
                        if (object2 != null) break block11;
                        Fabric.getLogger().e("CrashlyticsCore", "Tried to write a fatal exception while no session was open.", null);
                        CommonUtils.flushOrLog(null, "Failed to flush to session begin file.");
                        CommonUtils.closeOrLog(null, "Failed to close fatal exception file output stream.");
                        return;
                    }
                    CrashlyticsController.recordFatalExceptionAnswersEvent((String)object2, throwable.getClass().getName());
                    Object object5 = this.getFilesDir();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append((String)object2);
                    stringBuilder.append(SESSION_FATAL_TAG);
                    object2 = new ClsFileOutputStream((File)object5, stringBuilder.toString());
                    object3 = object4;
                    object4 = object2;
                    object5 = CodedOutputStream.newInstance((OutputStream)object2);
                    try {
                        this.writeSessionEvent((CodedOutputStream)object5, (Date)object, thread, throwable, EVENT_TYPE_CRASH, true);
                    }
                    catch (Throwable throwable2) {
                        object3 = object5;
                        break block12;
                    }
                    catch (Exception exception) {
                        object = object5;
                        break block13;
                    }
                    CommonUtils.flushOrLog((Flushable)object5, "Failed to flush to session begin file.");
                    break block14;
                    catch (Exception exception) {
                        object = var8_14;
                        break block13;
                    }
                    catch (Throwable throwable3) {
                        object2 = null;
                        break block12;
                    }
                    catch (Exception exception) {
                        object2 = null;
                        object = var8_14;
                    }
                }
                object3 = object;
                object4 = object2;
                try {
                    void var2_10;
                    Fabric.getLogger().e("CrashlyticsCore", "An error occurred in the fatal exception logger", (Throwable)var2_10);
                }
                catch (Throwable throwable4) {
                    object2 = object4;
                }
                CommonUtils.flushOrLog((Flushable)object, "Failed to flush to session begin file.");
            }
            CommonUtils.closeOrLog((Closeable)object2, "Failed to close fatal exception file output stream.");
            return;
        }
        CommonUtils.flushOrLog(object3, "Failed to flush to session begin file.");
        CommonUtils.closeOrLog((Closeable)object2, "Failed to close fatal exception file output stream.");
        throw var1_5;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void writeFile(String object, String string, FileOutputStreamWriteAction object2) throws Exception {
        StringBuilder stringBuilder;
        void var3_6;
        block4 : {
            stringBuilder = null;
            File file = this.getFilesDir();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append((String)object);
            stringBuilder2.append(string);
            object = new FileOutputStream(new File(file, stringBuilder2.toString()));
            try {
                object2.writeTo((FileOutputStream)object);
                object2 = new StringBuilder();
                object2.append("Failed to close ");
                object2.append(string);
                object2.append(" file.");
            }
            catch (Throwable throwable) {}
            CommonUtils.closeOrLog((Closeable)object, object2.toString());
            return;
            break block4;
            catch (Throwable throwable) {
                object = stringBuilder;
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to close ");
        stringBuilder.append(string);
        stringBuilder.append(" file.");
        CommonUtils.closeOrLog((Closeable)object, stringBuilder.toString());
        throw var3_6;
    }

    private void writeInitialPartsTo(CodedOutputStream codedOutputStream, String string) throws IOException {
        for (String string2 : INITIAL_SESSION_PART_TAGS) {
            Object object;
            Object object2 = new File[]();
            object2.append(string);
            object2.append(string2);
            object2.append(".cls");
            object2 = this.listFilesMatching(new FileNameContainsFilter(object2.toString()));
            if (((File[])object2).length == 0) {
                object2 = Fabric.getLogger();
                object = new StringBuilder();
                object.append("Can't find ");
                object.append(string2);
                object.append(" data for session ID ");
                object.append(string);
                object2.e("CrashlyticsCore", object.toString(), null);
                continue;
            }
            object = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Collecting ");
            stringBuilder.append(string2);
            stringBuilder.append(" data for session ID ");
            stringBuilder.append(string);
            object.d("CrashlyticsCore", stringBuilder.toString());
            CrashlyticsController.writeToCosFromFile(codedOutputStream, (File)object2[0]);
        }
    }

    private static void writeNonFatalEventsTo(CodedOutputStream codedOutputStream, File[] arrfile, String string) {
        Arrays.sort(arrfile, CommonUtils.FILE_MODIFIED_COMPARATOR);
        for (File file : arrfile) {
            try {
                Fabric.getLogger().d("CrashlyticsCore", String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", string, file.getName()));
                CrashlyticsController.writeToCosFromFile(codedOutputStream, file);
            }
            catch (Exception exception) {
                Fabric.getLogger().e("CrashlyticsCore", "Error writting non-fatal to session.", exception);
            }
        }
    }

    private void writeSessionApp(String string) throws Exception {
        final String string2 = this.idManager.getAppIdentifier();
        final String string3 = this.appData.versionCode;
        final String string4 = this.appData.versionName;
        final String string5 = this.idManager.getAppInstallIdentifier();
        final int n = DeliveryMechanism.determineFrom(this.appData.installerPackageName).getId();
        this.writeSessionPartFile(string, SESSION_APP_TAG, new CodedOutputStreamWriteAction(){

            @Override
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeSessionApp(codedOutputStream, string2, CrashlyticsController.access$1900((CrashlyticsController)CrashlyticsController.this).apiKey, string3, string4, string5, n, CrashlyticsController.this.unityVersion);
            }
        });
        this.writeFile(string, "SessionApp.json", new FileOutputStreamWriteAction(){

            @Override
            public void writeTo(FileOutputStream fileOutputStream) throws Exception {
                fileOutputStream.write(new JSONObject((Map)new HashMap<String, Object>(){
                    {
                        this.put("app_identifier", string2);
                        this.put("api_key", CrashlyticsController.access$1900((CrashlyticsController)CrashlyticsController.this).apiKey);
                        this.put("version_code", string3);
                        this.put("version_name", string4);
                        this.put("install_uuid", string5);
                        this.put("delivery_mechanism", n);
                        20.this = TextUtils.isEmpty((CharSequence)CrashlyticsController.this.unityVersion) ? "" : CrashlyticsController.this.unityVersion;
                        this.put("unity_version", 20.this);
                    }
                }).toString().getBytes());
            }

        });
    }

    private void writeSessionDevice(String string) throws Exception {
        Context context = this.crashlyticsCore.getContext();
        Object object = new StatFs(Environment.getDataDirectory().getPath());
        final int n = CommonUtils.getCpuArchitectureInt();
        final int n2 = Runtime.getRuntime().availableProcessors();
        final long l = CommonUtils.getTotalRamInBytes();
        final long l2 = (long)object.getBlockCount() * (long)object.getBlockSize();
        final boolean bl = CommonUtils.isEmulator(context);
        object = this.idManager.getDeviceIdentifiers();
        int n3 = CommonUtils.getDeviceState(context);
        this.writeSessionPartFile(string, SESSION_DEVICE_TAG, new CodedOutputStreamWriteAction((Map)object, n3){
            final /* synthetic */ Map val$ids;
            final /* synthetic */ int val$state;
            {
                this.val$ids = map;
                this.val$state = n3;
            }

            @Override
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeSessionDevice(codedOutputStream, n, Build.MODEL, n2, l, l2, bl, this.val$ids, this.val$state, Build.MANUFACTURER, Build.PRODUCT);
            }
        });
        this.writeFile(string, "SessionDevice.json", new FileOutputStreamWriteAction((Map)object, n3){
            final /* synthetic */ Map val$ids;
            final /* synthetic */ int val$state;
            {
                this.val$ids = map;
                this.val$state = n3;
            }

            @Override
            public void writeTo(FileOutputStream fileOutputStream) throws Exception {
                fileOutputStream.write(new JSONObject((Map)new HashMap<String, Object>(){
                    {
                        this.put("arch", n);
                        this.put("build_model", Build.MODEL);
                        this.put("available_processors", n2);
                        this.put("total_ram", l);
                        this.put("disk_space", l2);
                        this.put("is_emulator", bl);
                        this.put("ids", 24.this.val$ids);
                        this.put("state", 24.this.val$state);
                        this.put("build_manufacturer", Build.MANUFACTURER);
                        this.put("build_product", Build.PRODUCT);
                    }
                }).toString().getBytes());
            }

        });
    }

    private void writeSessionEvent(CodedOutputStream codedOutputStream, Date treeMap, Thread thread, Throwable arrthread, String string, boolean bl) throws Exception {
        TrimmedThrowableData trimmedThrowableData = new TrimmedThrowableData((Throwable)arrthread, this.stackTraceTrimmingStrategy);
        Object object = this.crashlyticsCore.getContext();
        long l = treeMap.getTime() / 1000L;
        Float f = CommonUtils.getBatteryLevel((Context)object);
        int n = CommonUtils.getBatteryVelocity((Context)object, this.devicePowerStateListener.isPowerConnected());
        boolean bl2 = CommonUtils.getProximitySensorEnabled((Context)object);
        int n2 = object.getResources().getConfiguration().orientation;
        long l2 = CommonUtils.getTotalRamInBytes();
        long l3 = CommonUtils.calculateFreeRamInBytes((Context)object);
        long l4 = CommonUtils.calculateUsedDiskSpaceInBytes(Environment.getDataDirectory().getPath());
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = CommonUtils.getAppProcessInfo(object.getPackageName(), (Context)object);
        LinkedList<StackTraceElement[]> linkedList = new LinkedList<StackTraceElement[]>();
        StackTraceElement[] arrstackTraceElement = trimmedThrowableData.stacktrace;
        String string2 = this.appData.buildId;
        String string3 = this.idManager.getAppIdentifier();
        int n3 = 0;
        if (bl) {
            treeMap = Thread.getAllStackTraces();
            arrthread = new Thread[treeMap.size()];
            for (Map.Entry entry : treeMap.entrySet()) {
                arrthread[n3] = (Thread)entry.getKey();
                linkedList.add(this.stackTraceTrimmingStrategy.getTrimmedStackTrace((StackTraceElement[])entry.getValue()));
                ++n3;
            }
        } else {
            arrthread = new Thread[]{};
        }
        if (!CommonUtils.getBooleanResourceValue((Context)object, COLLECT_CUSTOM_KEYS, true)) {
            treeMap = new Object();
        } else {
            treeMap = object = this.crashlyticsCore.getAttributes();
            if (object != null) {
                treeMap = object;
                if (object.size() > 1) {
                    treeMap = new TreeMap(object);
                }
            }
        }
        SessionProtobufHelper.writeSessionEvent(codedOutputStream, l, string, trimmedThrowableData, thread, arrstackTraceElement, arrthread, linkedList, (Map<String, String>)treeMap, this.logFileManager, runningAppProcessInfo, n2, string3, string2, f, n, bl2, l2 - l3, l4);
    }

    private void writeSessionOS(String string) throws Exception {
        final boolean bl = CommonUtils.isRooted(this.crashlyticsCore.getContext());
        this.writeSessionPartFile(string, SESSION_OS_TAG, new CodedOutputStreamWriteAction(){

            @Override
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeSessionOS(codedOutputStream, Build.VERSION.RELEASE, Build.VERSION.CODENAME, bl);
            }
        });
        this.writeFile(string, "SessionOS.json", new FileOutputStreamWriteAction(){

            @Override
            public void writeTo(FileOutputStream fileOutputStream) throws Exception {
                fileOutputStream.write(new JSONObject((Map)new HashMap<String, Object>(){
                    {
                        this.put("version", Build.VERSION.RELEASE);
                        this.put("build_version", Build.VERSION.CODENAME);
                        this.put("is_rooted", bl);
                    }
                }).toString().getBytes());
            }

        });
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void writeSessionPartFile(String object, String string, CodedOutputStreamWriteAction object2) throws Exception {
        Object object3;
        Object object4;
        block6 : {
            object3 = null;
            object4 = this.getFilesDir();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append(string);
            object = new ClsFileOutputStream((File)object4, stringBuilder.toString());
            try {
                object4 = CodedOutputStream.newInstance((OutputStream)object);
            }
            catch (Throwable throwable) {
                object2 = object;
                object = throwable;
                break block6;
            }
            try {
                object2.writeTo((CodedOutputStream)object4);
                object2 = new StringBuilder();
                object2.append("Failed to flush to session ");
                object2.append(string);
                object2.append(" file.");
            }
            catch (Throwable throwable) {
                object3 = object4;
                object2 = object;
                object = throwable;
            }
            CommonUtils.flushOrLog((Flushable)object4, object2.toString());
            object2 = new StringBuilder();
            object2.append("Failed to close session ");
            object2.append(string);
            object2.append(" file.");
            CommonUtils.closeOrLog((Closeable)object, object2.toString());
            return;
            break block6;
            catch (Throwable throwable) {
                object2 = null;
            }
        }
        object4 = new StringBuilder();
        object4.append("Failed to flush to session ");
        object4.append(string);
        object4.append(" file.");
        CommonUtils.flushOrLog(object3, object4.toString());
        object3 = new StringBuilder();
        object3.append("Failed to close session ");
        object3.append(string);
        object3.append(" file.");
        CommonUtils.closeOrLog((Closeable)object2, object3.toString());
        throw object;
    }

    private void writeSessionPartsToSessionFile(File object, String string, int n) {
        Object object2 = Fabric.getLogger();
        File[] arrfile = new File[]();
        arrfile.append("Collecting session parts for ID ");
        arrfile.append(string);
        object2.d("CrashlyticsCore", arrfile.toString());
        object2 = new StringBuilder();
        object2.append(string);
        object2.append(SESSION_FATAL_TAG);
        object2 = this.listFilesMatching(new FileNameContainsFilter(object2.toString()));
        boolean bl = object2 != null && ((File[])object2).length > 0;
        Fabric.getLogger().d("CrashlyticsCore", String.format(Locale.US, "Session %s has fatal exception: %s", string, bl));
        arrfile = new StringBuilder();
        arrfile.append(string);
        arrfile.append(SESSION_NON_FATAL_TAG);
        arrfile = this.listFilesMatching(new FileNameContainsFilter(arrfile.toString()));
        boolean bl2 = arrfile != null && arrfile.length > 0;
        Fabric.getLogger().d("CrashlyticsCore", String.format(Locale.US, "Session %s has non-fatal exceptions: %s", string, bl2));
        if (!bl && !bl2) {
            object = Fabric.getLogger();
            object2 = new StringBuilder();
            object2.append("No events present for session ID ");
            object2.append(string);
            object.d("CrashlyticsCore", object2.toString());
        } else {
            arrfile = this.getTrimmedNonFatalFiles(string, arrfile, n);
            object2 = bl ? object2[0] : null;
            this.synthesizeSessionFile((File)object, string, arrfile, (File)object2);
        }
        object = Fabric.getLogger();
        object2 = new StringBuilder();
        object2.append("Removing session part files for ID ");
        object2.append(string);
        object.d("CrashlyticsCore", object2.toString());
        this.deleteSessionPartFilesFor(string);
    }

    private void writeSessionUser(String string) throws Exception {
        this.writeSessionPartFile(string, SESSION_USER_TAG, new CodedOutputStreamWriteAction(this.getUserMetaData(string)){
            final /* synthetic */ UserMetaData val$userMetaData;
            {
                this.val$userMetaData = userMetaData;
            }

            @Override
            public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
                SessionProtobufHelper.writeSessionUser(codedOutputStream, this.val$userMetaData.id, this.val$userMetaData.name, this.val$userMetaData.email);
            }
        });
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void writeToCosFromFile(CodedOutputStream object, File file) throws IOException {
        void var1_4;
        block5 : {
            if (!file.exists()) {
                object = Fabric.getLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Tried to include a file that doesn't exist: ");
                stringBuilder.append(file.getName());
                object.e("CrashlyticsCore", stringBuilder.toString(), null);
                return;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                CrashlyticsController.copyToCodedOutputStream(fileInputStream, (CodedOutputStream)object, (int)file.length());
            }
            catch (Throwable throwable) {
                object = fileInputStream;
                break block5;
            }
            CommonUtils.closeOrLog(fileInputStream, "Failed to close file input stream.");
            return;
            catch (Throwable throwable) {
                object = null;
            }
        }
        CommonUtils.closeOrLog((Closeable)object, "Failed to close file input stream.");
        throw var1_4;
    }

    void cacheKeyData(final Map<String, String> map) {
        this.backgroundWorker.submit(new Callable<Void>(){

            @Override
            public Void call() throws Exception {
                String string = CrashlyticsController.this.getCurrentSessionId();
                new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeKeyData(string, map);
                return null;
            }
        });
    }

    void cacheUserData(final String string, final String string2, final String string3) {
        this.backgroundWorker.submit(new Callable<Void>(){

            @Override
            public Void call() throws Exception {
                String string4 = CrashlyticsController.this.getCurrentSessionId();
                new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeUserData(string4, new UserMetaData(string, string2, string3));
                return null;
            }
        });
    }

    void cleanInvalidTempFiles() {
        this.backgroundWorker.submit(new Runnable(){

            @Override
            public void run() {
                CrashlyticsController.this.doCleanInvalidTempFiles(CrashlyticsController.this.listFilesMatching(new InvalidPartFileFilter()));
            }
        });
    }

    void doCleanInvalidTempFiles(File[] object) {
        Logger logger;
        StringBuilder stringBuilder;
        File[] arrfile = new File[]();
        int n = 0;
        for (File file : object) {
            logger = Fabric.getLogger();
            stringBuilder = new StringBuilder();
            stringBuilder.append("Found invalid session part file: ");
            stringBuilder.append(file);
            logger.d("CrashlyticsCore", stringBuilder.toString());
            arrfile.add(CrashlyticsController.getSessionIdFromSessionFile(file));
        }
        if (arrfile.isEmpty()) {
            return;
        }
        object = this.getInvalidFilesDir();
        if (!object.exists()) {
            object.mkdir();
        }
        arrfile = this.listFilesMatching(new FilenameFilter((Set)arrfile){
            final /* synthetic */ Set val$invalidSessionIds;
            {
                this.val$invalidSessionIds = set;
            }

            @Override
            public boolean accept(File file, String string) {
                if (string.length() < 35) {
                    return false;
                }
                return this.val$invalidSessionIds.contains(string.substring(0, 35));
            }
        });
        int n2 = arrfile.length;
        for (int i = n; i < n2; ++i) {
            File file;
            file = arrfile[i];
            logger = Fabric.getLogger();
            stringBuilder = new StringBuilder();
            stringBuilder.append("Moving session file: ");
            stringBuilder.append(file);
            logger.d("CrashlyticsCore", stringBuilder.toString());
            if (file.renameTo(new File((File)object, file.getName()))) continue;
            logger = Fabric.getLogger();
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not move session file. Deleting ");
            stringBuilder.append(file);
            logger.d("CrashlyticsCore", stringBuilder.toString());
            file.delete();
        }
        this.trimInvalidSessionFiles();
    }

    void doCloseSessions(SessionSettingsData sessionSettingsData) throws Exception {
        this.doCloseSessions(sessionSettingsData, false);
    }

    void enableExceptionHandling(Thread.UncaughtExceptionHandler uncaughtExceptionHandler, boolean bl) {
        this.openSession();
        this.crashHandler = new CrashlyticsUncaughtExceptionHandler(new CrashlyticsUncaughtExceptionHandler.CrashListener(){

            @Override
            public void onUncaughtException(CrashlyticsUncaughtExceptionHandler.SettingsDataProvider settingsDataProvider, Thread thread, Throwable throwable, boolean bl) {
                CrashlyticsController.this.handleUncaughtException(settingsDataProvider, thread, throwable, bl);
            }
        }, new DefaultSettingsDataProvider(), bl, uncaughtExceptionHandler);
        Thread.setDefaultUncaughtExceptionHandler(this.crashHandler);
    }

    boolean finalizeNativeReport(final CrashlyticsNdkData crashlyticsNdkData) {
        if (crashlyticsNdkData == null) {
            return true;
        }
        return (Boolean)this.backgroundWorker.submitAndWait(new Callable<Boolean>(){

            @Override
            public Boolean call() throws Exception {
                File file;
                TreeSet<File> treeSet = crashlyticsNdkData.timestampedDirectories;
                String string = CrashlyticsController.this.getPreviousSessionId();
                if (string != null && !treeSet.isEmpty() && (file = treeSet.first()) != null) {
                    CrashlyticsController.this.finalizeMostRecentNativeCrash(CrashlyticsController.this.crashlyticsCore.getContext(), file, string);
                }
                CrashlyticsController.this.recursiveDelete(treeSet);
                return Boolean.TRUE;
            }
        });
    }

    boolean finalizeSessions(final SessionSettingsData sessionSettingsData) {
        return (Boolean)this.backgroundWorker.submitAndWait(new Callable<Boolean>(){

            @Override
            public Boolean call() throws Exception {
                if (CrashlyticsController.this.isHandlingException()) {
                    Fabric.getLogger().d("CrashlyticsCore", "Skipping session finalization because a crash has already occurred.");
                    return Boolean.FALSE;
                }
                Fabric.getLogger().d("CrashlyticsCore", "Finalizing previously open sessions.");
                CrashlyticsController.this.doCloseSessions(sessionSettingsData, true);
                Fabric.getLogger().d("CrashlyticsCore", "Closed all previously open sessions");
                return Boolean.TRUE;
            }
        });
    }

    File getFatalSessionFilesDir() {
        return new File(this.getFilesDir(), FATAL_SESSION_DIR);
    }

    File getFilesDir() {
        return this.fileStore.getFilesDir();
    }

    File getInvalidFilesDir() {
        return new File(this.getFilesDir(), INVALID_CLS_CACHE_DIR);
    }

    File getNonFatalSessionFilesDir() {
        return new File(this.getFilesDir(), NONFATAL_SESSION_DIR);
    }

    void handleUncaughtException(CrashlyticsUncaughtExceptionHandler.SettingsDataProvider settingsDataProvider, Thread thread, Throwable throwable, boolean bl) {
        synchronized (this) {
            Object object = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Crashlytics is handling uncaught exception \"");
            stringBuilder.append(throwable);
            stringBuilder.append("\" from thread ");
            stringBuilder.append(thread.getName());
            object.d("CrashlyticsCore", stringBuilder.toString());
            this.devicePowerStateListener.dispose();
            object = new Date();
            this.backgroundWorker.submitAndWait(new Callable<Void>((Date)object, thread, throwable, settingsDataProvider, bl){
                final /* synthetic */ Throwable val$ex;
                final /* synthetic */ boolean val$firebaseCrashlyticsClientFlag;
                final /* synthetic */ CrashlyticsUncaughtExceptionHandler.SettingsDataProvider val$settingsDataProvider;
                final /* synthetic */ Thread val$thread;
                final /* synthetic */ Date val$time;
                {
                    this.val$time = date;
                    this.val$thread = thread;
                    this.val$ex = throwable;
                    this.val$settingsDataProvider = settingsDataProvider;
                    this.val$firebaseCrashlyticsClientFlag = bl;
                }

                @Override
                public Void call() throws Exception {
                    SessionSettingsData sessionSettingsData;
                    Object object;
                    CrashlyticsController.this.crashlyticsCore.createCrashMarker();
                    CrashlyticsController.this.writeFatal(this.val$time, this.val$thread, this.val$ex);
                    SettingsData settingsData = this.val$settingsDataProvider.getSettingsData();
                    if (settingsData != null) {
                        sessionSettingsData = settingsData.sessionData;
                        object = settingsData.featuresData;
                    } else {
                        sessionSettingsData = null;
                        object = sessionSettingsData;
                    }
                    boolean bl = object == null || object.firebaseCrashlyticsEnabled;
                    if (bl || this.val$firebaseCrashlyticsClientFlag) {
                        CrashlyticsController.this.recordFatalFirebaseEvent(this.val$time.getTime());
                    }
                    CrashlyticsController.this.doCloseSessions(sessionSettingsData);
                    CrashlyticsController.this.doOpenSession();
                    if (sessionSettingsData != null) {
                        CrashlyticsController.this.trimSessionFiles(sessionSettingsData.maxCompleteSessionsCount);
                    }
                    if (!CrashlyticsController.this.shouldPromptUserBeforeSendingCrashReports(settingsData)) {
                        CrashlyticsController.this.sendSessionReports(settingsData);
                    }
                    return null;
                }
            });
            return;
        }
    }

    boolean hasOpenSession() {
        if (this.listSessionBeginFiles().length > 0) {
            return true;
        }
        return false;
    }

    boolean isHandlingException() {
        if (this.crashHandler != null && this.crashHandler.isHandlingException()) {
            return true;
        }
        return false;
    }

    File[] listCompleteSessionFiles() {
        LinkedList linkedList = new LinkedList();
        Collections.addAll(linkedList, this.listFilesMatching(this.getFatalSessionFilesDir(), SESSION_FILE_FILTER));
        Collections.addAll(linkedList, this.listFilesMatching(this.getNonFatalSessionFilesDir(), SESSION_FILE_FILTER));
        Collections.addAll(linkedList, this.listFilesMatching(this.getFilesDir(), SESSION_FILE_FILTER));
        return linkedList.toArray(new File[linkedList.size()]);
    }

    File[] listNativeSessionFileDirectories() {
        return this.listFilesMatching(SESSION_DIRECTORY_FILTER);
    }

    File[] listSessionBeginFiles() {
        return this.listFilesMatching(SESSION_BEGIN_FILE_FILTER);
    }

    void openSession() {
        this.backgroundWorker.submit(new Callable<Void>(){

            @Override
            public Void call() throws Exception {
                CrashlyticsController.this.doOpenSession();
                return null;
            }
        });
    }

    void registerAnalyticsEventListener(SettingsData settingsData) {
        if (settingsData.featuresData.firebaseCrashlyticsEnabled && this.appMeasurementEventListenerRegistrar.register()) {
            Fabric.getLogger().d("CrashlyticsCore", "Registered Firebase Analytics event listener");
        }
    }

    void registerDevicePowerStateListener() {
        this.devicePowerStateListener.initialize();
    }

    void submitAllReports(float f, SettingsData object) {
        if (object == null) {
            Fabric.getLogger().w("CrashlyticsCore", "Could not send reports. Settings are not available.");
            return;
        }
        CreateReportSpiCall createReportSpiCall = this.getCreateReportSpiCall(object.appData.reportsUrl, object.appData.ndkReportsUrl);
        object = this.shouldPromptUserBeforeSendingCrashReports((SettingsData)object) ? new PrivacyDialogCheck(this.crashlyticsCore, this.preferenceManager, object.promptData) : new ReportUploader.AlwaysSendCheck();
        new ReportUploader(this.appData.apiKey, createReportSpiCall, this.reportFilesProvider, this.handlingExceptionCheck).uploadReports(f, (ReportUploader.SendCheck)object);
    }

    void trimSessionFiles(int n) {
        n -= Utils.capFileCount(this.getFatalSessionFilesDir(), n, SMALLEST_FILE_NAME_FIRST);
        int n2 = Utils.capFileCount(this.getNonFatalSessionFilesDir(), n, SMALLEST_FILE_NAME_FIRST);
        Utils.capFileCount(this.getFilesDir(), SESSION_FILE_FILTER, n - n2, SMALLEST_FILE_NAME_FIRST);
    }

    void writeNonFatalException(final Thread thread, final Throwable throwable) {
        final Date date = new Date();
        this.backgroundWorker.submit(new Runnable(){

            @Override
            public void run() {
                if (!CrashlyticsController.this.isHandlingException()) {
                    CrashlyticsController.this.doWriteNonFatal(date, thread, throwable);
                }
            }
        });
    }

    void writeToLog(final long l, final String string) {
        this.backgroundWorker.submit(new Callable<Void>(){

            @Override
            public Void call() throws Exception {
                if (!CrashlyticsController.this.isHandlingException()) {
                    CrashlyticsController.this.logFileManager.writeToLog(l, string);
                }
                return null;
            }
        });
    }

    private static class AnySessionPartFileFilter
    implements FilenameFilter {
        private AnySessionPartFileFilter() {
        }

        @Override
        public boolean accept(File file, String string) {
            if (!CrashlyticsController.SESSION_FILE_FILTER.accept(file, string) && SESSION_FILE_PATTERN.matcher(string).matches()) {
                return true;
            }
            return false;
        }
    }

    private static interface CodedOutputStreamWriteAction {
        public void writeTo(CodedOutputStream var1) throws Exception;
    }

    private static final class DefaultSettingsDataProvider
    implements CrashlyticsUncaughtExceptionHandler.SettingsDataProvider {
        private DefaultSettingsDataProvider() {
        }

        @Override
        public SettingsData getSettingsData() {
            return Settings.getInstance().awaitSettingsData();
        }
    }

    static class FileNameContainsFilter
    implements FilenameFilter {
        private final String string;

        public FileNameContainsFilter(String string) {
            this.string = string;
        }

        @Override
        public boolean accept(File file, String string) {
            if (string.contains(this.string) && !string.endsWith(".cls_temp")) {
                return true;
            }
            return false;
        }
    }

    private static interface FileOutputStreamWriteAction {
        public void writeTo(FileOutputStream var1) throws Exception;
    }

    static class InvalidPartFileFilter
    implements FilenameFilter {
        InvalidPartFileFilter() {
        }

        @Override
        public boolean accept(File file, String string) {
            if (!ClsFileOutputStream.TEMP_FILENAME_FILTER.accept(file, string) && !string.contains(CrashlyticsController.SESSION_EVENT_MISSING_BINARY_IMGS_TAG)) {
                return false;
            }
            return true;
        }
    }

    private static final class LogFileDirectoryProvider
    implements LogFileManager.DirectoryProvider {
        private static final String LOG_FILES_DIR = "log-files";
        private final FileStore rootFileStore;

        public LogFileDirectoryProvider(FileStore fileStore) {
            this.rootFileStore = fileStore;
        }

        @Override
        public File getLogFileDir() {
            File file = new File(this.rootFileStore.getFilesDir(), LOG_FILES_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
    }

    private static final class PrivacyDialogCheck
    implements ReportUploader.SendCheck {
        private final Kit kit;
        private final PreferenceManager preferenceManager;
        private final PromptSettingsData promptData;

        public PrivacyDialogCheck(Kit kit, PreferenceManager preferenceManager, PromptSettingsData promptSettingsData) {
            this.kit = kit;
            this.preferenceManager = preferenceManager;
            this.promptData = promptSettingsData;
        }

        @Override
        public boolean canSendReports() {
            Activity activity = this.kit.getFabric().getCurrentActivity();
            if (activity != null && !activity.isFinishing()) {
                Object object = new CrashPromptDialog.AlwaysSendCallback(){

                    @Override
                    public void sendUserReportsWithoutPrompting(boolean bl) {
                        PrivacyDialogCheck.this.preferenceManager.setShouldAlwaysSendReports(bl);
                    }
                };
                object = CrashPromptDialog.create(activity, this.promptData, (CrashPromptDialog.AlwaysSendCallback)object);
                activity.runOnUiThread(new Runnable((CrashPromptDialog)object){
                    final /* synthetic */ CrashPromptDialog val$dialog;
                    {
                        this.val$dialog = crashPromptDialog;
                    }

                    @Override
                    public void run() {
                        this.val$dialog.show();
                    }
                });
                Fabric.getLogger().d("CrashlyticsCore", "Waiting for user opt-in.");
                object.await();
                return object.getOptIn();
            }
            return true;
        }

    }

    private final class ReportUploaderFilesProvider
    implements ReportUploader.ReportFilesProvider {
        private ReportUploaderFilesProvider() {
        }

        @Override
        public File[] getCompleteSessionFiles() {
            return CrashlyticsController.this.listCompleteSessionFiles();
        }

        @Override
        public File[] getInvalidSessionFiles() {
            return CrashlyticsController.this.getInvalidFilesDir().listFiles();
        }

        @Override
        public File[] getNativeReportFiles() {
            return CrashlyticsController.this.listNativeSessionFileDirectories();
        }
    }

    private final class ReportUploaderHandlingExceptionCheck
    implements ReportUploader.HandlingExceptionCheck {
        private ReportUploaderHandlingExceptionCheck() {
        }

        @Override
        public boolean isHandlingException() {
            return CrashlyticsController.this.isHandlingException();
        }
    }

    private static final class SendReportRunnable
    implements Runnable {
        private final Context context;
        private final Report report;
        private final ReportUploader reportUploader;

        public SendReportRunnable(Context context, Report report, ReportUploader reportUploader) {
            this.context = context;
            this.report = report;
            this.reportUploader = reportUploader;
        }

        @Override
        public void run() {
            if (!CommonUtils.canTryConnection(this.context)) {
                return;
            }
            Fabric.getLogger().d("CrashlyticsCore", "Attempting to send crash report at time of crash...");
            this.reportUploader.forceUpload(this.report);
        }
    }

    static class SessionPartFileFilter
    implements FilenameFilter {
        private final String sessionId;

        public SessionPartFileFilter(String string) {
            this.sessionId = string;
        }

        @Override
        public boolean accept(File serializable, String string) {
            serializable = new StringBuilder();
            serializable.append(this.sessionId);
            serializable.append(".cls");
            boolean bl = string.equals(serializable.toString());
            boolean bl2 = false;
            if (bl) {
                return false;
            }
            bl = bl2;
            if (string.contains(this.sessionId)) {
                bl = bl2;
                if (!string.endsWith(".cls_temp")) {
                    bl = true;
                }
            }
            return bl;
        }
    }

}
