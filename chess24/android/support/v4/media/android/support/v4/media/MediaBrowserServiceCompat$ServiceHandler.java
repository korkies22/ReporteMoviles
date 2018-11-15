/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcelable
 *  android.util.Log
 */
package android.support.v4.media;

import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

private final class MediaBrowserServiceCompat.ServiceHandler
extends Handler {
    private final MediaBrowserServiceCompat.ServiceBinderImpl mServiceBinderImpl;

    MediaBrowserServiceCompat.ServiceHandler() {
        this.mServiceBinderImpl = new MediaBrowserServiceCompat.ServiceBinderImpl(MediaBrowserServiceCompat.this);
    }

    public void handleMessage(Message message) {
        Object object = message.getData();
        switch (message.what) {
            default: {
                object = new StringBuilder();
                object.append("Unhandled message: ");
                object.append((Object)message);
                object.append("\n  Service version: ");
                object.append(2);
                object.append("\n  Client version: ");
                object.append(message.arg1);
                Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                return;
            }
            case 9: {
                this.mServiceBinderImpl.sendCustomAction(object.getString("data_custom_action"), object.getBundle("data_custom_action_extras"), (ResultReceiver)object.getParcelable("data_result_receiver"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(message.replyTo));
                return;
            }
            case 8: {
                this.mServiceBinderImpl.search(object.getString("data_search_query"), object.getBundle("data_search_extras"), (ResultReceiver)object.getParcelable("data_result_receiver"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(message.replyTo));
                return;
            }
            case 7: {
                this.mServiceBinderImpl.unregisterCallbacks(new MediaBrowserServiceCompat.ServiceCallbacksCompat(message.replyTo));
                return;
            }
            case 6: {
                this.mServiceBinderImpl.registerCallbacks(new MediaBrowserServiceCompat.ServiceCallbacksCompat(message.replyTo), object.getBundle("data_root_hints"));
                return;
            }
            case 5: {
                this.mServiceBinderImpl.getMediaItem(object.getString("data_media_item_id"), (ResultReceiver)object.getParcelable("data_result_receiver"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(message.replyTo));
                return;
            }
            case 4: {
                this.mServiceBinderImpl.removeSubscription(object.getString("data_media_item_id"), BundleCompat.getBinder((Bundle)object, "data_callback_token"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(message.replyTo));
                return;
            }
            case 3: {
                this.mServiceBinderImpl.addSubscription(object.getString("data_media_item_id"), BundleCompat.getBinder((Bundle)object, "data_callback_token"), object.getBundle("data_options"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(message.replyTo));
                return;
            }
            case 2: {
                this.mServiceBinderImpl.disconnect(new MediaBrowserServiceCompat.ServiceCallbacksCompat(message.replyTo));
                return;
            }
            case 1: 
        }
        this.mServiceBinderImpl.connect(object.getString("data_package_name"), object.getInt("data_calling_uid"), object.getBundle("data_root_hints"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(message.replyTo));
    }

    public void postOrRun(Runnable runnable) {
        if (Thread.currentThread() == this.getLooper().getThread()) {
            runnable.run();
            return;
        }
        this.post(runnable);
    }

    public boolean sendMessageAtTime(Message message, long l) {
        Bundle bundle = message.getData();
        bundle.setClassLoader(MediaBrowserCompat.class.getClassLoader());
        bundle.putInt("data_calling_uid", Binder.getCallingUid());
        return super.sendMessageAtTime(message, l);
    }
}
