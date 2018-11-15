/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.RemoteException
 *  android.text.TextUtils
 */
package android.support.customtabs;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.customtabs.ICustomTabsCallback;
import android.support.customtabs.ICustomTabsService;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CustomTabsClient {
    private final ICustomTabsService mService;
    private final ComponentName mServiceComponentName;

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    CustomTabsClient(ICustomTabsService iCustomTabsService, ComponentName componentName) {
        this.mService = iCustomTabsService;
        this.mServiceComponentName = componentName;
    }

    public static boolean bindCustomTabsService(Context context, String string, CustomTabsServiceConnection customTabsServiceConnection) {
        Intent intent = new Intent("android.support.customtabs.action.CustomTabsService");
        if (!TextUtils.isEmpty((CharSequence)string)) {
            intent.setPackage(string);
        }
        return context.bindService(intent, (ServiceConnection)customTabsServiceConnection, 33);
    }

    public static boolean connectAndInitialize(final Context context, String string) {
        if (string == null) {
            return false;
        }
        context = context.getApplicationContext();
        CustomTabsServiceConnection customTabsServiceConnection = new CustomTabsServiceConnection(){

            @Override
            public final void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                customTabsClient.warmup(0L);
                context.unbindService((ServiceConnection)this);
            }

            public final void onServiceDisconnected(ComponentName componentName) {
            }
        };
        try {
            boolean bl = CustomTabsClient.bindCustomTabsService(context, string, customTabsServiceConnection);
            return bl;
        }
        catch (SecurityException securityException) {
            return false;
        }
    }

    public static String getPackageName(Context context, @Nullable List<String> list) {
        return CustomTabsClient.getPackageName(context, list, false);
    }

    public static String getPackageName(Context object, @Nullable List<String> object2, boolean bl) {
        PackageManager packageManager = object.getPackageManager();
        object = object2 == null ? new ArrayList() : object2;
        Object object3 = new Intent("android.intent.action.VIEW", Uri.parse((String)"http://"));
        Object object4 = object;
        if (!bl) {
            object3 = packageManager.resolveActivity(object3, 0);
            object4 = object;
            if (object3 != null) {
                object3 = object3.activityInfo.packageName;
                object4 = new ArrayList(object.size() + 1);
                object4.add(object3);
                if (object2 != null) {
                    object4.addAll(object2);
                }
            }
        }
        object = new Intent("android.support.customtabs.action.CustomTabsService");
        object2 = object4.iterator();
        while (object2.hasNext()) {
            object4 = (String)object2.next();
            object.setPackage((String)object4);
            if (packageManager.resolveService((Intent)object, 0) == null) continue;
            return object4;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Bundle extraCommand(String string, Bundle bundle) {
        try {
            return this.mService.extraCommand(string, bundle);
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public CustomTabsSession newSession(CustomTabsCallback object) {
        block2 : {
            object = new ICustomTabsCallback.Stub((CustomTabsCallback)object){
                private Handler mHandler = new Handler(Looper.getMainLooper());
                final /* synthetic */ CustomTabsCallback val$callback;
                {
                    this.val$callback = customTabsCallback;
                }

                @Override
                public void extraCallback(final String string, final Bundle bundle) throws RemoteException {
                    if (this.val$callback == null) {
                        return;
                    }
                    this.mHandler.post(new Runnable(){

                        @Override
                        public void run() {
                            2.this.val$callback.extraCallback(string, bundle);
                        }
                    });
                }

                @Override
                public void onMessageChannelReady(final Bundle bundle) throws RemoteException {
                    if (this.val$callback == null) {
                        return;
                    }
                    this.mHandler.post(new Runnable(){

                        @Override
                        public void run() {
                            2.this.val$callback.onMessageChannelReady(bundle);
                        }
                    });
                }

                @Override
                public void onNavigationEvent(final int n, final Bundle bundle) {
                    if (this.val$callback == null) {
                        return;
                    }
                    this.mHandler.post(new Runnable(){

                        @Override
                        public void run() {
                            2.this.val$callback.onNavigationEvent(n, bundle);
                        }
                    });
                }

                @Override
                public void onPostMessage(final String string, final Bundle bundle) throws RemoteException {
                    if (this.val$callback == null) {
                        return;
                    }
                    this.mHandler.post(new Runnable(){

                        @Override
                        public void run() {
                            2.this.val$callback.onPostMessage(string, bundle);
                        }
                    });
                }

                @Override
                public void onRelationshipValidationResult(final int n, final Uri uri, final boolean bl, final @Nullable Bundle bundle) throws RemoteException {
                    if (this.val$callback == null) {
                        return;
                    }
                    this.mHandler.post(new Runnable(){

                        @Override
                        public void run() {
                            2.this.val$callback.onRelationshipValidationResult(n, uri, bl, bundle);
                        }
                    });
                }

            };
            try {
                boolean bl = this.mService.newSession((ICustomTabsCallback)object);
                if (bl) break block2;
                return null;
            }
            catch (RemoteException remoteException) {
                return null;
            }
        }
        return new CustomTabsSession(this.mService, (ICustomTabsCallback)object, this.mServiceComponentName);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean warmup(long l) {
        try {
            return this.mService.warmup(l);
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

}
