/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Parcel
 */
package android.support.v4.media;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaBrowserServiceCompatApi21;
import android.support.v4.media.MediaBrowserServiceCompatApi23;

@RequiresApi(value=23)
class MediaBrowserServiceCompat.MediaBrowserServiceImplApi23
extends MediaBrowserServiceCompat.MediaBrowserServiceImplApi21
implements MediaBrowserServiceCompatApi23.ServiceCompatProxy {
    MediaBrowserServiceCompat.MediaBrowserServiceImplApi23() {
        super(MediaBrowserServiceCompat.this);
    }

    @Override
    public void onCreate() {
        this.mServiceObj = MediaBrowserServiceCompatApi23.createService((Context)MediaBrowserServiceCompat.this, this);
        MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
    }

    @Override
    public void onLoadItem(String string, final MediaBrowserServiceCompatApi21.ResultWrapper<Parcel> object) {
        object = new MediaBrowserServiceCompat.Result<MediaBrowserCompat.MediaItem>((Object)string){

            @Override
            public void detach() {
                object.detach();
            }

            @Override
            void onResultSent(MediaBrowserCompat.MediaItem mediaItem) {
                if (mediaItem == null) {
                    object.sendResult(null);
                    return;
                }
                Parcel parcel = Parcel.obtain();
                mediaItem.writeToParcel(parcel, 0);
                object.sendResult(parcel);
            }
        };
        MediaBrowserServiceCompat.this.onLoadItem(string, (MediaBrowserServiceCompat.Result<MediaBrowserCompat.MediaItem>)object);
    }

}
