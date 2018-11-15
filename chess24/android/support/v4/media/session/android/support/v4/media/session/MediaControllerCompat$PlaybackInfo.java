/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.media.session;

import android.support.v4.media.session.MediaControllerCompat;

public static final class MediaControllerCompat.PlaybackInfo {
    public static final int PLAYBACK_TYPE_LOCAL = 1;
    public static final int PLAYBACK_TYPE_REMOTE = 2;
    private final int mAudioStream;
    private final int mCurrentVolume;
    private final int mMaxVolume;
    private final int mPlaybackType;
    private final int mVolumeControl;

    MediaControllerCompat.PlaybackInfo(int n, int n2, int n3, int n4, int n5) {
        this.mPlaybackType = n;
        this.mAudioStream = n2;
        this.mVolumeControl = n3;
        this.mMaxVolume = n4;
        this.mCurrentVolume = n5;
    }

    public int getAudioStream() {
        return this.mAudioStream;
    }

    public int getCurrentVolume() {
        return this.mCurrentVolume;
    }

    public int getMaxVolume() {
        return this.mMaxVolume;
    }

    public int getPlaybackType() {
        return this.mPlaybackType;
    }

    public int getVolumeControl() {
        return this.mVolumeControl;
    }
}
