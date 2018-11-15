/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcelable
 *  android.os.RemoteException
 */
package android.support.v4.media;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

private static class MediaBrowserServiceCompat.ServiceCallbacksCompat
implements MediaBrowserServiceCompat.ServiceCallbacks {
    final Messenger mCallbacks;

    MediaBrowserServiceCompat.ServiceCallbacksCompat(Messenger messenger) {
        this.mCallbacks = messenger;
    }

    private void sendRequest(int n, Bundle bundle) throws RemoteException {
        Message message = Message.obtain();
        message.what = n;
        message.arg1 = 2;
        message.setData(bundle);
        this.mCallbacks.send(message);
    }

    @Override
    public IBinder asBinder() {
        return this.mCallbacks.getBinder();
    }

    @Override
    public void onConnect(String string, MediaSessionCompat.Token token, Bundle bundle) throws RemoteException {
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = new Bundle();
        }
        bundle2.putInt("extra_service_version", 2);
        bundle = new Bundle();
        bundle.putString("data_media_item_id", string);
        bundle.putParcelable("data_media_session_token", (Parcelable)token);
        bundle.putBundle("data_root_hints", bundle2);
        this.sendRequest(1, bundle);
    }

    @Override
    public void onConnectFailed() throws RemoteException {
        this.sendRequest(2, null);
    }

    @Override
    public void onLoadChildren(String arrayList, List<MediaBrowserCompat.MediaItem> list, Bundle bundle) throws RemoteException {
        Bundle bundle2 = new Bundle();
        bundle2.putString("data_media_item_id", (String)((Object)arrayList));
        bundle2.putBundle("data_options", bundle);
        if (list != null) {
            arrayList = list instanceof ArrayList ? (ArrayList)list : new ArrayList<MediaBrowserCompat.MediaItem>(list);
            bundle2.putParcelableArrayList("data_media_item_list", arrayList);
        }
        this.sendRequest(3, bundle2);
    }
}
