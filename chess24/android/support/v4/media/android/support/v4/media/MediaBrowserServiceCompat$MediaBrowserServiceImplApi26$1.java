/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package android.support.v4.media;

import android.os.Parcel;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaBrowserServiceCompatApi26;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class MediaBrowserServiceCompat.MediaBrowserServiceImplApi26
extends MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>> {
    final /* synthetic */ MediaBrowserServiceCompatApi26.ResultWrapper val$resultWrapper;

    MediaBrowserServiceCompat.MediaBrowserServiceImplApi26(Object object, MediaBrowserServiceCompatApi26.ResultWrapper resultWrapper) {
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
}
