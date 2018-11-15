/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package android.support.v4.media.session;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import java.util.List;

private class MediaControllerCompat.Callback.MessageHandler
extends Handler {
    private static final int MSG_DESTROYED = 8;
    private static final int MSG_EVENT = 1;
    private static final int MSG_SESSION_READY = 13;
    private static final int MSG_UPDATE_CAPTIONING_ENABLED = 11;
    private static final int MSG_UPDATE_EXTRAS = 7;
    private static final int MSG_UPDATE_METADATA = 3;
    private static final int MSG_UPDATE_PLAYBACK_STATE = 2;
    private static final int MSG_UPDATE_QUEUE = 5;
    private static final int MSG_UPDATE_QUEUE_TITLE = 6;
    private static final int MSG_UPDATE_REPEAT_MODE = 9;
    private static final int MSG_UPDATE_SHUFFLE_MODE = 12;
    private static final int MSG_UPDATE_VOLUME = 4;
    boolean mRegistered;

    MediaControllerCompat.Callback.MessageHandler(Looper looper) {
        super(looper);
        this.mRegistered = false;
    }

    public void handleMessage(Message message) {
        if (!this.mRegistered) {
            return;
        }
        switch (message.what) {
            default: {
                return;
            }
            case 13: {
                Callback.this.onSessionReady();
                return;
            }
            case 12: {
                Callback.this.onShuffleModeChanged((Integer)message.obj);
                return;
            }
            case 11: {
                Callback.this.onCaptioningEnabledChanged((Boolean)message.obj);
                return;
            }
            case 9: {
                Callback.this.onRepeatModeChanged((Integer)message.obj);
                return;
            }
            case 8: {
                Callback.this.onSessionDestroyed();
                return;
            }
            case 7: {
                Callback.this.onExtrasChanged((Bundle)message.obj);
                return;
            }
            case 6: {
                Callback.this.onQueueTitleChanged((CharSequence)message.obj);
                return;
            }
            case 5: {
                Callback.this.onQueueChanged((List)message.obj);
                return;
            }
            case 4: {
                Callback.this.onAudioInfoChanged((MediaControllerCompat.PlaybackInfo)message.obj);
                return;
            }
            case 3: {
                Callback.this.onMetadataChanged((MediaMetadataCompat)message.obj);
                return;
            }
            case 2: {
                Callback.this.onPlaybackStateChanged((PlaybackStateCompat)message.obj);
                return;
            }
            case 1: 
        }
        Callback.this.onSessionEvent((String)message.obj, message.getData());
    }
}
