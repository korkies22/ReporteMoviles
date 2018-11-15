/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.MediaPlayer
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.Surface
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback
 *  android.view.SurfaceView
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.MediaController
 *  android.widget.MediaController$MediaPlayerControl
 *  android.widget.RelativeLayout
 */
package de.cisha.android.board.video.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import de.cisha.android.board.analyze.navigationbaritems.VideoAnalysisNavigationBarController;
import de.cisha.android.board.video.CustomMediaController;

public class VideoWithControlsView
extends RelativeLayout
implements MediaController.MediaPlayerControl,
SurfaceHolder.Callback {
    private VideoAnalysisNavigationBarController.VideoDelegate _delegate;
    private MediaController _mediaControls;
    private MediaPlayer _mediaPlayer;
    private SurfaceView _videoSurfaceView;

    public VideoWithControlsView(Context context, MediaPlayer mediaPlayer) {
        super(context);
        this._mediaPlayer = mediaPlayer;
        this.initView(context);
        this.setMediaPlayer(mediaPlayer);
    }

    public VideoWithControlsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initView(context);
    }

    public VideoWithControlsView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initView(context);
    }

    private void initView(Context context) {
        ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(2131427377, (ViewGroup)this, true);
        this._videoSurfaceView = (SurfaceView)this.findViewById(2131296338);
        this._videoSurfaceView.getHolder().addCallback((SurfaceHolder.Callback)this);
        this._mediaControls = new CustomMediaController(context);
        this._videoSurfaceView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                VideoWithControlsView.this._mediaControls.show();
            }
        });
    }

    public void adjustSurfaceSize() {
        if (this._mediaPlayer != null) {
            int n = this._mediaPlayer.getVideoWidth();
            int n2 = this._mediaPlayer.getVideoHeight();
            if (n > 0 && n2 > 0) {
                float f = (float)n / (float)n2;
                n = this.getWidth();
                n2 = this.getHeight();
                if (n2 > 0) {
                    float f2 = n;
                    float f3 = n2;
                    float f4 = f2 / f3;
                    ViewGroup.LayoutParams layoutParams = this._videoSurfaceView.getLayoutParams();
                    if (f > f4) {
                        layoutParams.width = n;
                        layoutParams.height = (int)(f2 / f);
                    } else {
                        layoutParams.width = (int)(f * f3);
                        layoutParams.height = n2;
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
        if (this._mediaPlayer == null) {
            return false;
        }
        return this._mediaPlayer.isPlaying();
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.adjustSurfaceSize();
    }

    public void pause() {
        if (this._mediaPlayer != null) {
            this._mediaPlayer.pause();
        }
    }

    public void seekTo(int n) {
        if (this._mediaPlayer != null) {
            this._mediaPlayer.seekTo(n);
        }
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this._mediaPlayer = mediaPlayer;
        this._mediaControls.setMediaPlayer((MediaController.MediaPlayerControl)this);
    }

    public void setVideoDelegate(VideoAnalysisNavigationBarController.VideoDelegate videoDelegate) {
        this._delegate = videoDelegate;
    }

    public void start() {
        if (this._mediaPlayer != null) {
            this._mediaPlayer.start();
            if (this._delegate != null) {
                this._delegate.videoStarted();
            }
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int n, int n2, int n3) {
        Surface surface = surfaceHolder.getSurface();
        if (surface != null && surface.isValid() && this._mediaPlayer != null) {
            this._mediaPlayer.setDisplay(surfaceHolder);
        }
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Surface surface = surfaceHolder.getSurface();
        if (surface != null && surface.isValid()) {
            this._mediaControls.setAnchorView((View)this);
            this._mediaControls.setEnabled(true);
            if (this._mediaPlayer != null) {
                this._mediaPlayer.setDisplay(surfaceHolder);
            }
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this._mediaControls.setEnabled(false);
        if (this._mediaPlayer != null) {
            this._mediaPlayer.setDisplay(null);
        }
    }

}
