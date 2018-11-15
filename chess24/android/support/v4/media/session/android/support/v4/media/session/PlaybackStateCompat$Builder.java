/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.SystemClock
 */
package android.support.v4.media.session;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.media.session.PlaybackStateCompat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public static final class PlaybackStateCompat.Builder {
    private long mActions;
    private long mActiveItemId = -1L;
    private long mBufferedPosition;
    private final List<PlaybackStateCompat.CustomAction> mCustomActions = new ArrayList<PlaybackStateCompat.CustomAction>();
    private int mErrorCode;
    private CharSequence mErrorMessage;
    private Bundle mExtras;
    private long mPosition;
    private float mRate;
    private int mState;
    private long mUpdateTime;

    public PlaybackStateCompat.Builder() {
    }

    public PlaybackStateCompat.Builder(PlaybackStateCompat playbackStateCompat) {
        this.mState = playbackStateCompat.mState;
        this.mPosition = playbackStateCompat.mPosition;
        this.mRate = playbackStateCompat.mSpeed;
        this.mUpdateTime = playbackStateCompat.mUpdateTime;
        this.mBufferedPosition = playbackStateCompat.mBufferedPosition;
        this.mActions = playbackStateCompat.mActions;
        this.mErrorCode = playbackStateCompat.mErrorCode;
        this.mErrorMessage = playbackStateCompat.mErrorMessage;
        if (playbackStateCompat.mCustomActions != null) {
            this.mCustomActions.addAll(playbackStateCompat.mCustomActions);
        }
        this.mActiveItemId = playbackStateCompat.mActiveItemId;
        this.mExtras = playbackStateCompat.mExtras;
    }

    public PlaybackStateCompat.Builder addCustomAction(PlaybackStateCompat.CustomAction customAction) {
        if (customAction == null) {
            throw new IllegalArgumentException("You may not add a null CustomAction to PlaybackStateCompat.");
        }
        this.mCustomActions.add(customAction);
        return this;
    }

    public PlaybackStateCompat.Builder addCustomAction(String string, String string2, int n) {
        return this.addCustomAction(new PlaybackStateCompat.CustomAction(string, string2, n, null));
    }

    public PlaybackStateCompat build() {
        return new PlaybackStateCompat(this.mState, this.mPosition, this.mBufferedPosition, this.mRate, this.mActions, this.mErrorCode, this.mErrorMessage, this.mUpdateTime, this.mCustomActions, this.mActiveItemId, this.mExtras);
    }

    public PlaybackStateCompat.Builder setActions(long l) {
        this.mActions = l;
        return this;
    }

    public PlaybackStateCompat.Builder setActiveQueueItemId(long l) {
        this.mActiveItemId = l;
        return this;
    }

    public PlaybackStateCompat.Builder setBufferedPosition(long l) {
        this.mBufferedPosition = l;
        return this;
    }

    public PlaybackStateCompat.Builder setErrorMessage(int n, CharSequence charSequence) {
        this.mErrorCode = n;
        this.mErrorMessage = charSequence;
        return this;
    }

    public PlaybackStateCompat.Builder setErrorMessage(CharSequence charSequence) {
        this.mErrorMessage = charSequence;
        return this;
    }

    public PlaybackStateCompat.Builder setExtras(Bundle bundle) {
        this.mExtras = bundle;
        return this;
    }

    public PlaybackStateCompat.Builder setState(int n, long l, float f) {
        return this.setState(n, l, f, SystemClock.elapsedRealtime());
    }

    public PlaybackStateCompat.Builder setState(int n, long l, float f, long l2) {
        this.mState = n;
        this.mPosition = l;
        this.mUpdateTime = l2;
        this.mRate = f;
        return this;
    }
}
