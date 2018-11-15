/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.view.Display
 *  android.view.LayoutInflater
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback
 *  android.view.SurfaceView
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.WindowManager
 *  android.widget.MediaController
 *  android.widget.MediaController$MediaPlayerControl
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast.video;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.cisha.android.board.BaseFragment;
import de.cisha.android.board.broadcast.video.VideoStreamActivity;
import de.cisha.android.board.broadcast.video.VideoStreamService;
import de.cisha.android.board.view.RookieLoadingView;
import de.cisha.chess.util.Logger;

public class VideoStreamFragment
extends BaseFragment
implements VideoStreamService.VideoStreamServiceListener,
SurfaceHolder.Callback,
MediaController.MediaPlayerControl {
    private static final String ARGS_KEY_SHOWFULLSCREENBUTTON = "ARGS_KEY_SHOWFULLSCREENBUTTON";
    private static final String ARGS_KEY_STOPVIDEONDESTROY = "ARGS_KEY_STOPVIDEONDESTROY";
    private static final String ARGS_KEY_URI = "ARGS_KEY_URI";
    private int _bufferPercentage = 0;
    private TextView _bufferText;
    private ServiceConnection _connection;
    private boolean _flagServiceBounded = false;
    private View _fullsreenButton;
    private boolean _isResumed;
    private RookieLoadingView _loadingView;
    private MediaController _mediaController;
    private boolean _showFullScreenButton = false;
    private boolean _stopVideoOnDestroy = false;
    protected boolean _surfaceLiving;
    private SurfaceView _surfaceVideo;
    private ViewGroup _surfaceVideoContainer;
    private String _uri;
    private VideoStreamService _videoStreamService;
    private View _view;

    public static VideoStreamFragment createInstanceForStreamWithURI(String string, boolean bl, boolean bl2) {
        VideoStreamFragment videoStreamFragment = new VideoStreamFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_KEY_URI, string);
        bundle.putBoolean(ARGS_KEY_STOPVIDEONDESTROY, bl);
        bundle.putBoolean(ARGS_KEY_SHOWFULLSCREENBUTTON, bl);
        videoStreamFragment.setArguments(bundle);
        return videoStreamFragment;
    }

    private void hideLoadingView() {
        if (this._loadingView != null) {
            this._loadingView.enableLoadingAnimation(false);
            ((ViewGroup)this._view).removeView((View)this._loadingView);
            this._loadingView = null;
            this._mediaController.requestFocus();
        }
    }

    private void showLoadingView() {
        if (this._loadingView == null) {
            this._loadingView = new RookieLoadingView((Context)this.getActivity());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            ((ViewGroup)this._view).addView((View)this._loadingView, (ViewGroup.LayoutParams)layoutParams);
        }
        this._loadingView.enableLoadingAnimation(true);
        this._loadingView.bringToFront();
        this._mediaController.requestFocus();
    }

    private void startFullscreenVideoActivity() {
        Intent intent = new Intent((Context)this.getActivity(), VideoStreamActivity.class);
        intent.setFlags(524288);
        this.startActivity(intent);
    }

    public boolean canPause() {
        if (this._videoStreamService != null) {
            return this._videoStreamService.canVideoPause();
        }
        return true;
    }

    public boolean canSeekBackward() {
        return false;
    }

    public boolean canSeekForward() {
        return false;
    }

    public int getAudioSessionId() {
        if (this._videoStreamService != null) {
            return this._videoStreamService.getAudioSessionId();
        }
        return 0;
    }

    public int getBufferPercentage() {
        return this._bufferPercentage;
    }

    public int getCurrentPosition() {
        if (this._videoStreamService != null) {
            return this._videoStreamService.getCurrentPositionFromVideo();
        }
        return 0;
    }

    public int getDuration() {
        if (this._videoStreamService != null) {
            return this._videoStreamService.getVideoDuration();
        }
        return 1;
    }

    public boolean isPlaying() {
        if (this._videoStreamService != null) {
            return this._videoStreamService.isVideoPlaying();
        }
        return false;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        bundle = this.getArguments();
        if (bundle != null) {
            this._uri = bundle.getString(ARGS_KEY_URI);
            this._stopVideoOnDestroy = bundle.getBoolean(ARGS_KEY_STOPVIDEONDESTROY, false);
            this._showFullScreenButton = bundle.getBoolean(ARGS_KEY_SHOWFULLSCREENBUTTON, true);
        }
        this._connection = new ServiceConnection(){

            public void onServiceConnected(ComponentName object, IBinder iBinder) {
                object = (VideoStreamService.LocalBinder)iBinder;
                VideoStreamFragment.this._videoStreamService = object.getService();
                VideoStreamFragment.this._videoStreamService.setVideoStreamServiceListener(VideoStreamFragment.this);
                VideoStreamFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        VideoStreamFragment.this._surfaceVideo = new SurfaceView((Context)VideoStreamFragment.this.getActivity());
                        VideoStreamFragment.this._surfaceVideoContainer.addView((View)VideoStreamFragment.this._surfaceVideo);
                        VideoStreamFragment.this._surfaceVideo.getHolder().addCallback((SurfaceHolder.Callback)VideoStreamFragment.this);
                    }
                });
            }

            public void onServiceDisconnected(ComponentName componentName) {
                VideoStreamFragment.this._flagServiceBounded = false;
            }

        };
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this._view = layoutInflater.inflate(2131427584, viewGroup, false);
        this._mediaController = new MediaController((Context)this.getActivity()){

            public void hide() {
                super.hide();
                if (VideoStreamFragment.this._showFullScreenButton) {
                    VideoStreamFragment.this._fullsreenButton.setVisibility(4);
                }
            }

            public void show(int n) {
                super.show(n);
                if (VideoStreamFragment.this._showFullScreenButton) {
                    VideoStreamFragment.this._fullsreenButton.setVisibility(0);
                }
            }
        };
        this._bufferText = (TextView)this._view.findViewById(2131297245);
        this._fullsreenButton = this._view.findViewById(2131297247);
        this._fullsreenButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                VideoStreamFragment.this.startFullscreenVideoActivity();
            }
        });
        this._surfaceVideoContainer = (ViewGroup)this._view.findViewById(2131297248);
        this._view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                VideoStreamFragment.this._mediaController.show();
                VideoStreamFragment.this._mediaController.requestFocus();
            }
        });
        this._mediaController.setAnchorView(this._view);
        this._mediaController.setMediaPlayer((MediaController.MediaPlayerControl)this);
        return this._view;
    }

    @Override
    public void onDestroy() {
        if (this._stopVideoOnDestroy) {
            this.getActivity().stopService(new Intent((Context)this.getActivity(), VideoStreamService.class));
        }
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean bl) {
        super.onHiddenChanged(bl);
        ViewGroup viewGroup = this._surfaceVideoContainer;
        int n = bl ? 8 : 0;
        viewGroup.setVisibility(n);
    }

    @Override
    public void onMediaPlayerPrepared() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoStreamFragment.this.hideLoadingView();
            }
        });
    }

    @Override
    public void onPause() {
        this._isResumed = false;
        if (this._videoStreamService != null) {
            this._videoStreamService.setVideoDisplay(null);
            this._videoStreamService.setVideoStreamServiceListener(null);
            this._videoStreamService = null;
        }
        if (this._surfaceVideo != null) {
            this._surfaceVideoContainer.removeView((View)this._surfaceVideo);
            this._surfaceVideo = null;
        }
        if (this._flagServiceBounded) {
            this._flagServiceBounded = false;
            try {
                this.getActivity().unbindService(this._connection);
            }
            catch (Exception exception) {
                Logger.getInstance().error("VideoStreamFragment", "Exception unbindung Service", exception);
            }
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.hideLoadingView();
        this.getActivity().startService(VideoStreamService.createIntentWithUri((Context)this.getActivity(), this._uri));
        this._flagServiceBounded = this.getActivity().bindService(new Intent((Context)this.getActivity(), VideoStreamService.class), this._connection, 1);
        this._isResumed = true;
        if (this._surfaceLiving) {
            this._mediaController.show();
        }
    }

    @Override
    public void onStartBuffering() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoStreamFragment.this._bufferText.setVisibility(0);
            }
        });
    }

    @Override
    public void onStartPreparingMediaPlayer() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoStreamFragment.this.showLoadingView();
                VideoStreamFragment.this.getActivity().findViewById(2131297246).setVisibility(8);
            }
        });
    }

    @Override
    public void onStopBuffering() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoStreamFragment.this._bufferText.setVisibility(8);
            }
        });
    }

    @Override
    public void onVideoBufferingUpdate(final int n) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoStreamFragment.this._bufferPercentage = n;
                TextView textView = VideoStreamFragment.this._bufferText;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(VideoStreamFragment.this._bufferPercentage);
                stringBuilder.append("%");
                textView.setText((CharSequence)stringBuilder.toString());
            }
        });
    }

    @Override
    public void onVideoSizeChanged(final int n, final int n2) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                int n3 = n;
                int n22 = n2;
                float f = (float)n3 / (float)n22;
                n3 = VideoStreamFragment.this.getActivity().getWindowManager().getDefaultDisplay().getWidth();
                n22 = VideoStreamFragment.this.getActivity().getWindowManager().getDefaultDisplay().getHeight();
                float f2 = n3;
                float f3 = n22;
                float f4 = f2 / f3;
                ViewGroup.LayoutParams layoutParams = VideoStreamFragment.this._surfaceVideo.getLayoutParams();
                if (f > f4) {
                    layoutParams.width = n3;
                    layoutParams.height = (int)(f2 / f);
                } else {
                    layoutParams.width = (int)(f * f3);
                    layoutParams.height = n22;
                }
                VideoStreamFragment.this._surfaceVideo.setLayoutParams(layoutParams);
            }
        });
    }

    @Override
    public void onVideoStreamError() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoStreamFragment.this.hideLoadingView();
                if (VideoStreamFragment.this.getActivity() != null && VideoStreamFragment.this.getActivity().findViewById(2131297246) != null) {
                    VideoStreamFragment.this.getActivity().findViewById(2131297246).setVisibility(0);
                }
            }
        });
    }

    public void pause() {
        if (this._videoStreamService != null) {
            this._videoStreamService.stopPlaying();
            this.hideLoadingView();
        }
    }

    public void seekTo(int n) {
    }

    public void start() {
        if (this._videoStreamService != null) {
            this._videoStreamService.startPlaying();
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int n, int n2, int n3) {
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (VideoStreamFragment.this._videoStreamService != null) {
                    VideoStreamFragment.this._videoStreamService.setVideoDisplay(VideoStreamFragment.this._surfaceVideo.getHolder());
                    if (VideoStreamFragment.this._videoStreamService.isPreparing()) {
                        VideoStreamFragment.this.showLoadingView();
                    }
                    if (VideoStreamFragment.this._isResumed) {
                        VideoStreamFragment.this._mediaController.show();
                    }
                    VideoStreamFragment.this._surfaceLiving = true;
                }
            }
        });
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this._surfaceLiving = false;
    }

}
