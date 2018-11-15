/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcelable
 *  android.os.RemoteException
 */
package android.support.v4.media;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.os.ResultReceiver;

private static class MediaBrowserCompat.ServiceBinderWrapper {
    private Messenger mMessenger;
    private Bundle mRootHints;

    public MediaBrowserCompat.ServiceBinderWrapper(IBinder iBinder, Bundle bundle) {
        this.mMessenger = new Messenger(iBinder);
        this.mRootHints = bundle;
    }

    private void sendRequest(int n, Bundle bundle, Messenger messenger) throws RemoteException {
        Message message = Message.obtain();
        message.what = n;
        message.arg1 = 1;
        message.setData(bundle);
        message.replyTo = messenger;
        this.mMessenger.send(message);
    }

    void addSubscription(String string, IBinder iBinder, Bundle bundle, Messenger messenger) throws RemoteException {
        Bundle bundle2 = new Bundle();
        bundle2.putString("data_media_item_id", string);
        BundleCompat.putBinder(bundle2, "data_callback_token", iBinder);
        bundle2.putBundle("data_options", bundle);
        this.sendRequest(3, bundle2, messenger);
    }

    void connect(Context context, Messenger messenger) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putString("data_package_name", context.getPackageName());
        bundle.putBundle("data_root_hints", this.mRootHints);
        this.sendRequest(1, bundle, messenger);
    }

    void disconnect(Messenger messenger) throws RemoteException {
        this.sendRequest(2, null, messenger);
    }

    void getMediaItem(String string, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putString("data_media_item_id", string);
        bundle.putParcelable("data_result_receiver", (Parcelable)resultReceiver);
        this.sendRequest(5, bundle, messenger);
    }

    void registerCallbackMessenger(Messenger messenger) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putBundle("data_root_hints", this.mRootHints);
        this.sendRequest(6, bundle, messenger);
    }

    void removeSubscription(String string, IBinder iBinder, Messenger messenger) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putString("data_media_item_id", string);
        BundleCompat.putBinder(bundle, "data_callback_token", iBinder);
        this.sendRequest(4, bundle, messenger);
    }

    void search(String string, Bundle bundle, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
        Bundle bundle2 = new Bundle();
        bundle2.putString("data_search_query", string);
        bundle2.putBundle("data_search_extras", bundle);
        bundle2.putParcelable("data_result_receiver", (Parcelable)resultReceiver);
        this.sendRequest(8, bundle2, messenger);
    }

    void sendCustomAction(String string, Bundle bundle, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
        Bundle bundle2 = new Bundle();
        bundle2.putString("data_custom_action", string);
        bundle2.putBundle("data_custom_action_extras", bundle);
        bundle2.putParcelable("data_result_receiver", (Parcelable)resultReceiver);
        this.sendRequest(9, bundle2, messenger);
    }

    void unregisterCallbackMessenger(Messenger messenger) throws RemoteException {
        this.sendRequest(7, null, messenger);
    }
}
