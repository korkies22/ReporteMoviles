/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.RemoteException
 */
package android.support.customtabs;

import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.customtabs.CustomTabsSessionToken;
import android.support.customtabs.ICustomTabsCallback;
import android.support.customtabs.ICustomTabsService;
import java.util.List;
import java.util.Map;

class CustomTabsService
extends ICustomTabsService.Stub {
    CustomTabsService() {
    }

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

}
