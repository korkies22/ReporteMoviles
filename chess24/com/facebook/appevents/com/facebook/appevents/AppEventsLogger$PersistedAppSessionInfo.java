/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 */
package com.facebook.appevents;

import android.content.Context;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.FacebookTimeSpentData;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

static class AppEventsLogger.PersistedAppSessionInfo {
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
                AppEventsLogger.PersistedAppSessionInfo.saveAppSessionInformation(FacebookSdk.getApplicationContext());
            }
        };
    }

    AppEventsLogger.PersistedAppSessionInfo() {
    }

    private static FacebookTimeSpentData getTimeSpentData(Context object, AccessTokenAppIdPair accessTokenAppIdPair) {
        AppEventsLogger.PersistedAppSessionInfo.restoreAppSessionInformation(object);
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
            AppEventsLogger.PersistedAppSessionInfo.getTimeSpentData(context, accessTokenAppIdPair).onResume(appEventsLogger, l, string);
            AppEventsLogger.PersistedAppSessionInfo.onTimeSpentDataUpdate();
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
            AppEventsLogger.PersistedAppSessionInfo.getTimeSpentData(context, accessTokenAppIdPair).onSuspend(appEventsLogger, l);
            AppEventsLogger.PersistedAppSessionInfo.onTimeSpentDataUpdate();
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
                    void var3_5;
                    ObjectInputStream objectInputStream2;
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
