// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.video;

import android.os.Binder;
import android.content.Intent;
import android.content.Context;
import de.cisha.chess.util.Logger;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.os.IBinder;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.app.Service;

public class VideoStreamService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener
{
    private static final String ARGS_KEY_URI = "ARGS_KEY_URI";
    private final IBinder _binder;
    private SurfaceHolder _display;
    private boolean _flagError;
    private boolean _flagVideoIsPrepared;
    private boolean _flagVideoIsPreparing;
    private VideoStreamServiceListener _listener;
    private volatile MediaPlayer _mediaPlayer;
    private String _uri;
    
    public VideoStreamService() {
        this._binder = (IBinder)new LocalBinder();
        this._flagError = false;
        this._flagVideoIsPrepared = false;
        this._flagVideoIsPreparing = false;
    }
    
    private void createAndPrepareMediaPlayer() {
        this._flagVideoIsPrepared = false;
        if (this._mediaPlayer != null) {
            this._mediaPlayer.release();
        }
        (this._mediaPlayer = new MediaPlayer()).setOnCompletionListener((MediaPlayer.OnCompletionListener)this);
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
                }
            }
            catch (Exception ex) {
                Logger.getInstance().error(VideoStreamService.class.getName(), "", ex);
                if (this._listener != null) {
                    this._listener.onVideoStreamError();
                }
            }
        }
    }
    
    public static Intent createIntentWithUri(final Context context, final String s) {
        final Intent intent = new Intent(context, (Class)VideoStreamService.class);
        intent.putExtra("ARGS_KEY_URI", s);
        return intent;
    }
    
    public boolean canVideoPause() {
        synchronized (this) {
            return this._flagVideoIsPreparing || this._flagVideoIsPrepared;
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
                return this._mediaPlayer.getCurrentPosition();
            }
            return 0;
        }
    }
    
    public int getVideoDuration() {
        synchronized (this) {
            if (this._flagVideoIsPrepared) {
                return this._mediaPlayer.getDuration();
            }
            return 1;
        }
    }
    
    public boolean isPreparing() {
        synchronized (this) {
            return this._flagVideoIsPreparing;
        }
    }
    
    public boolean isVideoPlaying() {
        synchronized (this) {
            return (this._flagVideoIsPreparing || this._flagVideoIsPrepared) && !this._flagError;
        }
    }
    
    public IBinder onBind(final Intent intent) {
        this.createAndPrepareMediaPlayer();
        return this._binder;
    }
    
    public void onBufferingUpdate(final MediaPlayer mediaPlayer, final int n) {
        if (this._listener != null) {
            this._listener.onVideoBufferingUpdate(n);
        }
    }
    
    public void onCompletion(final MediaPlayer mediaPlayer) {
        this.stopSelf();
    }
    
    public void onDestroy() {
        super.onDestroy();
        if (this._mediaPlayer != null) {
            this._mediaPlayer.release();
        }
    }
    
    public boolean onError(final MediaPlayer mediaPlayer, final int n, final int n2) {
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
    
    public boolean onInfo(final MediaPlayer mediaPlayer, final int n, final int n2) {
        if (this._listener != null) {
            if (n == 701) {
                this._listener.onStartBuffering();
            }
            else if (n == 702) {
                this._listener.onStopBuffering();
            }
        }
        return true;
    }
    
    public void onPrepared(final MediaPlayer mediaPlayer) {
        this._flagVideoIsPrepared = true;
        this._flagVideoIsPreparing = false;
        if (this._listener != null) {
            this._listener.onMediaPlayerPrepared();
        }
        this._mediaPlayer.start();
    }
    
    public void onRebind(final Intent intent) {
        if (this._mediaPlayer == null || this._flagError || (!this._flagVideoIsPreparing && !this._flagVideoIsPrepared)) {
            this.createAndPrepareMediaPlayer();
        }
        super.onRebind(intent);
    }
    
    public int onStartCommand(final Intent intent, final int n, final int n2) {
        if (intent != null && intent.hasExtra("ARGS_KEY_URI")) {
            this._uri = intent.getStringExtra("ARGS_KEY_URI");
        }
        return super.onStartCommand(intent, n, n2);
    }
    
    public boolean onUnbind(final Intent intent) {
        return true;
    }
    
    public void onVideoSizeChanged(final MediaPlayer mediaPlayer, final int n, final int n2) {
        if (this._listener != null) {
            this._listener.onVideoSizeChanged(n, n2);
        }
    }
    
    public void setVideoDisplay(final SurfaceHolder surfaceHolder) {
        this._display = surfaceHolder;
        if (this._mediaPlayer != null && !this._flagError) {
            this._mediaPlayer.setDisplay(surfaceHolder);
        }
    }
    
    public void setVideoStreamServiceListener(final VideoStreamServiceListener listener) {
        this._listener = listener;
    }
    
    public void startPlaying() {
        if (this._flagError || (this._mediaPlayer != null && !this._mediaPlayer.isPlaying())) {
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
    
    public class LocalBinder extends Binder
    {
        VideoStreamService getService() {
            return VideoStreamService.this;
        }
    }
    
    public interface VideoStreamServiceListener
    {
        void onMediaPlayerPrepared();
        
        void onStartBuffering();
        
        void onStartPreparingMediaPlayer();
        
        void onStopBuffering();
        
        void onVideoBufferingUpdate(final int p0);
        
        void onVideoSizeChanged(final int p0, final int p1);
        
        void onVideoStreamError();
    }
}
