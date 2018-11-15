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
import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEvent;
import com.facebook.appevents.AppEventCollection;
import com.facebook.appevents.PersistedEvents;
import com.facebook.appevents.SessionEventsState;
import com.facebook.appevents.internal.AppEventUtility;
import com.facebook.internal.Utility;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

class AppEventStore {
    private static final String PERSISTED_EVENTS_FILENAME = "AppEventsLogger.persistedevents";
    private static final String TAG = "com.facebook.appevents.AppEventStore";

    AppEventStore() {
    }

    public static void persistEvents(AccessTokenAppIdPair accessTokenAppIdPair, SessionEventsState sessionEventsState) {
        synchronized (AppEventStore.class) {
            AppEventUtility.assertIsNotMainThread();
            PersistedEvents persistedEvents = AppEventStore.readAndClearStore();
            if (persistedEvents.containsKey(accessTokenAppIdPair)) {
                persistedEvents.get(accessTokenAppIdPair).addAll(sessionEventsState.getEventsToPersist());
            } else {
                persistedEvents.addEvents(accessTokenAppIdPair, sessionEventsState.getEventsToPersist());
            }
            AppEventStore.saveEventsToDisk(persistedEvents);
            return;
        }
    }

    public static void persistEvents(AppEventCollection appEventCollection) {
        synchronized (AppEventStore.class) {
            AppEventUtility.assertIsNotMainThread();
            PersistedEvents persistedEvents = AppEventStore.readAndClearStore();
            for (AccessTokenAppIdPair accessTokenAppIdPair : appEventCollection.keySet()) {
                persistedEvents.addEvents(accessTokenAppIdPair, appEventCollection.get(accessTokenAppIdPair).getEventsToPersist());
            }
            AppEventStore.saveEventsToDisk(persistedEvents);
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
    public static PersistedEvents readAndClearStore() {
        block25 : {
            block22 : {
                // MONITORENTER : com.facebook.appevents.AppEventStore.class
                AppEventUtility.assertIsNotMainThread();
                var4 = FacebookSdk.getApplicationContext();
                var3_1 = null;
                var1_2 = null;
                var1_2 = var0_4 = new MovedClassObjectInputStream(new BufferedInputStream(var4.openFileInput("AppEventsLogger.persistedevents")));
                var2_10 = (PersistedEvents)var0_4.readObject();
                Utility.closeQuietly((Closeable)var0_4);
                try {
                    var4.getFileStreamPath("AppEventsLogger.persistedevents").delete();
                }
                catch (Exception var0_5) {
                    Log.w((String)AppEventStore.TAG, (String)"Got unexpected exception when removing events file: ", (Throwable)var0_5);
                }
                var0_4 = var2_10;
                break block25;
                catch (Exception var2_11) {
                    break block22;
                }
                catch (Throwable var2_12) {
                    var0_4 = var1_2;
                    ** GOTO lbl43
                }
                catch (Exception var2_13) {
                    var0_4 = null;
                }
            }
            var1_2 = var0_4;
            Log.w((String)AppEventStore.TAG, (String)"Got unexpected exception while reading events: ", (Throwable)var2_14);
            Utility.closeQuietly((Closeable)var0_4);
            try {
                var4.getFileStreamPath("AppEventsLogger.persistedevents").delete();
                var0_4 = var3_1;
            }
            catch (Exception var0_6) {
                block26 : {
                    block24 : {
                        block23 : {
                            var1_2 = AppEventStore.TAG;
                            break block26;
                            catch (FileNotFoundException var0_9) {
                                break block23;
                            }
                            catch (FileNotFoundException var1_3) {
                                break block24;
                            }
                            catch (Throwable var2_16) {
                                var0_4 = var1_2;
                            }
lbl43: // 2 sources:
                            Utility.closeQuietly(var0_4);
                            try {
                                var4.getFileStreamPath("AppEventsLogger.persistedevents").delete();
                                throw var2_15;
                            }
                            catch (Exception var0_7) {
                                Log.w((String)AppEventStore.TAG, (String)"Got unexpected exception when removing events file: ", (Throwable)var0_7);
                            }
                            throw var2_15;
                        }
                        var0_4 = null;
                    }
                    Utility.closeQuietly(var0_4);
                    try {
                        var4.getFileStreamPath("AppEventsLogger.persistedevents").delete();
                        var0_4 = var3_1;
                    }
                    catch (Exception var0_8) {
                        var1_2 = AppEventStore.TAG;
                    }
                }
                Log.w((String)var1_2, (String)"Got unexpected exception when removing events file: ", (Throwable)var0_4);
                var0_4 = var3_1;
            }
        }
        var1_2 = var0_4;
        if (var0_4 == null) {
            var1_2 = new PersistedEvents();
        }
        // MONITOREXIT : com.facebook.appevents.AppEventStore.class
        return var1_2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void saveEventsToDisk(PersistedEvents var0) {
        block10 : {
            block9 : {
                var4_4 = FacebookSdk.getApplicationContext();
                var3_5 = null;
                var1_6 = null;
                var2_9 = new ObjectOutputStream(new BufferedOutputStream(var4_4.openFileOutput("AppEventsLogger.persistedevents", 0)));
                try {
                    var2_9.writeObject(var0);
                }
                catch (Throwable var0_1) {
                    var1_6 = var2_9;
                    ** GOTO lbl29
                }
                catch (Exception var1_7) {
                    var0 = var2_9;
                    var2_9 = var1_7;
                    break block9;
                }
                Utility.closeQuietly((Closeable)var2_9);
                return;
                catch (Throwable var0_2) {
                    ** GOTO lbl29
                }
                catch (Exception var2_10) {
                    var0 = var3_5;
                }
            }
            var1_6 = var0;
            {
                Log.w((String)AppEventStore.TAG, (String)"Got unexpected exception while persisting events: ", (Throwable)var2_9);
                var1_6 = var0;
                try {
                    var4_4.getFileStreamPath("AppEventsLogger.persistedevents").delete();
                    break block10;
                }
                catch (Exception var1_8) {}
lbl29: // 2 sources:
                Utility.closeQuietly(var1_6);
                throw var0_3;
            }
        }
        Utility.closeQuietly((Closeable)var0);
    }

    private static class MovedClassObjectInputStream
    extends ObjectInputStream {
        private static final String ACCESS_TOKEN_APP_ID_PAIR_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair$SerializationProxyV1";
        private static final String APP_EVENT_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AppEvent$SerializationProxyV1";

        public MovedClassObjectInputStream(InputStream inputStream) throws IOException {
            super(inputStream);
        }

        @Override
        protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
            ObjectStreamClass objectStreamClass = super.readClassDescriptor();
            if (objectStreamClass.getName().equals(ACCESS_TOKEN_APP_ID_PAIR_SERIALIZATION_PROXY_V1_CLASS_NAME)) {
                return ObjectStreamClass.lookup(AccessTokenAppIdPair.SerializationProxyV1.class);
            }
            ObjectStreamClass objectStreamClass2 = objectStreamClass;
            if (objectStreamClass.getName().equals(APP_EVENT_SERIALIZATION_PROXY_V1_CLASS_NAME)) {
                objectStreamClass2 = ObjectStreamClass.lookup(AppEvent.SerializationProxyV1.class);
            }
            return objectStreamClass2;
        }
    }

}
