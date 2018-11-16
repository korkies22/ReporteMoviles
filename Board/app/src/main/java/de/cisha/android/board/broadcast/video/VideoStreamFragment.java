// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.video;

import de.cisha.chess.util.Logger;
import android.view.SurfaceHolder;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.Intent;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.SurfaceView;
import android.widget.MediaController;
import de.cisha.android.board.view.RookieLoadingView;
import android.view.View;
import android.content.ServiceConnection;
import android.widget.TextView;
import android.widget.MediaController.MediaPlayerControl;
import android.view.SurfaceHolder.Callback;
import de.cisha.android.board.BaseFragment;

public class VideoStreamFragment extends BaseFragment implements VideoStreamServiceListener, SurfaceHolder.Callback, MediaController.MediaPlayerControl
{
    private static final String ARGS_KEY_SHOWFULLSCREENBUTTON = "ARGS_KEY_SHOWFULLSCREENBUTTON";
    private static final String ARGS_KEY_STOPVIDEONDESTROY = "ARGS_KEY_STOPVIDEONDESTROY";
    private static final String ARGS_KEY_URI = "ARGS_KEY_URI";
    private int _bufferPercentage;
    private TextView _bufferText;
    private ServiceConnection _connection;
    private boolean _flagServiceBounded;
    private View _fullsreenButton;
    private boolean _isResumed;
    private RookieLoadingView _loadingView;
    private MediaController _mediaController;
    private boolean _showFullScreenButton;
    private boolean _stopVideoOnDestroy;
    protected boolean _surfaceLiving;
    private SurfaceView _surfaceVideo;
    private ViewGroup _surfaceVideoContainer;
    private String _uri;
    private VideoStreamService _videoStreamService;
    private View _view;
    
    public VideoStreamFragment() {
        this._flagServiceBounded = false;
        this._bufferPercentage = 0;
        this._stopVideoOnDestroy = false;
        this._showFullScreenButton = false;
    }
    
    public static VideoStreamFragment createInstanceForStreamWithURI(final String s, final boolean b, final boolean b2) {
        final VideoStreamFragment videoStreamFragment = new VideoStreamFragment();
        final Bundle arguments = new Bundle();
        arguments.putString("ARGS_KEY_URI", s);
        arguments.putBoolean("ARGS_KEY_STOPVIDEONDESTROY", b);
        arguments.putBoolean("ARGS_KEY_SHOWFULLSCREENBUTTON", b);
        videoStreamFragment.setArguments(arguments);
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
            ((ViewGroup)this._view).addView((View)this._loadingView, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
        }
        this._loadingView.enableLoadingAnimation(true);
        this._loadingView.bringToFront();
        this._mediaController.requestFocus();
    }
    
    private void startFullscreenVideoActivity() {
        final Intent intent = new Intent((Context)this.getActivity(), (Class)VideoStreamActivity.class);
        intent.setFlags(524288);
        this.startActivity(intent);
    }
    
