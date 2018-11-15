/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package android.support.v4.media.session;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.session.MediaSessionCompat;

private class MediaSessionCompat.Callback.CallbackHandler
extends Handler {
    private static final int MSG_MEDIA_PLAY_PAUSE_KEY_DOUBLE_TAP_TIMEOUT = 1;

    MediaSessionCompat.Callback.CallbackHandler(Looper looper) {
        super(looper);
    }

    public void handleMessage(Message message) {
        if (message.what == 1) {
            Callback.this.handleMediaPlayPauseKeySingleTapIfPending();
        }
    }
}
