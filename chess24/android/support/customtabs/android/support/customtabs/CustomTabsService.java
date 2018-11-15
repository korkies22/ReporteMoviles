/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.RemoteException
 */
package android.support.customtabs;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.customtabs.CustomTabsSessionToken;
import android.support.customtabs.ICustomTabsCallback;
import android.support.customtabs.ICustomTabsService;
import android.support.v4.util.ArrayMap;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class CustomTabsService
extends Service {
    public static final String ACTION_CUSTOM_TABS_CONNECTION = "android.support.customtabs.action.CustomTabsService";
    public static final String KEY_URL = "android.support.customtabs.otherurls.URL";
    public static final int RELATION_HANDLE_ALL_URLS = 2;
    public static final int RELATION_USE_AS_ORIGIN = 1;
    public static final int RESULT_FAILURE_DISALLOWED = -1;
    public static final int RESULT_FAILURE_MESSAGING_ERROR = -3;
    public static final int RESULT_FAILURE_REMOTE_ERROR = -2;
    public static final int RESULT_SUCCESS = 0;
    private ICustomTabsService.Stub mBinder = new ICustomTabsService.Stub(){

        @Override
        public Bundle extraCommand(String string, Bundle bundle) {
            return CustomTabsService.this.extraCommand(string, bundle);
        }

        @Override
        public boolean mayLaunchUrl(ICustomTabsCallback iCustomTabsCallback, Uri uri, Bundle bundle, List<Bundle> list) {
            return CustomTabsService.this.mayLaunchUrl(new CustomTabsSessionToken(iCustomTabsCallback), uri, bundle, list);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public boolean newSession(ICustomTabsCallback iCustomTabsCallback) {
            IBinder.DeathRecipient deathRecipient;
            final CustomTabsSessionToken customTabsSessionToken = new CustomTabsSessionToken(iCustomTabsCallback);
            try {
                deathRecipient = new IBinder.DeathRecipient(){

                    public void binderDied() {
                        CustomTabsService.this.cleanUpSession(customTabsSessionToken);
                    }
                };
                Map map = CustomTabsService.this.mDeathRecipientMap;
                // MONITORENTER : map
            }
            catch (RemoteException remoteException) {
                return false;
            }
            iCustomTabsCallback.asBinder().linkToDeath(deathRecipient, 0);
            CustomTabsService.this.mDeathRecipientMap.put(iCustomTabsCallback.asBinder(), deathRecipient);
            // MONITOREXIT : map
            return CustomTabsService.this.newSession(customTabsSessionToken);
        }

        @Override
        public int postMessage(ICustomTabsCallback iCustomTabsCallback, String string, Bundle bundle) {
            return CustomTabsService.this.postMessage(new CustomTabsSessionToken(iCustomTabsCallback), string, bundle);
        }

        @Override
        public boolean requestPostMessageChannel(ICustomTabsCallback iCustomTabsCallback, Uri uri) {
            return CustomTabsService.this.requestPostMessageChannel(new CustomTabsSessionToken(iCustomTabsCallback), uri);
        }

        @Override
        public boolean updateVisuals(ICustomTabsCallback iCustomTabsCallback, Bundle bundle) {
            return CustomTabsService.this.updateVisuals(new CustomTabsSessionToken(iCustomTabsCallback), bundle);
        }

        @Override
        public boolean validateRelationship(ICustomTabsCallback iCustomTabsCallback, int n, Uri uri, Bundle bundle) {
            return CustomTabsService.this.validateRelationship(new CustomTabsSessionToken(iCustomTabsCallback), n, uri, bundle);
        }

        @Override
        public boolean warmup(long l) {
            return CustomTabsService.this.warmup(l);
        }

    };
    private final Map<IBinder, IBinder.DeathRecipient> mDeathRecipientMap = new ArrayMap<IBinder, IBinder.DeathRecipient>();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected boolean cleanUpSession(CustomTabsSessionToken customTabsSessionToken) {
        try {
            Map<IBinder, IBinder.DeathRecipient> map = this.mDeathRecipientMap;
            // MONITORENTER : map
        }
        catch (NoSuchElementException noSuchElementException) {
            return false;
        }
        customTabsSessionToken = customTabsSessionToken.getCallbackBinder();
        customTabsSessionToken.unlinkToDeath(this.mDeathRecipientMap.get(customTabsSessionToken), 0);
        this.mDeathRecipientMap.remove(customTabsSessionToken);
        // MONITOREXIT : map
        return true;
    }

    protected abstract Bundle extraCommand(String var1, Bundle var2);

    protected abstract boolean mayLaunchUrl(CustomTabsSessionToken var1, Uri var2, Bundle var3, List<Bundle> var4);

    protected abstract boolean newSession(CustomTabsSessionToken var1);

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    protected abstract int postMessage(CustomTabsSessionToken var1, String var2, Bundle var3);

    protected abstract boolean requestPostMessageChannel(CustomTabsSessionToken var1, Uri var2);

    protected abstract boolean updateVisuals(CustomTabsSessionToken var1, Bundle var2);

    protected abstract boolean validateRelationship(CustomTabsSessionToken var1, int var2, Uri var3, Bundle var4);

    protected abstract boolean warmup(long var1);

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Relation {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Result {
    }

}
