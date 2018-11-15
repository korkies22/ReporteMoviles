/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$ConnectionCallback
 */
package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.support.v4.media.MediaBrowserCompatApi21;

static class MediaBrowserCompatApi21.ConnectionCallbackProxy<T extends MediaBrowserCompatApi21.ConnectionCallback>
extends MediaBrowser.ConnectionCallback {
    protected final T mConnectionCallback;

    public MediaBrowserCompatApi21.ConnectionCallbackProxy(T t) {
        this.mConnectionCallback = t;
    }

    public void onConnected() {
        this.mConnectionCallback.onConnected();
    }

    public void onConnectionFailed() {
        this.mConnectionCallback.onConnectionFailed();
    }

    public void onConnectionSuspended() {
        this.mConnectionCallback.onConnectionSuspended();
    }
}
