/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.media.AudioManager
 *  android.media.RemoteControlClient
 *  android.media.RemoteControlClient$MetadataEditor
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcelable
 *  android.os.RemoteCallbackList
 *  android.os.RemoteException
 *  android.os.ResultReceiver
 *  android.util.Log
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.RemoteControlClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;
import java.util.List;

static class MediaSessionCompat.MediaSessionImplBase
implements MediaSessionCompat.MediaSessionImpl {
    static final int RCC_PLAYSTATE_NONE = 0;
    final AudioManager mAudioManager;
    volatile MediaSessionCompat.Callback mCallback;
    boolean mCaptioningEnabled;
    private final Context mContext;
    final RemoteCallbackList<IMediaControllerCallback> mControllerCallbacks = new RemoteCallbackList();
    boolean mDestroyed = false;
    Bundle mExtras;
    int mFlags;
    private MessageHandler mHandler;
    boolean mIsActive = false;
    private boolean mIsMbrRegistered = false;
    private boolean mIsRccRegistered = false;
    int mLocalStream;
    final Object mLock = new Object();
    private final ComponentName mMediaButtonReceiverComponentName;
    private final PendingIntent mMediaButtonReceiverIntent;
    MediaMetadataCompat mMetadata;
    final String mPackageName;
    List<MediaSessionCompat.QueueItem> mQueue;
    CharSequence mQueueTitle;
    int mRatingType;
    final RemoteControlClient mRcc;
    int mRepeatMode;
    PendingIntent mSessionActivity;
    int mShuffleMode;
    PlaybackStateCompat mState;
    private final MediaSessionStub mStub;
    final String mTag;
    private final MediaSessionCompat.Token mToken;
    private VolumeProviderCompat.Callback mVolumeCallback = new VolumeProviderCompat.Callback(){

        @Override
        public void onVolumeChanged(VolumeProviderCompat object) {
            if (MediaSessionImplBase.this.mVolumeProvider != object) {
                return;
            }
            object = new ParcelableVolumeInfo(MediaSessionImplBase.this.mVolumeType, MediaSessionImplBase.this.mLocalStream, object.getVolumeControl(), object.getMaxVolume(), object.getCurrentVolume());
            MediaSessionImplBase.this.sendVolumeInfoChanged((ParcelableVolumeInfo)object);
        }
    };
    VolumeProviderCompat mVolumeProvider;
    int mVolumeType;

    public MediaSessionCompat.MediaSessionImplBase(Context context, String string, ComponentName componentName, PendingIntent pendingIntent) {
        if (componentName == null) {
            throw new IllegalArgumentException("MediaButtonReceiver component may not be null.");
        }
        this.mContext = context;
        this.mPackageName = context.getPackageName();
        this.mAudioManager = (AudioManager)context.getSystemService("audio");
        this.mTag = string;
        this.mMediaButtonReceiverComponentName = componentName;
        this.mMediaButtonReceiverIntent = pendingIntent;
        this.mStub = new MediaSessionStub();
        this.mToken = new MediaSessionCompat.Token(this.mStub);
        this.mRatingType = 0;
        this.mVolumeType = 1;
        this.mLocalStream = 3;
        this.mRcc = new RemoteControlClient(pendingIntent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendCaptioningEnabled(boolean bl) {
        int n = this.mControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n < 0) {
                this.mControllerCallbacks.finishBroadcast();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
            try {
                iMediaControllerCallback.onCaptioningEnabledChanged(bl);
            }
            catch (RemoteException remoteException) {}
            --n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendEvent(String string, Bundle bundle) {
        int n = this.mControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n < 0) {
                this.mControllerCallbacks.finishBroadcast();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
            try {
                iMediaControllerCallback.onEvent(string, bundle);
            }
            catch (RemoteException remoteException) {}
            --n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendExtras(Bundle bundle) {
        int n = this.mControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n < 0) {
                this.mControllerCallbacks.finishBroadcast();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
            try {
                iMediaControllerCallback.onExtrasChanged(bundle);
            }
            catch (RemoteException remoteException) {}
            --n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendMetadata(MediaMetadataCompat mediaMetadataCompat) {
        int n = this.mControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n < 0) {
                this.mControllerCallbacks.finishBroadcast();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
            try {
                iMediaControllerCallback.onMetadataChanged(mediaMetadataCompat);
            }
            catch (RemoteException remoteException) {}
            --n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendQueue(List<MediaSessionCompat.QueueItem> list) {
        int n = this.mControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n < 0) {
                this.mControllerCallbacks.finishBroadcast();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
            try {
                iMediaControllerCallback.onQueueChanged(list);
            }
            catch (RemoteException remoteException) {}
            --n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendQueueTitle(CharSequence charSequence) {
        int n = this.mControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n < 0) {
                this.mControllerCallbacks.finishBroadcast();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
            try {
                iMediaControllerCallback.onQueueTitleChanged(charSequence);
            }
            catch (RemoteException remoteException) {}
            --n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendRepeatMode(int n) {
        int n2 = this.mControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n2 < 0) {
                this.mControllerCallbacks.finishBroadcast();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n2);
            try {
                iMediaControllerCallback.onRepeatModeChanged(n);
            }
            catch (RemoteException remoteException) {}
            --n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendSessionDestroyed() {
        int n = this.mControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n < 0) {
                this.mControllerCallbacks.finishBroadcast();
                this.mControllerCallbacks.kill();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
            try {
                iMediaControllerCallback.onSessionDestroyed();
            }
            catch (RemoteException remoteException) {}
            --n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendShuffleMode(int n) {
        int n2 = this.mControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n2 < 0) {
                this.mControllerCallbacks.finishBroadcast();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n2);
            try {
                iMediaControllerCallback.onShuffleModeChanged(n);
            }
            catch (RemoteException remoteException) {}
            --n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendState(PlaybackStateCompat playbackStateCompat) {
        int n = this.mControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n < 0) {
                this.mControllerCallbacks.finishBroadcast();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
            try {
                iMediaControllerCallback.onPlaybackStateChanged(playbackStateCompat);
            }
            catch (RemoteException remoteException) {}
            --n;
        } while (true);
    }

    void adjustVolume(int n, int n2) {
        if (this.mVolumeType == 2) {
            if (this.mVolumeProvider != null) {
                this.mVolumeProvider.onAdjustVolume(n);
                return;
            }
        } else {
            this.mAudioManager.adjustStreamVolume(this.mLocalStream, n, n2);
        }
    }

    RemoteControlClient.MetadataEditor buildRccMetadata(Bundle bundle) {
        RemoteControlClient.MetadataEditor metadataEditor = this.mRcc.editMetadata(true);
        if (bundle == null) {
            return metadataEditor;
        }
        if (bundle.containsKey("android.media.metadata.ART")) {
            Bitmap bitmap;
            Bitmap bitmap2 = bitmap = (Bitmap)bundle.getParcelable("android.media.metadata.ART");
            if (bitmap != null) {
                bitmap2 = bitmap.copy(bitmap.getConfig(), false);
            }
            metadataEditor.putBitmap(100, bitmap2);
        } else if (bundle.containsKey("android.media.metadata.ALBUM_ART")) {
            Bitmap bitmap;
            Bitmap bitmap3 = bitmap = (Bitmap)bundle.getParcelable("android.media.metadata.ALBUM_ART");
            if (bitmap != null) {
                bitmap3 = bitmap.copy(bitmap.getConfig(), false);
            }
            metadataEditor.putBitmap(100, bitmap3);
        }
        if (bundle.containsKey("android.media.metadata.ALBUM")) {
            metadataEditor.putString(1, bundle.getString("android.media.metadata.ALBUM"));
        }
        if (bundle.containsKey("android.media.metadata.ALBUM_ARTIST")) {
            metadataEditor.putString(13, bundle.getString("android.media.metadata.ALBUM_ARTIST"));
        }
        if (bundle.containsKey("android.media.metadata.ARTIST")) {
            metadataEditor.putString(2, bundle.getString("android.media.metadata.ARTIST"));
        }
        if (bundle.containsKey("android.media.metadata.AUTHOR")) {
            metadataEditor.putString(3, bundle.getString("android.media.metadata.AUTHOR"));
        }
        if (bundle.containsKey("android.media.metadata.COMPILATION")) {
            metadataEditor.putString(15, bundle.getString("android.media.metadata.COMPILATION"));
        }
        if (bundle.containsKey("android.media.metadata.COMPOSER")) {
            metadataEditor.putString(4, bundle.getString("android.media.metadata.COMPOSER"));
        }
        if (bundle.containsKey("android.media.metadata.DATE")) {
            metadataEditor.putString(5, bundle.getString("android.media.metadata.DATE"));
        }
        if (bundle.containsKey("android.media.metadata.DISC_NUMBER")) {
            metadataEditor.putLong(14, bundle.getLong("android.media.metadata.DISC_NUMBER"));
        }
        if (bundle.containsKey("android.media.metadata.DURATION")) {
            metadataEditor.putLong(9, bundle.getLong("android.media.metadata.DURATION"));
        }
        if (bundle.containsKey("android.media.metadata.GENRE")) {
            metadataEditor.putString(6, bundle.getString("android.media.metadata.GENRE"));
        }
        if (bundle.containsKey("android.media.metadata.TITLE")) {
            metadataEditor.putString(7, bundle.getString("android.media.metadata.TITLE"));
        }
        if (bundle.containsKey("android.media.metadata.TRACK_NUMBER")) {
            metadataEditor.putLong(0, bundle.getLong("android.media.metadata.TRACK_NUMBER"));
        }
        if (bundle.containsKey("android.media.metadata.WRITER")) {
            metadataEditor.putString(11, bundle.getString("android.media.metadata.WRITER"));
        }
        return metadataEditor;
    }

    @Override
    public String getCallingPackage() {
        return null;
    }

    @Override
    public Object getMediaSession() {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public PlaybackStateCompat getPlaybackState() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mState;
        }
    }

    int getRccStateFromState(int n) {
        switch (n) {
            default: {
                return -1;
            }
            case 10: 
            case 11: {
                return 6;
            }
            case 9: {
                return 7;
            }
            case 7: {
                return 9;
            }
            case 6: 
            case 8: {
                return 8;
            }
            case 5: {
                return 5;
            }
            case 4: {
                return 4;
            }
            case 3: {
                return 3;
            }
            case 2: {
                return 2;
            }
            case 1: {
                return 1;
            }
            case 0: 
        }
        return 0;
    }

    int getRccTransportControlFlagsFromActions(long l) {
        int n = (l & 1L) != 0L ? 32 : 0;
        int n2 = n;
        if ((l & 2L) != 0L) {
            n2 = n | 16;
        }
        n = n2;
        if ((l & 4L) != 0L) {
            n = n2 | 4;
        }
        n2 = n;
        if ((l & 8L) != 0L) {
            n2 = n | 2;
        }
        n = n2;
        if ((l & 16L) != 0L) {
            n = n2 | 1;
        }
        n2 = n;
        if ((l & 32L) != 0L) {
            n2 = n | 128;
        }
        n = n2;
        if ((l & 64L) != 0L) {
            n = n2 | 64;
        }
        n2 = n;
        if ((l & 512L) != 0L) {
            n2 = n | 8;
        }
        return n2;
    }

    @Override
    public Object getRemoteControlClient() {
        return null;
    }

    @Override
    public MediaSessionCompat.Token getSessionToken() {
        return this.mToken;
    }

    @Override
    public boolean isActive() {
        return this.mIsActive;
    }

    void postToHandler(int n) {
        this.postToHandler(n, null);
    }

    void postToHandler(int n, int n2) {
        this.postToHandler(n, null, n2);
    }

    void postToHandler(int n, Object object) {
        this.postToHandler(n, object, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void postToHandler(int n, Object object, int n2) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mHandler != null) {
                this.mHandler.post(n, object, n2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void postToHandler(int n, Object object, Bundle bundle) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mHandler != null) {
                this.mHandler.post(n, object, bundle);
            }
            return;
        }
    }

    void registerMediaButtonEventReceiver(PendingIntent pendingIntent, ComponentName componentName) {
        this.mAudioManager.registerMediaButtonEventReceiver(componentName);
    }

    @Override
    public void release() {
        this.mIsActive = false;
        this.mDestroyed = true;
        this.update();
        this.sendSessionDestroyed();
    }

    @Override
    public void sendSessionEvent(String string, Bundle bundle) {
        this.sendEvent(string, bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void sendVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
        int n = this.mControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n < 0) {
                this.mControllerCallbacks.finishBroadcast();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
            try {
                iMediaControllerCallback.onVolumeInfoChanged(parcelableVolumeInfo);
            }
            catch (RemoteException remoteException) {}
            --n;
        } while (true);
    }

    @Override
    public void setActive(boolean bl) {
        if (bl == this.mIsActive) {
            return;
        }
        this.mIsActive = bl;
        if (this.update()) {
            this.setMetadata(this.mMetadata);
            this.setPlaybackState(this.mState);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setCallback(MediaSessionCompat.Callback callback, Handler object) {
        this.mCallback = callback;
        if (callback == null) {
            return;
        }
        callback = object;
        if (object == null) {
            callback = new Handler();
        }
        object = this.mLock;
        synchronized (object) {
            if (this.mHandler != null) {
                this.mHandler.removeCallbacksAndMessages(null);
            }
            this.mHandler = new MessageHandler(callback.getLooper());
            this.mCallback.setSessionImpl(this, (Handler)callback);
            return;
        }
    }

    @Override
    public void setCaptioningEnabled(boolean bl) {
        if (this.mCaptioningEnabled != bl) {
            this.mCaptioningEnabled = bl;
            this.sendCaptioningEnabled(bl);
        }
    }

    @Override
    public void setExtras(Bundle bundle) {
        this.mExtras = bundle;
        this.sendExtras(bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setFlags(int n) {
        Object object = this.mLock;
        synchronized (object) {
            this.mFlags = n;
        }
        this.update();
    }

    @Override
    public void setMediaButtonReceiver(PendingIntent pendingIntent) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setMetadata(MediaMetadataCompat object) {
        MediaMetadataCompat mediaMetadataCompat = object;
        if (object != null) {
            mediaMetadataCompat = new MediaMetadataCompat.Builder((MediaMetadataCompat)object, MediaSessionCompat.sMaxBitmapSize).build();
        }
        object = this.mLock;
        synchronized (object) {
            this.mMetadata = mediaMetadataCompat;
        }
        this.sendMetadata(mediaMetadataCompat);
        if (!this.mIsActive) {
            return;
        }
        object = mediaMetadataCompat == null ? null : mediaMetadataCompat.getBundle();
        this.buildRccMetadata((Bundle)object).apply();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
        Object object = this.mLock;
        synchronized (object) {
            this.mState = playbackStateCompat;
        }
        this.sendState(playbackStateCompat);
        if (!this.mIsActive) {
            return;
        }
        if (playbackStateCompat == null) {
            this.mRcc.setPlaybackState(0);
            this.mRcc.setTransportControlFlags(0);
            return;
        }
        this.setRccState(playbackStateCompat);
        this.mRcc.setTransportControlFlags(this.getRccTransportControlFlagsFromActions(playbackStateCompat.getActions()));
    }

    @Override
    public void setPlaybackToLocal(int n) {
        if (this.mVolumeProvider != null) {
            this.mVolumeProvider.setCallback(null);
        }
        this.mVolumeType = 1;
        this.sendVolumeInfoChanged(new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, 2, this.mAudioManager.getStreamMaxVolume(this.mLocalStream), this.mAudioManager.getStreamVolume(this.mLocalStream)));
    }

    @Override
    public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
        if (volumeProviderCompat == null) {
            throw new IllegalArgumentException("volumeProvider may not be null");
        }
        if (this.mVolumeProvider != null) {
            this.mVolumeProvider.setCallback(null);
        }
        this.mVolumeType = 2;
        this.mVolumeProvider = volumeProviderCompat;
        this.sendVolumeInfoChanged(new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, this.mVolumeProvider.getVolumeControl(), this.mVolumeProvider.getMaxVolume(), this.mVolumeProvider.getCurrentVolume()));
        volumeProviderCompat.setCallback(this.mVolumeCallback);
    }

    @Override
    public void setQueue(List<MediaSessionCompat.QueueItem> list) {
        this.mQueue = list;
        this.sendQueue(list);
    }

    @Override
    public void setQueueTitle(CharSequence charSequence) {
        this.mQueueTitle = charSequence;
        this.sendQueueTitle(charSequence);
    }

    @Override
    public void setRatingType(int n) {
        this.mRatingType = n;
    }

    void setRccState(PlaybackStateCompat playbackStateCompat) {
        this.mRcc.setPlaybackState(this.getRccStateFromState(playbackStateCompat.getState()));
    }

    @Override
    public void setRepeatMode(int n) {
        if (this.mRepeatMode != n) {
            this.mRepeatMode = n;
            this.sendRepeatMode(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setSessionActivity(PendingIntent pendingIntent) {
        Object object = this.mLock;
        synchronized (object) {
            this.mSessionActivity = pendingIntent;
            return;
        }
    }

    @Override
    public void setShuffleMode(int n) {
        if (this.mShuffleMode != n) {
            this.mShuffleMode = n;
            this.sendShuffleMode(n);
        }
    }

    void setVolumeTo(int n, int n2) {
        if (this.mVolumeType == 2) {
            if (this.mVolumeProvider != null) {
                this.mVolumeProvider.onSetVolumeTo(n);
                return;
            }
        } else {
            this.mAudioManager.setStreamVolume(this.mLocalStream, n, n2);
        }
    }

    void unregisterMediaButtonEventReceiver(PendingIntent pendingIntent, ComponentName componentName) {
        this.mAudioManager.unregisterMediaButtonEventReceiver(componentName);
    }

    boolean update() {
        if (this.mIsActive) {
            if (!this.mIsMbrRegistered && (this.mFlags & 1) != 0) {
                this.registerMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                this.mIsMbrRegistered = true;
            } else if (this.mIsMbrRegistered && (this.mFlags & 1) == 0) {
                this.unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                this.mIsMbrRegistered = false;
            }
            if (!this.mIsRccRegistered && (this.mFlags & 2) != 0) {
                this.mAudioManager.registerRemoteControlClient(this.mRcc);
                this.mIsRccRegistered = true;
                return true;
            }
            if (this.mIsRccRegistered && (this.mFlags & 2) == 0) {
                this.mRcc.setPlaybackState(0);
                this.mAudioManager.unregisterRemoteControlClient(this.mRcc);
                this.mIsRccRegistered = false;
            }
        } else {
            if (this.mIsMbrRegistered) {
                this.unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                this.mIsMbrRegistered = false;
            }
            if (this.mIsRccRegistered) {
                this.mRcc.setPlaybackState(0);
                this.mAudioManager.unregisterRemoteControlClient(this.mRcc);
                this.mIsRccRegistered = false;
            }
        }
        return false;
    }

    private static final class Command {
        public final String command;
        public final Bundle extras;
        public final ResultReceiver stub;

        public Command(String string, Bundle bundle, ResultReceiver resultReceiver) {
            this.command = string;
            this.extras = bundle;
            this.stub = resultReceiver;
        }
    }

    class MediaSessionStub
    extends IMediaSession.Stub {
        MediaSessionStub() {
        }

        @Override
        public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
            MediaSessionImplBase.this.postToHandler(25, mediaDescriptionCompat);
        }

        @Override
        public void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int n) {
            MediaSessionImplBase.this.postToHandler(26, (Object)mediaDescriptionCompat, n);
        }

        @Override
        public void adjustVolume(int n, int n2, String string) {
            MediaSessionImplBase.this.adjustVolume(n, n2);
        }

        @Override
        public void fastForward() throws RemoteException {
            MediaSessionImplBase.this.postToHandler(16);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Bundle getExtras() {
            Object object = MediaSessionImplBase.this.mLock;
            synchronized (object) {
                return MediaSessionImplBase.this.mExtras;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public long getFlags() {
            Object object = MediaSessionImplBase.this.mLock;
            synchronized (object) {
                return MediaSessionImplBase.this.mFlags;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public PendingIntent getLaunchPendingIntent() {
            Object object = MediaSessionImplBase.this.mLock;
            synchronized (object) {
                return MediaSessionImplBase.this.mSessionActivity;
            }
        }

        @Override
        public MediaMetadataCompat getMetadata() {
            return MediaSessionImplBase.this.mMetadata;
        }

        @Override
        public String getPackageName() {
            return MediaSessionImplBase.this.mPackageName;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public PlaybackStateCompat getPlaybackState() {
            Object object = MediaSessionImplBase.this.mLock;
            synchronized (object) {
                PlaybackStateCompat playbackStateCompat = MediaSessionImplBase.this.mState;
                MediaMetadataCompat mediaMetadataCompat = MediaSessionImplBase.this.mMetadata;
                return MediaSessionCompat.getStateWithUpdatedPosition(playbackStateCompat, mediaMetadataCompat);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public List<MediaSessionCompat.QueueItem> getQueue() {
            Object object = MediaSessionImplBase.this.mLock;
            synchronized (object) {
                return MediaSessionImplBase.this.mQueue;
            }
        }

        @Override
        public CharSequence getQueueTitle() {
            return MediaSessionImplBase.this.mQueueTitle;
        }

        @Override
        public int getRatingType() {
            return MediaSessionImplBase.this.mRatingType;
        }

        @Override
        public int getRepeatMode() {
            return MediaSessionImplBase.this.mRepeatMode;
        }

        @Override
        public int getShuffleMode() {
            return MediaSessionImplBase.this.mShuffleMode;
        }

        @Override
        public String getTag() {
            return MediaSessionImplBase.this.mTag;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public ParcelableVolumeInfo getVolumeAttributes() {
            Object object = MediaSessionImplBase.this.mLock;
            synchronized (object) {
                int n;
                int n2;
                int n3 = MediaSessionImplBase.this.mVolumeType;
                int n4 = MediaSessionImplBase.this.mLocalStream;
                VolumeProviderCompat volumeProviderCompat = MediaSessionImplBase.this.mVolumeProvider;
                int n5 = 2;
                if (n3 == 2) {
                    n5 = volumeProviderCompat.getVolumeControl();
                    n2 = volumeProviderCompat.getMaxVolume();
                    n = volumeProviderCompat.getCurrentVolume();
                } else {
                    n2 = MediaSessionImplBase.this.mAudioManager.getStreamMaxVolume(n4);
                    n = MediaSessionImplBase.this.mAudioManager.getStreamVolume(n4);
                }
                return new ParcelableVolumeInfo(n3, n4, n5, n2, n);
            }
        }

        @Override
        public boolean isCaptioningEnabled() {
            return MediaSessionImplBase.this.mCaptioningEnabled;
        }

        @Override
        public boolean isShuffleModeEnabledRemoved() {
            return false;
        }

        @Override
        public boolean isTransportControlEnabled() {
            if ((MediaSessionImplBase.this.mFlags & 2) != 0) {
                return true;
            }
            return false;
        }

        @Override
        public void next() throws RemoteException {
            MediaSessionImplBase.this.postToHandler(14);
        }

        @Override
        public void pause() throws RemoteException {
            MediaSessionImplBase.this.postToHandler(12);
        }

        @Override
        public void play() throws RemoteException {
            MediaSessionImplBase.this.postToHandler(7);
        }

        @Override
        public void playFromMediaId(String string, Bundle bundle) throws RemoteException {
            MediaSessionImplBase.this.postToHandler(8, (Object)string, bundle);
        }

        @Override
        public void playFromSearch(String string, Bundle bundle) throws RemoteException {
            MediaSessionImplBase.this.postToHandler(9, (Object)string, bundle);
        }

        @Override
        public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
            MediaSessionImplBase.this.postToHandler(10, (Object)uri, bundle);
        }

        @Override
        public void prepare() throws RemoteException {
            MediaSessionImplBase.this.postToHandler(3);
        }

        @Override
        public void prepareFromMediaId(String string, Bundle bundle) throws RemoteException {
            MediaSessionImplBase.this.postToHandler(4, (Object)string, bundle);
        }

        @Override
        public void prepareFromSearch(String string, Bundle bundle) throws RemoteException {
            MediaSessionImplBase.this.postToHandler(5, (Object)string, bundle);
        }

        @Override
        public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
            MediaSessionImplBase.this.postToHandler(6, (Object)uri, bundle);
        }

        @Override
        public void previous() throws RemoteException {
            MediaSessionImplBase.this.postToHandler(15);
        }

        @Override
        public void rate(RatingCompat ratingCompat) throws RemoteException {
            MediaSessionImplBase.this.postToHandler(19, ratingCompat);
        }

        @Override
        public void rateWithExtras(RatingCompat ratingCompat, Bundle bundle) throws RemoteException {
            MediaSessionImplBase.this.postToHandler(31, (Object)ratingCompat, bundle);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
            if (!MediaSessionImplBase.this.mDestroyed) {
                MediaSessionImplBase.this.mControllerCallbacks.register((IInterface)iMediaControllerCallback);
                return;
            }
            try {
                iMediaControllerCallback.onSessionDestroyed();
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        @Override
        public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
            MediaSessionImplBase.this.postToHandler(27, mediaDescriptionCompat);
        }

        @Override
        public void removeQueueItemAt(int n) {
            MediaSessionImplBase.this.postToHandler(28, n);
        }

        @Override
        public void rewind() throws RemoteException {
            MediaSessionImplBase.this.postToHandler(17);
        }

        @Override
        public void seekTo(long l) throws RemoteException {
            MediaSessionImplBase.this.postToHandler(18, l);
        }

        @Override
        public void sendCommand(String string, Bundle bundle, MediaSessionCompat.ResultReceiverWrapper resultReceiverWrapper) {
            MediaSessionImplBase.this.postToHandler(1, new Command(string, bundle, resultReceiverWrapper.mResultReceiver));
        }

        @Override
        public void sendCustomAction(String string, Bundle bundle) throws RemoteException {
            MediaSessionImplBase.this.postToHandler(20, (Object)string, bundle);
        }

        @Override
        public boolean sendMediaButton(KeyEvent keyEvent) {
            int n = MediaSessionImplBase.this.mFlags;
            boolean bl = true;
            if ((n & 1) == 0) {
                bl = false;
            }
            if (bl) {
                MediaSessionImplBase.this.postToHandler(21, (Object)keyEvent);
            }
            return bl;
        }

        @Override
        public void setCaptioningEnabled(boolean bl) throws RemoteException {
            MediaSessionImplBase.this.postToHandler(29, bl);
        }

        @Override
        public void setRepeatMode(int n) throws RemoteException {
            MediaSessionImplBase.this.postToHandler(23, n);
        }

        @Override
        public void setShuffleMode(int n) throws RemoteException {
            MediaSessionImplBase.this.postToHandler(30, n);
        }

        @Override
        public void setShuffleModeEnabledRemoved(boolean bl) throws RemoteException {
        }

        @Override
        public void setVolumeTo(int n, int n2, String string) {
            MediaSessionImplBase.this.setVolumeTo(n, n2);
        }

        @Override
        public void skipToQueueItem(long l) {
            MediaSessionImplBase.this.postToHandler(11, l);
        }

        @Override
        public void stop() throws RemoteException {
            MediaSessionImplBase.this.postToHandler(13);
        }

        @Override
        public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
            MediaSessionImplBase.this.mControllerCallbacks.unregister((IInterface)iMediaControllerCallback);
        }
    }

    class MessageHandler
    extends Handler {
        private static final int KEYCODE_MEDIA_PAUSE = 127;
        private static final int KEYCODE_MEDIA_PLAY = 126;
        private static final int MSG_ADD_QUEUE_ITEM = 25;
        private static final int MSG_ADD_QUEUE_ITEM_AT = 26;
        private static final int MSG_ADJUST_VOLUME = 2;
        private static final int MSG_COMMAND = 1;
        private static final int MSG_CUSTOM_ACTION = 20;
        private static final int MSG_FAST_FORWARD = 16;
        private static final int MSG_MEDIA_BUTTON = 21;
        private static final int MSG_NEXT = 14;
        private static final int MSG_PAUSE = 12;
        private static final int MSG_PLAY = 7;
        private static final int MSG_PLAY_MEDIA_ID = 8;
        private static final int MSG_PLAY_SEARCH = 9;
        private static final int MSG_PLAY_URI = 10;
        private static final int MSG_PREPARE = 3;
        private static final int MSG_PREPARE_MEDIA_ID = 4;
        private static final int MSG_PREPARE_SEARCH = 5;
        private static final int MSG_PREPARE_URI = 6;
        private static final int MSG_PREVIOUS = 15;
        private static final int MSG_RATE = 19;
        private static final int MSG_RATE_EXTRA = 31;
        private static final int MSG_REMOVE_QUEUE_ITEM = 27;
        private static final int MSG_REMOVE_QUEUE_ITEM_AT = 28;
        private static final int MSG_REWIND = 17;
        private static final int MSG_SEEK_TO = 18;
        private static final int MSG_SET_CAPTIONING_ENABLED = 29;
        private static final int MSG_SET_REPEAT_MODE = 23;
        private static final int MSG_SET_SHUFFLE_MODE = 30;
        private static final int MSG_SET_VOLUME = 22;
        private static final int MSG_SKIP_TO_ITEM = 11;
        private static final int MSG_STOP = 13;

        public MessageHandler(Looper looper) {
            super(looper);
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private void onMediaButtonEvent(KeyEvent keyEvent, MediaSessionCompat.Callback callback) {
            if (keyEvent == null) return;
            if (keyEvent.getAction() != 0) {
                return;
            }
            long l = MediaSessionImplBase.this.mState == null ? 0L : MediaSessionImplBase.this.mState.getActions();
            int n = keyEvent.getKeyCode();
            if (n != 79) {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                return;
                            }
                            case 127: {
                                if ((l & 2L) == 0L) return;
                                callback.onPause();
                                return;
                            }
                            case 126: 
                        }
                        if ((l & 4L) == 0L) return;
                        callback.onPlay();
                        return;
                    }
                    case 90: {
                        if ((l & 64L) == 0L) return;
                        callback.onFastForward();
                        return;
                    }
                    case 89: {
                        if ((l & 8L) == 0L) return;
                        callback.onRewind();
                        return;
                    }
                    case 88: {
                        if ((l & 16L) == 0L) return;
                        callback.onSkipToPrevious();
                        return;
                    }
                    case 87: {
                        if ((l & 32L) == 0L) return;
                        callback.onSkipToNext();
                        return;
                    }
                    case 86: {
                        if ((l & 1L) == 0L) return;
                        callback.onStop();
                        return;
                    }
                    case 85: 
                }
            }
            Log.w((String)MediaSessionCompat.TAG, (String)"KEYCODE_MEDIA_PLAY_PAUSE and KEYCODE_HEADSETHOOK are handled already");
        }

        public void handleMessage(Message object) {
            MediaSessionCompat.Callback callback = MediaSessionImplBase.this.mCallback;
            if (callback == null) {
                return;
            }
            switch (object.what) {
                default: {
                    return;
                }
                case 31: {
                    callback.onSetRating((RatingCompat)object.obj, object.getData());
                    return;
                }
                case 30: {
                    callback.onSetShuffleMode(object.arg1);
                    return;
                }
                case 29: {
                    callback.onSetCaptioningEnabled((Boolean)object.obj);
                    return;
                }
                case 28: {
                    if (MediaSessionImplBase.this.mQueue == null) break;
                    object = object.arg1 >= 0 && object.arg1 < MediaSessionImplBase.this.mQueue.size() ? MediaSessionImplBase.this.mQueue.get(object.arg1) : null;
                    if (object == null) break;
                    callback.onRemoveQueueItem(object.getDescription());
                    return;
                }
                case 27: {
                    callback.onRemoveQueueItem((MediaDescriptionCompat)object.obj);
                    return;
                }
                case 26: {
                    callback.onAddQueueItem((MediaDescriptionCompat)object.obj, object.arg1);
                    return;
                }
                case 25: {
                    callback.onAddQueueItem((MediaDescriptionCompat)object.obj);
                    return;
                }
                case 23: {
                    callback.onSetRepeatMode(object.arg1);
                    return;
                }
                case 22: {
                    MediaSessionImplBase.this.setVolumeTo(object.arg1, 0);
                    return;
                }
                case 21: {
                    object = (KeyEvent)object.obj;
                    Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                    intent.putExtra("android.intent.extra.KEY_EVENT", (Parcelable)object);
                    if (callback.onMediaButtonEvent(intent)) break;
                    this.onMediaButtonEvent((KeyEvent)object, callback);
                    return;
                }
                case 20: {
                    callback.onCustomAction((String)object.obj, object.getData());
                    return;
                }
                case 19: {
                    callback.onSetRating((RatingCompat)object.obj);
                    return;
                }
                case 18: {
                    callback.onSeekTo((Long)object.obj);
                    return;
                }
                case 17: {
                    callback.onRewind();
                    return;
                }
                case 16: {
                    callback.onFastForward();
                    return;
                }
                case 15: {
                    callback.onSkipToPrevious();
                    return;
                }
                case 14: {
                    callback.onSkipToNext();
                    return;
                }
                case 13: {
                    callback.onStop();
                    return;
                }
                case 12: {
                    callback.onPause();
                    return;
                }
                case 11: {
                    callback.onSkipToQueueItem((Long)object.obj);
                    return;
                }
                case 10: {
                    callback.onPlayFromUri((Uri)object.obj, object.getData());
                    return;
                }
                case 9: {
                    callback.onPlayFromSearch((String)object.obj, object.getData());
                    return;
                }
                case 8: {
                    callback.onPlayFromMediaId((String)object.obj, object.getData());
                    return;
                }
                case 7: {
                    callback.onPlay();
                    return;
                }
                case 6: {
                    callback.onPrepareFromUri((Uri)object.obj, object.getData());
                    return;
                }
                case 5: {
                    callback.onPrepareFromSearch((String)object.obj, object.getData());
                    return;
                }
                case 4: {
                    callback.onPrepareFromMediaId((String)object.obj, object.getData());
                    return;
                }
                case 3: {
                    callback.onPrepare();
                    return;
                }
                case 2: {
                    MediaSessionImplBase.this.adjustVolume(object.arg1, 0);
                    return;
                }
                case 1: {
                    object = (Command)object.obj;
                    callback.onCommand(object.command, object.extras, object.stub);
                }
            }
        }

        public void post(int n) {
            this.post(n, null);
        }

        public void post(int n, Object object) {
            this.obtainMessage(n, object).sendToTarget();
        }

        public void post(int n, Object object, int n2) {
            this.obtainMessage(n, n2, 0, object).sendToTarget();
        }

        public void post(int n, Object object, Bundle bundle) {
            object = this.obtainMessage(n, object);
            object.setData(bundle);
            object.sendToTarget();
        }
    }

}
