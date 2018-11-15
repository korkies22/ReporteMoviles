/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$MediaItem
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.service.media.MediaBrowserService
 *  android.service.media.MediaBrowserService$Result
 *  android.util.Log
 */
package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserServiceCompatApi26;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

static class MediaBrowserServiceCompatApi26.ResultWrapper {
    MediaBrowserService.Result mResultObj;

    MediaBrowserServiceCompatApi26.ResultWrapper(MediaBrowserService.Result result) {
        this.mResultObj = result;
    }

    public void detach() {
        this.mResultObj.detach();
    }

    List<MediaBrowser.MediaItem> parcelListToItemList(List<Parcel> object) {
        if (object == null) {
            return null;
        }
        ArrayList<MediaBrowser.MediaItem> arrayList = new ArrayList<MediaBrowser.MediaItem>();
        object = object.iterator();
        while (object.hasNext()) {
            Parcel parcel = (Parcel)object.next();
            parcel.setDataPosition(0);
            arrayList.add((MediaBrowser.MediaItem)MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
            parcel.recycle();
        }
        return arrayList;
    }

    public void sendResult(List<Parcel> list, int n) {
        try {
            sResultFlags.setInt((Object)this.mResultObj, n);
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.w((String)MediaBrowserServiceCompatApi26.TAG, (Throwable)illegalAccessException);
        }
        this.mResultObj.sendResult(this.parcelListToItemList(list));
    }
}
