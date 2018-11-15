/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Messenger
 *  android.os.Parcel
 */
package android.support.v4.media;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.os.Parcel;
import android.support.annotation.RequiresApi;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatUtils;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaBrowserServiceCompatApi21;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiresApi(value=21)
class MediaBrowserServiceCompat.MediaBrowserServiceImplApi21
implements MediaBrowserServiceCompat.MediaBrowserServiceImpl,
MediaBrowserServiceCompatApi21.ServiceCompatProxy {
    Messenger mMessenger;
    final List<Bundle> mRootExtrasList = new ArrayList<Bundle>();
    Object mServiceObj;

    MediaBrowserServiceCompat.MediaBrowserServiceImplApi21() {
    }

    @Override
    public Bundle getBrowserRootHints() {
        if (this.mMessenger == null) {
            return null;
        }
        if (MediaBrowserServiceCompat.this.mCurConnection == null) {
            throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods");
        }
        if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
            return null;
        }
        return new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
    }

    @Override
    public void notifyChildrenChanged(String string, Bundle bundle) {
        this.notifyChildrenChangedForFramework(string, bundle);
        this.notifyChildrenChangedForCompat(string, bundle);
    }

    void notifyChildrenChangedForCompat(final String string, final Bundle bundle) {
        MediaBrowserServiceCompat.this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                for (Object object : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                    object = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object);
                    List<Pair<IBinder, Bundle>> list = object.subscriptions.get(string);
                    if (list == null) continue;
                    for (Pair pair : list) {
                        if (!MediaBrowserCompatUtils.hasDuplicatedItems(bundle, (Bundle)pair.second)) continue;
                        MediaBrowserServiceCompat.this.performLoadChildren(string, (MediaBrowserServiceCompat.ConnectionRecord)object, (Bundle)pair.second);
                    }
                }
            }
        });
    }

    void notifyChildrenChangedForFramework(String string, Bundle bundle) {
        MediaBrowserServiceCompatApi21.notifyChildrenChanged(this.mServiceObj, string);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return MediaBrowserServiceCompatApi21.onBind(this.mServiceObj, intent);
    }

    @Override
    public void onCreate() {
        this.mServiceObj = MediaBrowserServiceCompatApi21.createService((Context)MediaBrowserServiceCompat.this, this);
        MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
    }

    @Override
    public MediaBrowserServiceCompatApi21.BrowserRoot onGetRoot(String string, int n, Bundle object) {
        IMediaSession iMediaSession;
        if (object != null && object.getInt("extra_client_version", 0) != 0) {
            object.remove("extra_client_version");
            this.mMessenger = new Messenger((Handler)MediaBrowserServiceCompat.this.mHandler);
            Bundle bundle = new Bundle();
            bundle.putInt("extra_service_version", 2);
            BundleCompat.putBinder(bundle, "extra_messenger", this.mMessenger.getBinder());
            if (MediaBrowserServiceCompat.this.mSession != null) {
                iMediaSession = MediaBrowserServiceCompat.this.mSession.getExtraBinder();
                iMediaSession = iMediaSession == null ? null : iMediaSession.asBinder();
                BundleCompat.putBinder(bundle, "extra_session_binder", (IBinder)iMediaSession);
                iMediaSession = bundle;
            } else {
                this.mRootExtrasList.add(bundle);
                iMediaSession = bundle;
            }
        } else {
            iMediaSession = null;
        }
        object = MediaBrowserServiceCompat.this.onGetRoot(string, n, (Bundle)object);
        if (object == null) {
            return null;
        }
        if (iMediaSession == null) {
            string = object.getExtras();
        } else {
            string = iMediaSession;
            if (object.getExtras() != null) {
                iMediaSession.putAll(object.getExtras());
                string = iMediaSession;
            }
        }
        return new MediaBrowserServiceCompatApi21.BrowserRoot(object.getRootId(), (Bundle)string);
    }

    @Override
    public void onLoadChildren(String string, final MediaBrowserServiceCompatApi21.ResultWrapper<List<Parcel>> object) {
        object = new MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>>((Object)string){

            @Override
            public void detach() {
                object.detach();
            }

            @Override
            void onResultSent(List<MediaBrowserCompat.MediaItem> object2) {
                if (object2 != null) {
                    ArrayList<MediaBrowserCompat.MediaItem> arrayList = new ArrayList<MediaBrowserCompat.MediaItem>();
                    Iterator<MediaBrowserCompat.MediaItem> iterator = object2.iterator();
                    do {
                        object2 = arrayList;
                        if (iterator.hasNext()) {
                            object2 = iterator.next();
                            Parcel parcel = Parcel.obtain();
                            object2.writeToParcel(parcel, 0);
                            arrayList.add((MediaBrowserCompat.MediaItem)parcel);
                            continue;
                        }
                        break;
                    } while (true);
                } else {
                    object2 = null;
                }
                object.sendResult(object2);
            }
        };
        MediaBrowserServiceCompat.this.onLoadChildren(string, (MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>>)object);
    }

    @Override
    public void setSessionToken(final MediaSessionCompat.Token token) {
        MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

            @Override
            public void run() {
                if (!MediaBrowserServiceImplApi21.this.mRootExtrasList.isEmpty()) {
                    IMediaSession iMediaSession = token.getExtraBinder();
                    if (iMediaSession != null) {
                        Iterator<Bundle> iterator = MediaBrowserServiceImplApi21.this.mRootExtrasList.iterator();
                        while (iterator.hasNext()) {
                            BundleCompat.putBinder(iterator.next(), "extra_session_binder", iMediaSession.asBinder());
                        }
                    }
                    MediaBrowserServiceImplApi21.this.mRootExtrasList.clear();
                }
                MediaBrowserServiceCompatApi21.setSessionToken(MediaBrowserServiceImplApi21.this.mServiceObj, token.getToken());
            }
        });
    }

}
