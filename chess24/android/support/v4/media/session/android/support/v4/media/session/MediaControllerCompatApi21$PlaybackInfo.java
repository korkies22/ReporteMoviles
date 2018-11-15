/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.AudioAttributes
 *  android.media.session.MediaController
 *  android.media.session.MediaController$PlaybackInfo
 */
package android.support.v4.media.session;

import android.media.AudioAttributes;
import android.media.session.MediaController;
import android.support.v4.media.session.MediaControllerCompatApi21;

public static class MediaControllerCompatApi21.PlaybackInfo {
    private static final int FLAG_SCO = 4;
    private static final int STREAM_BLUETOOTH_SCO = 6;
    private static final int STREAM_SYSTEM_ENFORCED = 7;

    public static AudioAttributes getAudioAttributes(Object object) {
        return ((MediaController.PlaybackInfo)object).getAudioAttributes();
    }

    public static int getCurrentVolume(Object object) {
        return ((MediaController.PlaybackInfo)object).getCurrentVolume();
    }

    public static int getLegacyAudioStream(Object object) {
        return MediaControllerCompatApi21.PlaybackInfo.toLegacyStreamType(MediaControllerCompatApi21.PlaybackInfo.getAudioAttributes(object));
    }

    public static int getMaxVolume(Object object) {
        return ((MediaController.PlaybackInfo)object).getMaxVolume();
    }

    public static int getPlaybackType(Object object) {
        return ((MediaController.PlaybackInfo)object).getPlaybackType();
    }

    public static int getVolumeControl(Object object) {
        return ((MediaController.PlaybackInfo)object).getVolumeControl();
    }

    private static int toLegacyStreamType(AudioAttributes audioAttributes) {
        if ((audioAttributes.getFlags() & 1) == 1) {
            return 7;
        }
        if ((audioAttributes.getFlags() & 4) == 4) {
            return 6;
        }
        switch (audioAttributes.getUsage()) {
            default: {
                return 3;
            }
            case 13: {
                return 1;
            }
            case 6: {
                return 2;
            }
            case 5: 
            case 7: 
            case 8: 
            case 9: 
            case 10: {
                return 5;
            }
            case 4: {
                return 4;
            }
            case 3: {
                return 8;
            }
            case 2: {
                return 0;
            }
            case 1: 
            case 11: 
            case 12: 
            case 14: 
        }
        return 3;
    }
}
