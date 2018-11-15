/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package io.fabric.sdk.android.services.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.AdvertisingInfo;
import io.fabric.sdk.android.services.common.AdvertisingInfoStrategy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class AdvertisingInfoServiceStrategy
implements AdvertisingInfoStrategy {
    public static final String GOOGLE_PLAY_SERVICES_INTENT = "com.google.android.gms.ads.identifier.service.START";
    public static final String GOOGLE_PLAY_SERVICES_INTENT_PACKAGE_NAME = "com.google.android.gms";
    private static final String GOOGLE_PLAY_SERVICE_PACKAGE_NAME = "com.android.vending";
    private final Context context;

    public AdvertisingInfoServiceStrategy(Context context) {
        this.context = context.getApplicationContext();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public AdvertisingInfo getAdvertisingInfo() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Fabric.getLogger().d("Fabric", "AdvertisingInfoServiceStrategy cannot be called on the main thread");
            return null;
        }
        this.context.getPackageManager().getPackageInfo("com.android.vending", 0);
        var2_1 = new AdvertisingConnection();
        var3_5 = new Intent("com.google.android.gms.ads.identifier.service.START");
        var3_5.setPackage("com.google.android.gms");
        var1_8 = this.context.bindService((Intent)var3_5, (ServiceConnection)var2_1, 1);
        if (!var1_8) ** GOTO lbl-1000
lbl-1000: // 3 sources:
        {
            catch (Throwable var2_2) {
                Fabric.getLogger().d("Fabric", "Could not bind to Google Play Service to capture AdvertisingId", var2_2);
                return null;
            }
        }
        var3_5 = new AdvertisingInterface(var2_1.getBinder());
        var3_5 = new AdvertisingInfo(var3_5.getId(), var3_5.isLimitAdTrackingEnabled());
        this.context.unbindService((ServiceConnection)var2_1);
        return var3_5;
lbl-1000: // 1 sources:
        {
            Fabric.getLogger().d("Fabric", "Could not bind to Google Play Service to capture AdvertisingId");
            return null;
            catch (Exception var2_3) {
                Fabric.getLogger().d("Fabric", "Unable to determine if Google Play Services is available", var2_3);
                return null;
            }
            catch (PackageManager.NameNotFoundException var2_4) {}
            Fabric.getLogger().d("Fabric", "Unable to find Google Play Services package name");
            return null;
            {
                block12 : {
                    catch (Throwable var3_6) {
                        break block12;
                    }
                    catch (Exception var3_7) {}
                    {
                        Fabric.getLogger().w("Fabric", "Exception in binding to Google Play Service to capture AdvertisingId", var3_7);
                    }
                    ** try [egrp 5[TRYBLOCK] [7 : 134->166)] { 
lbl34: // 1 sources:
                    this.context.unbindService((ServiceConnection)var2_1);
                    return null;
                }
                this.context.unbindService((ServiceConnection)var2_1);
                throw var3_6;
            }
        }
    }

    private static final class AdvertisingConnection
    implements ServiceConnection {
        private static final int QUEUE_TIMEOUT_IN_MS = 200;
        private final LinkedBlockingQueue<IBinder> queue = new LinkedBlockingQueue(1);
        private boolean retrieved = false;

        private AdvertisingConnection() {
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public IBinder getBinder() {
            if (this.retrieved) {
                Fabric.getLogger().e("Fabric", "getBinder already called");
            }
            this.retrieved = true;
            try {
                return this.queue.poll(200L, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException interruptedException) {
                return null;
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.queue.put(iBinder);
                return;
            }
            catch (InterruptedException interruptedException) {
                return;
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            this.queue.clear();
        }
    }

    private static final class AdvertisingInterface
    implements IInterface {
        public static final String ADVERTISING_ID_SERVICE_INTERFACE_TOKEN = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService";
        private static final int AD_TRANSACTION_CODE_ID = 1;
        private static final int AD_TRANSACTION_CODE_LIMIT_AD_TRACKING = 2;
        private static final int FLAGS_NONE = 0;
        private final IBinder binder;

        public AdvertisingInterface(IBinder iBinder) {
            this.binder = iBinder;
        }

        public IBinder asBinder() {
            return this.binder;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public String getId() throws RemoteException {
            String string;
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(ADVERTISING_ID_SERVICE_INTERFACE_TOKEN);
                this.binder.transact(1, parcel, parcel2, 0);
                parcel2.readException();
                string = parcel2.readString();
            }
            catch (Throwable throwable) {}
            parcel2.recycle();
            parcel.recycle();
            return string;
            parcel2.recycle();
            parcel.recycle();
            throw throwable;
            catch (Exception exception) {}
            {
                Fabric.getLogger().d("Fabric", "Could not get parcel from Google Play Service to capture AdvertisingId");
            }
            parcel2.recycle();
            parcel.recycle();
            return null;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public boolean isLimitAdTrackingEnabled() throws RemoteException {
            boolean bl;
            Parcel parcel;
            Parcel parcel2;
            block5 : {
                parcel = Parcel.obtain();
                parcel2 = Parcel.obtain();
                bl = false;
                try {
                    parcel.writeInterfaceToken(ADVERTISING_ID_SERVICE_INTERFACE_TOKEN);
                    parcel.writeInt(1);
                    this.binder.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n != 0) {
                        bl = true;
                    }
                    break block5;
                }
                catch (Throwable throwable) {}
                parcel2.recycle();
                parcel.recycle();
                throw throwable;
                catch (Exception exception) {}
                Fabric.getLogger().d("Fabric", "Could not get parcel from Google Play Service to capture Advertising limitAdTracking");
            }
            parcel2.recycle();
            parcel.recycle();
            return bl;
        }
    }

}
