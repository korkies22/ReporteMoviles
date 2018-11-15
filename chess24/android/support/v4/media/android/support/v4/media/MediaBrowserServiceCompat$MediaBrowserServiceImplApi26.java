/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Parcel
 */
package android.support.v4.media;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaBrowserServiceCompatApi21;
import android.support.v4.media.MediaBrowserServiceCompatApi26;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiresApi(value=26)
class MediaBrowserServiceCompat.MediaBrowserServiceImplApi26
extends MediaBrowserServiceCompat.MediaBrowserServiceImplApi23
implements MediaBrowserServiceCompatApi26.ServiceCompatProxy {
    MediaBrowserServiceCompat.MediaBrowserServiceImplApi26() {
        super(MediaBrowserServiceCompat.this);
    }

    @Override
    public Bundle getBrowserRootHints() {
        if (MediaBrowserServiceCompat.this.mCurConnection != null) {
            if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
                return null;
            }
            return new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
        }
        return MediaBrowserServiceCompatApi26.getBrowserRootHints(this.mServiceObj);
    }

    @Override
    void notifyChildrenChangedForFramework(String string, Bundle bundle) {
        if (bundle != null) {
            MediaBrowserServiceCompatApi26.notifyChildrenChanged(this.mServiceObj, string, bundle);
            return;
        }
        super.notifyChildrenChangedForFramework(string, bundle);
    }

    @Override
    public void onCreate() {
        this.mServiceObj = MediaBrowserServiceCompatApi26.createService((Context)MediaBrowserServiceCompat.this, this);
        MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
    }

    @Override
    public void onLoadChildren(String string, MediaBrowserServiceCompatApi26.ResultWrapper object, Bundle bundle) {
        object = new MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>>((Object)string, (MediaBrowserServiceCompatApi26.ResultWrapper)object){
            final /* synthetic */ MediaBrowserServiceCompatApi26.ResultWrapper val$resultWrapper;
            {
                this.val$resultWrapper = resultWrapper;
                super(object);
            }

            @Override
            public void detach() {
                this.val$resultWrapper.detach();
            }

            @Override
            void onResultSent(List<MediaBrowserCompat.MediaItem> object) {
                if (object != null) {
                    ArrayList<MediaBrowserCompat.MediaItem> arrayList = new ArrayList<MediaBrowserCompat.MediaItem>();
                    Iterator<MediaBrowserCompat.MediaItem> iterator = object.iterator();
                    do {
                        object = arrayList;
                        if (iterator.hasNext()) {
                            object = iterator.next();
                            Parcel parcel = Parcel.obtain();
                            object.writeToParcel(parcel, 0);
                            arrayList.add((MediaBrowserCompat.MediaItem)parcel);
                            continue;
                        }
                        break;
                    } while (true);
                } else {
                    object = null;
                }
                this.val$resultWrapper.sendResult((List<Parcel>)object, this.getFlags());
            }
        };
        MediaBrowserServiceCompat.this.onLoadChildren(string, (MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>>)object, bundle);
    }

}
