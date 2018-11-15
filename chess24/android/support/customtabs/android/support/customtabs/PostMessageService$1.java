/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.RemoteException
 */
package android.support.customtabs;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.customtabs.ICustomTabsCallback;
import android.support.customtabs.IPostMessageService;

class PostMessageService
extends IPostMessageService.Stub {
    PostMessageService() {
    }

    @Override
    public void onMessageChannelReady(ICustomTabsCallback iCustomTabsCallback, Bundle bundle) throws RemoteException {
        iCustomTabsCallback.onMessageChannelReady(bundle);
    }

    @Override
    public void onPostMessage(ICustomTabsCallback iCustomTabsCallback, String string, Bundle bundle) throws RemoteException {
        iCustomTabsCallback.onPostMessage(string, bundle);
    }
}
