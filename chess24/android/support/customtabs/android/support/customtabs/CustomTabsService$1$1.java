/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 */
package android.support.customtabs;

import android.os.IBinder;
import android.support.customtabs.CustomTabsService;
import android.support.customtabs.CustomTabsSessionToken;

class CustomTabsService
implements IBinder.DeathRecipient {
    final /* synthetic */ CustomTabsSessionToken val$sessionToken;

    CustomTabsService(CustomTabsSessionToken customTabsSessionToken) {
        this.val$sessionToken = customTabsSessionToken;
    }

    public void binderDied() {
        1.this.this$0.cleanUpSession(this.val$sessionToken);
    }
}
