// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.view;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import de.cisha.android.board.video.CustomMediaController;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.AttributeSet;
import android.content.Context;
import android.view.SurfaceView;
import android.media.MediaPlayer;
import android.widget.MediaController;
import de.cisha.android.board.analyze.navigationbaritems.VideoAnalysisNavigationBarController;
import android.view.SurfaceHolder.Callback;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.RelativeLayout;

public class VideoWithControlsView extends RelativeLayout implements MediaController.MediaPlayerControl, SurfaceHolder.Callback
{
    private VideoAnalysisNavigationBarController.VideoDelegate _delegate;
    private MediaController _mediaControls;
    private MediaPlayer _mediaPlayer;
    private SurfaceView _videoSurfaceView;
    
    public VideoWithControlsView(final Context context, final MediaPlayer mediaPlayer) {
        super(context);
        this._mediaPlayer = mediaPlayer;
        this.initView(context);
        this.setMediaPlayer(mediaPlayer);
    }
    
    public VideoWithControlsView(final Context context, final AttributeSet set) {
        super(context, set);
        this.initView(context);
    }
    
    public VideoWithControlsView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.initView(context);
    }
    
    private void initView(final Context context) {
        ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(2131427377, (ViewGroup)this, true);
        this._videoSurfaceView = (SurfaceView)this.findViewById(2131296338);
        this._videoSurfaceView.getHolder().addCallback((SurfaceHolder.Callback)this);
        this._mediaControls = new CustomMediaController(context);
        this._videoSurfaceView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                VideoWithControlsView.this._mediaControls.show();
            }
        });
    }
    
    public void adjustSurfaceSize() {
        if (this._mediaPlayer != null) {
            final int videoWidth = this._mediaPlayer.getVideoWidth();
            final int videoHeight = this._mediaPlayer.getVideoHeight();
            if (videoWidth > 0 && videoHeight > 0) {
                final float n = videoWidth / videoHeight;
                final int width = this.getWidth();
                final int height = this.getHeight();
                if (height > 0) {
                    final float n2 = width;
                    final float n3 = height;
                    final float n4 = n2 / n3;
                    final ViewGroup.LayoutParams layoutParams = this._videoSurfaceView.getLayoutParams();
                    if (n > n4) {
                        layoutParams.width = width;
                        layoutParams.height = (int)(n2 / n);
                    }
                    else {
                        layoutParams.width = (int)(n * n3);
                        layoutParams.height = height;
                    }
                    this._videoSurfaceView.setLayoutParams(layoutParams);
                }
            }
        }
    }
    
    public boolean canPause() {
        return true;
    }
    
    public boolean canSeekBackward() {
        return true;
    }
    
    public boolean canSeekForward() {
        return true;
    }
    
    public void destroy() {
        this._mediaControls.hide();
        this._mediaPlayer = null;
    }
    
    public int getAudioSessionId() {
        return 0;
    }
    
    public int getBufferPercentage() {
        return 0;
    }
    
    public int getCurrentPosition() {
        if (this._mediaPlayer == null) {
            return 0;
        }
        return this._mediaPlayer.getCurrentPosition();
    }
    
    public int getDuration() {
        if (this._mediaPlayer != null) {
            return this._mediaPlayer.getDuration();
        }
        return 0;
    }
    
    public boolean isPlaying() {
        return this._mediaPlayer != null && this._mediaPlayer.isPlaying();
    }
    
    protected void onSizeChanged(final int n, final int n2, final int n3, final int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.adjustSurfaceSize();
    }
    
    public void pause() {
        if (this._mediaPlayer != null) {
            this._mediaPlayer.pause();
        }
    }
    
    public void seekTo(final int n) {
        if (this._mediaPlayer != null) {
            this._mediaPlayer.seekTo(n);
        }
    }
    
    public void setMediaPlayer(final MediaPlayer mediaPlayer) {
        this._mediaPlayer = mediaPlayer;
        this._mediaControls.setMediaPlayer((MediaController.MediaPlayerControl)this);
    }
    
    public void setVideoDelegate(final VideoAnalysisNavigationBarController.VideoDelegate delegate) {
        this._delegate = delegate;
    }
    
    public void start() {
        if (this._mediaPlayer != null) {
            this._mediaPlayer.start();
            if (this._delegate != null) {
                this._delegate.videoStarted();
            }
        }
    }
    
    public void surfaceChanged(final SurfaceHolder display, final int n, final int n2, final int n3) {
        final Surface surface = display.getSurface();
        if (surface != null && surface.isValid() && this._mediaPlayer != null) {
            this._mediaPlayer.setDisplay(display);
        }
    }
    
    public void surfaceCreated(final SurfaceHolder display) {
        final Surface surface = display.getSurface();
        if (surface != null && surface.isValid()) {
            this._mediaControls.setAnchorView((View)this);
            this._mediaControls.setEnabled(true);
            if (this._mediaPlayer != null) {
                this._mediaPlayer.setDisplay(display);
            }
        }
    }
    
    public void surfaceDestroyed(final SurfaceHolder surfaceHolder) {
        this._mediaControls.setEnabled(false);
        if (this._mediaPlayer != null) {
            this._mediaPlayer.setDisplay((SurfaceHolder)null);
        }
    }
}
