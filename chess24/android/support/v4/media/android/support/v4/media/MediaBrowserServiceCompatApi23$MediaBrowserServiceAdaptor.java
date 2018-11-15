/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$MediaItem
 *  android.os.Parcel
 *  android.service.media.MediaBrowserService
 *  android.service.media.MediaBrowserService$Result
 */
package android.support.v4.media;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserServiceCompatApi21;
import android.support.v4.media.MediaBrowserServiceCompatApi23;

static class MediaBrowserServiceCompatApi23.MediaBrowserServiceAdaptor
extends MediaBrowserServiceCompatApi21.MediaBrowserServiceAdaptor {
    MediaBrowserServiceCompatApi23.MediaBrowserServiceAdaptor(Context context, MediaBrowserServiceCompatApi23.ServiceCompatProxy serviceCompatProxy) {
        super(context, serviceCompatProxy);
    }

    public void onLoadItem(String string, MediaBrowserService.Result<MediaBrowser.MediaItem> result) {
        ((MediaBrowserServiceCompatApi23.ServiceCompatProxy)this.mServiceProxy).onLoadItem(string, new MediaBrowserServiceCompatApi21.ResultWrapper<Parcel>(result));
    }
}
