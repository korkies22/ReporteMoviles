/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.media.AudioManager
 *  android.media.RemoteControlClient
 *  android.media.RemoteControlClient$OnPlaybackPositionUpdateListener
 *  android.os.Handler
 *  android.os.SystemClock
 *  android.util.Log
 */
package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.media.RemoteControlClient;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

@RequiresApi(value=18)
static class MediaSessionCompat.MediaSessionImplApi18
extends MediaSessionCompat.MediaSessionImplBase {
    private static boolean sIsMbrPendingIntentSupported = true;

    MediaSessionCompat.MediaSessionImplApi18(Context context, String string, ComponentName componentName, PendingIntent pendingIntent) {
        super(context, string, componentName, pendingIntent);
    }

    @Override
    int getRccTransportControlFlagsFromActions(long l) {
        int n;
        int n2 = n = super.getRccTransportControlFlagsFromActions(l);
        if ((l & 256L) != 0L) {
            n2 = n | 256;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void registerMediaButtonEventReceiver(PendingIntent pendingIntent, ComponentName componentName) {
        block4 : {
            if (sIsMbrPendingIntentSupported) {
                try {
                    this.mAudioManager.registerMediaButtonEventReceiver(pendingIntent);
                    break block4;
                }
                catch (NullPointerException nullPointerException) {}
                Log.w((String)MediaSessionCompat.TAG, (String)"Unable to register media button event receiver with PendingIntent, falling back to ComponentName.");
                sIsMbrPendingIntentSupported = false;
            }
        }
        if (!sIsMbrPendingIntentSupported) {
            super.registerMediaButtonEventReceiver(pendingIntent, componentName);
        }
    }

    @Override
    public void setCallback(MediaSessionCompat.Callback object, Handler handler) {
        super.setCallback((MediaSessionCompat.Callback)object, handler);
        if (object == null) {
            this.mRcc.setPlaybackPositionUpdateListener(null);
            return;
        }
        object = new RemoteControlClient.OnPlaybackPositionUpdateListener(){

            public void onPlaybackPositionUpdate(long l) {
                MediaSessionImplApi18.this.postToHandler(18, l);
            }
        };
        this.mRcc.setPlaybackPositionUpdateListener((RemoteControlClient.OnPlaybackPositionUpdateListener)object);
    }

    @Override
    void setRccState(PlaybackStateCompat playbackStateCompat) {
        long l = playbackStateCompat.getPosition();
        float f = playbackStateCompat.getPlaybackSpeed();
        long l2 = playbackStateCompat.getLastPositionUpdateTime();
        long l3 = SystemClock.elapsedRealtime();
        long l4 = l;
        if (playbackStateCompat.getState() == 3) {
            long l5 = 0L;
            l4 = l;
            if (l > 0L) {
                l4 = l5;
                if (l2 > 0L) {
                    l4 = l5 = l3 - l2;
                    if (f > 0.0f) {
                        l4 = l5;
                        if (f != 1.0f) {
                            l4 = (long)((float)l5 * f);
                        }
                    }
                }
                l4 = l + l4;
            }
        }
        this.mRcc.setPlaybackState(this.getRccStateFromState(playbackStateCompat.getState()), l4, f);
    }

    @Override
    void unregisterMediaButtonEventReceiver(PendingIntent pendingIntent, ComponentName componentName) {
        if (sIsMbrPendingIntentSupported) {
            this.mAudioManager.unregisterMediaButtonEventReceiver(pendingIntent);
            return;
        }
        super.unregisterMediaButtonEventReceiver(pendingIntent, componentName);
    }

}
