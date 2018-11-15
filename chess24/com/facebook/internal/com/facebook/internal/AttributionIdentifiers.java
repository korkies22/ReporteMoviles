/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.PackageManager
 *  android.content.pm.ProviderInfo
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Parcel
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.facebook.internal;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class AttributionIdentifiers {
    private static final String ANDROID_ID_COLUMN_NAME = "androidid";
    private static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
    private static final String ATTRIBUTION_ID_CONTENT_PROVIDER = "com.facebook.katana.provider.AttributionIdProvider";
    private static final String ATTRIBUTION_ID_CONTENT_PROVIDER_WAKIZASHI = "com.facebook.wakizashi.provider.AttributionIdProvider";
    private static final int CONNECTION_RESULT_SUCCESS = 0;
    private static final long IDENTIFIER_REFRESH_INTERVAL_MILLIS = 3600000L;
    private static final String LIMIT_TRACKING_COLUMN_NAME = "limit_tracking";
    private static final String TAG = AttributionIdentifiers.class.getCanonicalName();
    private static AttributionIdentifiers recentlyFetchedIdentifiers;
    private String androidAdvertiserId;
    private String androidInstallerPackage;
    private String attributionId;
    private long fetchTime;
    private boolean limitTracking;

    private static AttributionIdentifiers cacheAndReturnIdentifiers(AttributionIdentifiers attributionIdentifiers) {
        attributionIdentifiers.fetchTime = System.currentTimeMillis();
        recentlyFetchedIdentifiers = attributionIdentifiers;
        return attributionIdentifiers;
    }

    private static AttributionIdentifiers getAndroidId(Context object) {
        AttributionIdentifiers attributionIdentifiers;
        AttributionIdentifiers attributionIdentifiers2 = attributionIdentifiers = AttributionIdentifiers.getAndroidIdViaReflection(object);
        if (attributionIdentifiers == null) {
            object = AttributionIdentifiers.getAndroidIdViaService(object);
            attributionIdentifiers2 = object;
            if (object == null) {
                attributionIdentifiers2 = new AttributionIdentifiers();
            }
        }
        return attributionIdentifiers2;
    }

    private static AttributionIdentifiers getAndroidIdViaReflection(Context object) {
        block9 : {
            block13 : {
                Object object2;
                Method method;
                block14 : {
                    block12 : {
                        block11 : {
                            block10 : {
                                block8 : {
                                    try {
                                        if (Looper.myLooper() == Looper.getMainLooper()) {
                                            throw new FacebookException("getAndroidId cannot be called on the main thread.");
                                        }
                                        object2 = Utility.getMethodQuietly("com.google.android.gms.common.GooglePlayServicesUtil", "isGooglePlayServicesAvailable", Context.class);
                                        if (object2 != null) break block8;
                                        return null;
                                    }
                                    catch (Exception exception) {
                                        Utility.logd("android_id", exception);
                                        return null;
                                    }
                                }
                                object2 = Utility.invokeMethodQuietly(null, (Method)object2, object);
                                if (!(object2 instanceof Integer)) break block9;
                                if ((Integer)object2 == 0) break block10;
                                return null;
                            }
                            object2 = Utility.getMethodQuietly("com.google.android.gms.ads.identifier.AdvertisingIdClient", "getAdvertisingIdInfo", Context.class);
                            if (object2 != null) break block11;
                            return null;
                        }
                        object = Utility.invokeMethodQuietly(null, (Method)object2, object);
                        if (object != null) break block12;
                        return null;
                    }
                    object2 = Utility.getMethodQuietly(object.getClass(), "getId", new Class[0]);
                    method = Utility.getMethodQuietly(object.getClass(), "isLimitAdTrackingEnabled", new Class[0]);
                    if (object2 == null) break block13;
                    if (method != null) break block14;
                    return null;
                }
                AttributionIdentifiers attributionIdentifiers = new AttributionIdentifiers();
                attributionIdentifiers.androidAdvertiserId = (String)Utility.invokeMethodQuietly(object, (Method)object2, new Object[0]);
                attributionIdentifiers.limitTracking = (Boolean)Utility.invokeMethodQuietly(object, method, new Object[0]);
                return attributionIdentifiers;
            }
            return null;
        }
        return null;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static AttributionIdentifiers getAndroidIdViaService(Context context) {
        Throwable throwable2222;
        GoogleAdServiceConnection googleAdServiceConnection = new GoogleAdServiceConnection();
        Object object = new Intent("com.google.android.gms.ads.identifier.service.START");
        object.setPackage("com.google.android.gms");
        if (!context.bindService((Intent)object, (ServiceConnection)googleAdServiceConnection, 1)) return null;
        object = new GoogleAdInfo(googleAdServiceConnection.getBinder());
        AttributionIdentifiers attributionIdentifiers = new AttributionIdentifiers();
        attributionIdentifiers.androidAdvertiserId = object.getAdvertiserId();
        attributionIdentifiers.limitTracking = object.isTrackingLimited();
        context.unbindService((ServiceConnection)googleAdServiceConnection);
        return attributionIdentifiers;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                Utility.logd("android_id", exception);
            }
            context.unbindService((ServiceConnection)googleAdServiceConnection);
            return null;
        }
        context.unbindService((ServiceConnection)googleAdServiceConnection);
        throw throwable2222;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static AttributionIdentifiers getAttributionIdentifiers(Context context) {
        void var4_10;
        block16 : {
            block21 : {
                Object object;
                String string;
                block17 : {
                    Object object2;
                    block20 : {
                        block18 : {
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                Log.e((String)TAG, (String)"getAttributionIdentifiers should not be called from the main thread");
                            }
                            if (recentlyFetchedIdentifiers != null && System.currentTimeMillis() - AttributionIdentifiers.recentlyFetchedIdentifiers.fetchTime < 3600000L) {
                                return recentlyFetchedIdentifiers;
                            }
                            object = AttributionIdentifiers.getAndroidId(context);
                            string = null;
                            try {
                                object2 = context.getPackageManager().resolveContentProvider(ATTRIBUTION_ID_CONTENT_PROVIDER, 0) != null ? Uri.parse((String)"content://com.facebook.katana.provider.AttributionIdProvider") : (context.getPackageManager().resolveContentProvider(ATTRIBUTION_ID_CONTENT_PROVIDER_WAKIZASHI, 0) != null ? Uri.parse((String)"content://com.facebook.wakizashi.provider.AttributionIdProvider") : null);
                            }
                            catch (Throwable throwable) {
                                context = string;
                                break block16;
                            }
                            catch (Exception exception) {
                                context = null;
                                break block17;
                            }
                            String string2 = AttributionIdentifiers.getInstallerPackageName(context);
                            if (string2 != null) {
                                object.androidInstallerPackage = string2;
                            }
                            if (object2 == null) {
                                return AttributionIdentifiers.cacheAndReturnIdentifiers((AttributionIdentifiers)object);
                            }
                            if ((context = context.getContentResolver().query(object2, new String[]{ATTRIBUTION_ID_COLUMN_NAME, ANDROID_ID_COLUMN_NAME, LIMIT_TRACKING_COLUMN_NAME}, null, null, null)) != null) {
                                block19 : {
                                    if (!context.moveToFirst()) break block18;
                                    int n = context.getColumnIndex(ATTRIBUTION_ID_COLUMN_NAME);
                                    int n2 = context.getColumnIndex(ANDROID_ID_COLUMN_NAME);
                                    int n3 = context.getColumnIndex(LIMIT_TRACKING_COLUMN_NAME);
                                    object.attributionId = context.getString(n);
                                    if (n2 > 0 && n3 > 0 && object.getAndroidAdvertiserId() == null) {
                                        object.androidAdvertiserId = context.getString(n2);
                                        object.limitTracking = Boolean.parseBoolean(context.getString(n3));
                                    }
                                    if (context == null) break block19;
                                    context.close();
                                }
                                return AttributionIdentifiers.cacheAndReturnIdentifiers((AttributionIdentifiers)object);
                            }
                        }
                        try {
                            object2 = AttributionIdentifiers.cacheAndReturnIdentifiers((AttributionIdentifiers)object);
                            if (context == null) break block20;
                        }
                        catch (Throwable throwable) {
                            break block16;
                        }
                        catch (Exception exception) {
                            break block17;
                        }
                        context.close();
                    }
                    return object2;
                }
                try {
                    void var4_8;
                    string = TAG;
                    object = new StringBuilder();
                    object.append("Caught unexpected exception in getAttributionId(): ");
                    object.append(var4_8.toString());
                    Log.d((String)string, (String)object.toString());
                    if (context == null) break block21;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                context.close();
            }
            return null;
        }
        if (context != null) {
            context.close();
        }
        throw var4_10;
    }

    @Nullable
    private static String getInstallerPackageName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            return packageManager.getInstallerPackageName(context.getPackageName());
        }
        return null;
    }

    public String getAndroidAdvertiserId() {
        return this.androidAdvertiserId;
    }

    public String getAndroidInstallerPackage() {
        return this.androidInstallerPackage;
    }

    public String getAttributionId() {
        return this.attributionId;
    }

    public boolean isTrackingLimited() {
        return this.limitTracking;
    }

    private static final class GoogleAdInfo
    implements IInterface {
        private static final int FIRST_TRANSACTION_CODE = 1;
        private static final int SECOND_TRANSACTION_CODE = 2;
        private IBinder binder;

        GoogleAdInfo(IBinder iBinder) {
            this.binder = iBinder;
        }

        public IBinder asBinder() {
            return this.binder;
        }

        public String getAdvertiserId() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.binder.transact(1, parcel, parcel2, 0);
                parcel2.readException();
                String string = parcel2.readString();
                return string;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        public boolean isTrackingLimited() throws RemoteException {
            Parcel parcel;
            boolean bl;
            Parcel parcel2;
            block3 : {
                parcel = Parcel.obtain();
                parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    bl = true;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
                parcel.writeInt(1);
                this.binder.transact(2, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) break block3;
                bl = false;
            }
            parcel2.recycle();
            parcel.recycle();
            return bl;
        }
    }

    private static final class GoogleAdServiceConnection
    implements ServiceConnection {
        private AtomicBoolean consumed = new AtomicBoolean(false);
        private final BlockingQueue<IBinder> queue = new LinkedBlockingDeque<IBinder>();

        private GoogleAdServiceConnection() {
        }

        public IBinder getBinder() throws InterruptedException {
            if (this.consumed.compareAndSet(true, true)) {
                throw new IllegalStateException("Binder already consumed");
            }
            return this.queue.take();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder == null) return;
            try {
                this.queue.put(iBinder);
                return;
            }
            catch (InterruptedException interruptedException) {
                return;
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

}
