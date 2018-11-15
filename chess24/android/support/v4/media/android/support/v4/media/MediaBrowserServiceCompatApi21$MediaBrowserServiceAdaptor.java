/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$MediaItem
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.service.media.MediaBrowserService
 *  android.service.media.MediaBrowserService$BrowserRoot
 *  android.service.media.MediaBrowserService$Result
 */
package android.support.v4.media;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserServiceCompatApi21;
import java.util.List;

static class MediaBrowserServiceCompatApi21.MediaBrowserServiceAdaptor
extends MediaBrowserService {
    final MediaBrowserServiceCompatApi21.ServiceCompatProxy mServiceProxy;

    MediaBrowserServiceCompatApi21.MediaBrowserServiceAdaptor(Context context, MediaBrowserServiceCompatApi21.ServiceCompatProxy serviceCompatProxy) {
        this.attachBaseContext(context);
        this.mServiceProxy = serviceCompatProxy;
    }

    public MediaBrowserService.BrowserRoot onGetRoot(String object, int n, Bundle object2) {
        MediaBrowserServiceCompatApi21.ServiceCompatProxy serviceCompatProxy = this.mServiceProxy;
        object2 = object2 == null ? null : new Bundle(object2);
        if ((object = serviceCompatProxy.onGetRoot((String)object, n, (Bundle)object2)) == null) {
            return null;
        }
        return new MediaBrowserService.BrowserRoot(object.mRootId, object.mExtras);
    }

    public void onLoadChildren(String string, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result) {
        this.mServiceProxy.onLoadChildren(string, new MediaBrowserServiceCompatApi21.ResultWrapper<List<Parcel>>(result));
    }
}