    public boolean canPause() {
        return this._videoStreamService == null || this._videoStreamService.canVideoPause();
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
        return this._videoStreamService != null && this._videoStreamService.isVideoPlaying();
    }
    
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        arguments = this.getArguments();
        if (arguments != null) {
            this._uri = arguments.getString("ARGS_KEY_URI");
            this._stopVideoOnDestroy = arguments.getBoolean("ARGS_KEY_STOPVIDEONDESTROY", false);
            this._showFullScreenButton = arguments.getBoolean("ARGS_KEY_SHOWFULLSCREENBUTTON", true);
        }
        this._connection = (ServiceConnection)new ServiceConnection() {
            public void onServiceConnected(final ComponentName componentName, final IBinder binder) {
                VideoStreamFragment.this._videoStreamService = ((LocalBinder)binder).getService();
                VideoStreamFragment.this._videoStreamService.setVideoStreamServiceListener((VideoStreamService.VideoStreamServiceListener)VideoStreamFragment.this);
                VideoStreamFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        VideoStreamFragment.this._surfaceVideo = new SurfaceView((Context)VideoStreamFragment.this.getActivity());
                        VideoStreamFragment.this._surfaceVideoContainer.addView((View)VideoStreamFragment.this._surfaceVideo);
                        VideoStreamFragment.this._surfaceVideo.getHolder().addCallback((SurfaceHolder.Callback)VideoStreamFragment.this);
                    }
                });
            }
            
            public void onServiceDisconnected(final ComponentName componentName) {
                VideoStreamFragment.this._flagServiceBounded = false;
            }
        };
    }
    
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        this._view = layoutInflater.inflate(2131427584, viewGroup, false);
        this._mediaController = new MediaController(this.getActivity()) {
            public void hide() {
                super.hide();
                if (VideoStreamFragment.this._showFullScreenButton) {
                    VideoStreamFragment.this._fullsreenButton.setVisibility(4);
                }
            }
            
            public void show(final int n) {
                super.show(n);
                if (VideoStreamFragment.this._showFullScreenButton) {
                    VideoStreamFragment.this._fullsreenButton.setVisibility(0);
                }
            }
        };
        this._bufferText = (TextView)this._view.findViewById(2131297245);
        (this._fullsreenButton = this._view.findViewById(2131297247)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                VideoStreamFragment.this.startFullscreenVideoActivity();
            }
        });
        this._surfaceVideoContainer = (ViewGroup)this._view.findViewById(2131297248);
        this._view.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
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
            this.getActivity().stopService(new Intent((Context)this.getActivity(), (Class)VideoStreamService.class));
        }
        super.onDestroy();
    }
    
    public void onHiddenChanged(final boolean b) {
        super.onHiddenChanged(b);
        final ViewGroup surfaceVideoContainer = this._surfaceVideoContainer;
        int visibility;
        if (b) {
            visibility = 8;
        }
        else {
            visibility = 0;
        }
        surfaceVideoContainer.setVisibility(visibility);
    }
    
    @Override
    public void onMediaPlayerPrepared() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                VideoStreamFragment.this.hideLoadingView();
            }
        });
    }
    
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
            catch (Exception ex) {
                Logger.getInstance().error("VideoStreamFragment", "Exception unbindung Service", ex);
            }
        }
        super.onPause();
    }
    
    public void onResume() {
        super.onResume();
        this.hideLoadingView();
        this.getActivity().startService(VideoStreamService.createIntentWithUri((Context)this.getActivity(), this._uri));
        this._flagServiceBounded = this.getActivity().bindService(new Intent((Context)this.getActivity(), (Class)VideoStreamService.class), this._connection, 1);
        this._isResumed = true;
        if (this._surfaceLiving) {
            this._mediaController.show();
        }
    }
    
    @Override
    public void onStartBuffering() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                VideoStreamFragment.this._bufferText.setVisibility(0);
            }
        });
    }
    
    @Override
    public void onStartPreparingMediaPlayer() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                VideoStreamFragment.this.showLoadingView();
                VideoStreamFragment.this.getActivity().findViewById(2131297246).setVisibility(8);
            }
        });
    }
    
    @Override
    public void onStopBuffering() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                VideoStreamFragment.this._bufferText.setVisibility(8);
            }
        });
    }
    
    @Override
    public void onVideoBufferingUpdate(final int n) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                VideoStreamFragment.this._bufferPercentage = n;
                final TextView access.1300 = VideoStreamFragment.this._bufferText;
                final StringBuilder sb = new StringBuilder();
                sb.append(VideoStreamFragment.this._bufferPercentage);
                sb.append("%");
                access.1300.setText((CharSequence)sb.toString());
            }
        });
    }
    
    @Override
    public void onVideoSizeChanged(final int n, final int n2) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                final float n = n / n2;
                final int width = VideoStreamFragment.this.getActivity().getWindowManager().getDefaultDisplay().getWidth();
                final int height = VideoStreamFragment.this.getActivity().getWindowManager().getDefaultDisplay().getHeight();
                final float n2 = width;
                final float n3 = height;
                final float n4 = n2 / n3;
                final ViewGroup.LayoutParams layoutParams = VideoStreamFragment.this._surfaceVideo.getLayoutParams();
                if (n > n4) {
                    layoutParams.width = width;
                    layoutParams.height = (int)(n2 / n);
                }
                else {
                    layoutParams.width = (int)(n * n3);
                    layoutParams.height = height;
                }
                VideoStreamFragment.this._surfaceVideo.setLayoutParams(layoutParams);
            }
        });
    }
    
    @Override
    public void onVideoStreamError() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
    
    public void seekTo(final int n) {
    }
    
    public void start() {
        if (this._videoStreamService != null) {
            this._videoStreamService.startPlaying();
        }
    }
    
    public void surfaceChanged(final SurfaceHolder surfaceHolder, final int n, final int n2, final int n3) {
    }
    
    public void surfaceCreated(final SurfaceHolder surfaceHolder) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
    
    public void surfaceDestroyed(final SurfaceHolder surfaceHolder) {
        this._surfaceLiving = false;
    }
}
