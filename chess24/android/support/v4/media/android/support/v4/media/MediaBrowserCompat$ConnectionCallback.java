/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v4.media;

import android.os.Build;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatApi21;

public static class MediaBrowserCompat.ConnectionCallback {
    ConnectionCallbackInternal mConnectionCallbackInternal;
    final Object mConnectionCallbackObj;

    public MediaBrowserCompat.ConnectionCallback() {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mConnectionCallbackObj = MediaBrowserCompatApi21.createConnectionCallback(new StubApi21());
            return;
        }
        this.mConnectionCallbackObj = null;
    }

    public void onConnected() {
    }

    public void onConnectionFailed() {
    }

    public void onConnectionSuspended() {
    }

    void setInternalConnectionCallback(ConnectionCallbackInternal connectionCallbackInternal) {
        this.mConnectionCallbackInternal = connectionCallbackInternal;
    }

    static interface ConnectionCallbackInternal {
        public void onConnected();

        public void onConnectionFailed();

        public void onConnectionSuspended();
    }

    private class StubApi21
    implements MediaBrowserCompatApi21.ConnectionCallback {
        StubApi21() {
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

}
