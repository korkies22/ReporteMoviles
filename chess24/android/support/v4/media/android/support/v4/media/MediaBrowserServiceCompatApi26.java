/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$MediaItem
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.service.media.MediaBrowserService
 *  android.service.media.MediaBrowserService$Result
 *  android.util.Log
 */
package android.support.v4.media;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.media.MediaBrowserService;
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaBrowserServiceCompatApi21;
import android.support.v4.media.MediaBrowserServiceCompatApi23;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiresApi(value=26)
class MediaBrowserServiceCompatApi26 {
    private static final String TAG = "MBSCompatApi26";
    private static Field sResultFlags;

    static {
        try {
            sResultFlags = MediaBrowserService.Result.class.getDeclaredField("mFlags");
            sResultFlags.setAccessible(true);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            Log.w((String)TAG, (Throwable)noSuchFieldException);
        }
    }

    MediaBrowserServiceCompatApi26() {
    }

    public static Object createService(Context context, ServiceCompatProxy serviceCompatProxy) {
        return new MediaBrowserServiceAdaptor(context, serviceCompatProxy);
    }

    public static Bundle getBrowserRootHints(Object object) {
        return ((MediaBrowserService)object).getBrowserRootHints();
    }

    public static void notifyChildrenChanged(Object object, String string, Bundle bundle) {
        ((MediaBrowserService)object).notifyChildrenChanged(string, bundle);
    }

    static class MediaBrowserServiceAdaptor
    extends MediaBrowserServiceCompatApi23.MediaBrowserServiceAdaptor {
        MediaBrowserServiceAdaptor(Context context, ServiceCompatProxy serviceCompatProxy) {
            super(context, serviceCompatProxy);
        }

        public void onLoadChildren(String string, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result, Bundle bundle) {
            ((ServiceCompatProxy)this.mServiceProxy).onLoadChildren(string, new ResultWrapper(result), bundle);
        }
    }

    static class ResultWrapper {
        MediaBrowserService.Result mResultObj;

        ResultWrapper(MediaBrowserService.Result result) {
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

    public static interface ServiceCompatProxy
    extends MediaBrowserServiceCompatApi23.ServiceCompatProxy {
        public void onLoadChildren(String var1, ResultWrapper var2, Bundle var3);
    }

}
