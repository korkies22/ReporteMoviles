/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$MediaItem
 *  android.os.Bundle
 *  android.service.media.MediaBrowserService
 *  android.service.media.MediaBrowserService$Result
 */
package android.support.v4.media;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserServiceCompatApi21;
import android.support.v4.media.MediaBrowserServiceCompatApi23;
import android.support.v4.media.MediaBrowserServiceCompatApi26;
import java.util.List;

static class MediaBrowserServiceCompatApi26.MediaBrowserServiceAdaptor
extends MediaBrowserServiceCompatApi23.MediaBrowserServiceAdaptor {
    MediaBrowserServiceCompatApi26.MediaBrowserServiceAdaptor(Context context, MediaBrowserServiceCompatApi26.ServiceCompatProxy serviceCompatProxy) {
        super(context, serviceCompatProxy);
    }

    public void onLoadChildren(String string, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result, Bundle bundle) {
        ((MediaBrowserServiceCompatApi26.ServiceCompatProxy)this.mServiceProxy).onLoadChildren(string, new MediaBrowserServiceCompatApi26.ResultWrapper(result), bundle);
    }
}
