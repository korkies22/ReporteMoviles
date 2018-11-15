/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.media;

import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatApi21;

private class MediaBrowserCompat.ConnectionCallback.StubApi21
implements MediaBrowserCompatApi21.ConnectionCallback {
    MediaBrowserCompat.ConnectionCallback.StubApi21() {
    }

    @Override
    public void onConnected() {
        if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
            ConnectionCallback.this.mConnectionCallbackInternal.onConnected();
        }
        ConnectionCallback.this.onConnected();
    }

    @Override
    public void onConnectionFailed() {
        if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
            ConnectionCallback.this.mConnectionCallbackInternal.onConnectionFailed();
        }
        ConnectionCallback.this.onConnectionFailed();
    }

    @Override
    public void onConnectionSuspended() {
        if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
            ConnectionCallback.this.mConnectionCallbackInternal.onConnectionSuspended();
        }
        ConnectionCallback.this.onConnectionSuspended();
    }
}
