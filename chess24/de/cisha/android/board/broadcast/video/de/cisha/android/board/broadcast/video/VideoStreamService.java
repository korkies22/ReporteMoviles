/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnBufferingUpdateListener
 *  android.media.MediaPlayer$OnCompletionListener
 *  android.media.MediaPlayer$OnErrorListener
 *  android.media.MediaPlayer$OnInfoListener
 *  android.media.MediaPlayer$OnPreparedListener
 *  android.media.MediaPlayer$OnVideoSizeChangedListener
 *  android.os.Binder
 *  android.os.IBinder
 *  android.view.SurfaceHolder
 */
package de.cisha.android.board.broadcast.video;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.view.SurfaceHolder;
import de.cisha.chess.util.Logger;

public class VideoStreamService
extends Service
implements MediaPlayer.OnCompletionListener,
MediaPlayer.OnBufferingUpdateListener,
MediaPlayer.OnPreparedListener,
MediaPlayer.OnVideoSizeChangedListener,
MediaPlayer.OnErrorListener,
MediaPlayer.OnInfoListener {
    private static final String ARGS_KEY_URI = "ARGS_KEY_URI";
    private final IBinder _binder = new LocalBinder();
    private SurfaceHolder _display;
    private boolean _flagError = false;
    private boolean _flagVideoIsPrepared = false;
    private boolean _flagVideoIsPreparing = false;
    private VideoStreamServiceListener _listener;
    private volatile MediaPlayer _mediaPlayer;
    private String _uri;

    private void createAndPrepareMediaPlayer() {
        block6 : {
            this._flagVideoIsPrepared = false;
            if (this._mediaPlayer != null) {
                this._mediaPlayer.release();
            }
            this._mediaPlayer = new MediaPlayer();
            this._mediaPlayer.setOnCompletionListener((MediaPlayer.OnCompletionListener)this);
            this._mediaPlayer.setAudioStreamType(3);
            this._mediaPlayer.setOnBufferingUpdateListener((MediaPlayer.OnBufferingUpdateListener)this);
            this._mediaPlayer.setOnPreparedListener((MediaPlayer.OnPreparedListener)this);
            this._mediaPlayer.setOnErrorListener((MediaPlayer.OnErrorListener)this);
            this._mediaPlayer.setOnInfoListener((MediaPlayer.OnInfoListener)this);
            this._mediaPlayer.setOnVideoSizeChangedListener((MediaPlayer.OnVideoSizeChangedListener)this);
            if (this._display != null) {
                this._mediaPlayer.setDisplay(this._display);
            }
            this._mediaPlayer.setScreenOnWhilePlaying(true);
            this._flagError = false;
            if (this._uri != null) {
                try {
                    this._mediaPlayer.setDataSource(this._uri);
                    this._mediaPlayer.prepareAsync();
                    this._flagVideoIsPreparing = true;
                    if (this._listener != null) {
                        this._listener.onStartPreparingMediaPlayer();
                        return;
                    }
                }
                catch (Exception exception) {
                    Logger.getInstance().error(VideoStreamService.class.getName(), "", exception);
                    if (this._listener == null) break block6;
                    this._listener.onVideoStreamError();
                }
            }
        }
    }

    public static Intent createIntentWithUri(Context context, String string) {
        context = new Intent(context, VideoStreamService.class);
        context.putExtra(ARGS_KEY_URI, string);
        return context;
    }

    public boolean canVideoPause() {
        synchronized (this) {
            boolean bl;
            block4 : {
                if (!this._flagVideoIsPreparing && !(bl = this._flagVideoIsPrepared)) {
                    bl = false;
                    break block4;
                }
                bl = true;
            }
            return bl;
        }
    }

    public int getAudioSessionId() {
        if (this._mediaPlayer != null) {
            this._mediaPlayer.getAudioSessionId();
        }
        return 0;
    }

    public int getCurrentPositionFromVideo() {
        synchronized (this) {
            if (this._mediaPlayer != null && !this._flagError && !this._flagVideoIsPreparing && this._flagVideoIsPrepared) {
                int n = this._mediaPlayer.getCurrentPosition();
                return n;
            }
            return 0;
        }
    }

    public int getVideoDuration() {
        synchronized (this) {
            if (this._flagVideoIsPrepared) {
                int n = this._mediaPlayer.getDuration();
                return n;
            }
            return 1;
        }
    }

    public boolean isPreparing() {
        synchronized (this) {
            boolean bl = this._flagVideoIsPreparing;
            return bl;
        }
    }

    public boolean isVideoPlaying() {
        synchronized (this) {
            boolean bl;
            bl = (this._flagVideoIsPreparing || this._flagVideoIsPrepared) && !(bl = this._flagError);
            return bl;
        }
    }

    public IBinder onBind(Intent intent) {
        this.createAndPrepareMediaPlayer();
        return this._binder;
    }

    public void onBufferingUpdate(MediaPlayer mediaPlayer, int n) {
        if (this._listener != null) {
            this._listener.onVideoBufferingUpdate(n);
        }
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        this.stopSelf();
    }

    public void onDestroy() {
        super.onDestroy();
        if (this._mediaPlayer != null) {
            this._mediaPlayer.release();
        }
    }

    public boolean onError(MediaPlayer mediaPlayer, int n, int n2) {
        this._flagError = true;
        this._flagVideoIsPrepared = false;
        if (this._listener != null) {
            this._listener.onVideoStreamError();
        }
        if (this._mediaPlayer != null) {
            this._mediaPlayer.release();
        }
        return true;
    }

    public boolean onInfo(MediaPlayer mediaPlayer, int n, int n2) {
        if (this._listener != null) {
            if (n == 701) {
                this._listener.onStartBuffering();
            } else if (n == 702) {
                this._listener.onStopBuffering();
            }
        }
        return true;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        this._flagVideoIsPrepared = true;
        this._flagVideoIsPreparing = false;
        if (this._listener != null) {
            this._listener.onMediaPlayerPrepared();
        }
        this._mediaPlayer.start();
    }

    public void onRebind(Intent intent) {
        if (this._mediaPlayer == null || this._flagError || !this._flagVideoIsPreparing && !this._flagVideoIsPrepared) {
            this.createAndPrepareMediaPlayer();
        }
        super.onRebind(intent);
    }

    public int onStartCommand(Intent intent, int n, int n2) {
        if (intent != null && intent.hasExtra(ARGS_KEY_URI)) {
            this._uri = intent.getStringExtra(ARGS_KEY_URI);
        }
        return super.onStartCommand(intent, n, n2);
    }

    public boolean onUnbind(Intent intent) {
        return true;
    }

    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int n, int n2) {
        if (this._listener != null) {
            this._listener.onVideoSizeChanged(n, n2);
        }
    }

    public void setVideoDisplay(SurfaceHolder surfaceHolder) {
        this._display = surfaceHolder;
        if (this._mediaPlayer != null && !this._flagError) {
            this._mediaPlayer.setDisplay(surfaceHolder);
        }
    }

    public void setVideoStreamServiceListener(VideoStreamServiceListener videoStreamServiceListener) {
        this._listener = videoStreamServiceListener;
    }

    public void startPlaying() {
        if (this._flagError || this._mediaPlayer != null && !this._mediaPlayer.isPlaying()) {
            this.createAndPrepareMediaPlayer();
        }
    }

    public void stopPlaying() {
        this._flagVideoIsPrepared = false;
        this._flagVideoIsPreparing = false;
        if (this._mediaPlayer != null) {
            this._mediaPlayer.stop();
            this._mediaPlayer.release();
        }
        this._mediaPlayer = new MediaPlayer();
        this._flagError = false;
    }

    public class LocalBinder
    extends Binder {
        VideoStreamService getService() {
            return VideoStreamService.this;
        }
    }

    public static interface VideoStreamServiceListener {
        public void onMediaPlayerPrepared();

        public void onStartBuffering();

        public void onStartPreparingMediaPlayer();

        public void onStopBuffering();

        public void onVideoBufferingUpdate(int var1);

        public void onVideoSizeChanged(int var1, int var2);

        public void onVideoStreamError();
    }

}
