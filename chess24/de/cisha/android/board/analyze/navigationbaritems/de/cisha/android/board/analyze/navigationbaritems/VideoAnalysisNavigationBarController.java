/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnPreparedListener
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Looper
 *  android.view.View
 */
package de.cisha.android.board.analyze.navigationbaritems;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import de.cisha.android.board.analyze.navigationbaritems.AbstractNavigationBarItem;
import de.cisha.android.board.video.view.VideoWithControlsView;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.util.Logger;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class VideoAnalysisNavigationBarController
extends AbstractNavigationBarItem
implements MediaPlayer.OnPreparedListener {
    private static final long UPDATE_INTERVAL = 333L;
    private VideoWithControlsView _contentView;
    private Context _context;
    private VideoDelegate _delegate;
    private boolean _isInitialized;
    private Handler _mainThreadHandler;
    private MediaPlayer _mediaPlayer;
    private MenuBarItem _menuBarItem;
    private boolean _pauseOnClick;
    private int _savedVideoPosition;
    private Timer _updateTimeTimer;
    private int _videoDuration = -1;

    public VideoAnalysisNavigationBarController(Context context, int n) {
        this._context = context;
        this._savedVideoPosition = n;
        this._mediaPlayer = new MediaPlayer();
        this._mediaPlayer.setOnPreparedListener((MediaPlayer.OnPreparedListener)this);
        this._contentView = new VideoWithControlsView(context, this._mediaPlayer);
        this._mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    private void setVideoTime(final int n) {
        if (this._delegate != null) {
            this._mainThreadHandler.post(new Runnable(){

                @Override
                public void run() {
                    VideoAnalysisNavigationBarController.this._delegate.videoPlayed(n);
                }
            });
        }
    }

    private void startVideo() {
        this._mediaPlayer.seekTo(this._savedVideoPosition);
        this._mediaPlayer.start();
        if (this._delegate != null) {
            this._delegate.videoStarted();
        }
    }

    public void destroy() {
        if (this._updateTimeTimer != null) {
            this._updateTimeTimer.cancel();
        }
        this._contentView.destroy();
        this._mediaPlayer.stop();
        this._mediaPlayer.release();
        this._mediaPlayer = null;
        this._mainThreadHandler = null;
    }

    @Override
    public View getContentView(Context context) {
        return this._contentView;
    }

    public int getCurrentPosition() {
        if (this._mediaPlayer != null) {
            return this._mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public String getEventTrackingName() {
        return "Show Learn Video";
    }

    @Override
    public MenuBarItem getMenuItem(Context context) {
        if (this._menuBarItem == null) {
            this._menuBarItem = new MenuBarItem(context, "Video", 2131230986, 2131230987, -1);
            this._menuBarItem.setSelectionGroup(1);
        }
        return this._menuBarItem;
    }

    @Override
    public void handleClick() {
        if (this._mediaPlayer != null) {
            if (this._mediaPlayer.isPlaying()) {
                if (this._pauseOnClick) {
                    this._mediaPlayer.pause();
                }
            } else {
                this.startVideo();
            }
        }
        this._pauseOnClick = true;
    }

    @Override
    public void handleDeselection() {
        this._pauseOnClick = false;
        super.handleDeselection();
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        this._isInitialized = true;
        if (this._updateTimeTimer == null) {
            this._updateTimeTimer = new Timer();
            this._updateTimeTimer.scheduleAtFixedRate((TimerTask)new UpdateTask(), 333L, 333L);
        }
        this._contentView.adjustSurfaceSize();
        this.startVideo();
    }

    public void pauseVideo() {
        if (this._mediaPlayer != null) {
            this._mediaPlayer.pause();
        }
    }

    public void setVideo(Uri uri) {
        try {
            this._mediaPlayer.setDataSource(this._context, uri);
            this._mediaPlayer.prepare();
            return;
        }
        catch (IOException iOException) {
            Logger.getInstance().debug(VideoAnalysisNavigationBarController.class.getName(), IOException.class.getName(), iOException);
            return;
        }
        catch (IllegalStateException illegalStateException) {
            Logger.getInstance().debug(VideoAnalysisNavigationBarController.class.getName(), IllegalStateException.class.getName(), illegalStateException);
            return;
        }
        catch (SecurityException securityException) {
            Logger.getInstance().debug(VideoAnalysisNavigationBarController.class.getName(), SecurityException.class.getName(), securityException);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Logger.getInstance().debug(VideoAnalysisNavigationBarController.class.getName(), IllegalArgumentException.class.getName(), illegalArgumentException);
            return;
        }
    }

    public void setVideoDelegate(VideoDelegate videoDelegate) {
        this._delegate = videoDelegate;
        this._contentView.setVideoDelegate(videoDelegate);
    }

    private class UpdateTask
    extends TimerTask {
        private UpdateTask() {
        }

        @Override
        public void run() {
            int n = VideoAnalysisNavigationBarController.this._mediaPlayer.getCurrentPosition();
            if (VideoAnalysisNavigationBarController.this._videoDuration <= 0) {
                VideoAnalysisNavigationBarController.this._videoDuration = VideoAnalysisNavigationBarController.this._mediaPlayer.getDuration();
            }
            if (VideoAnalysisNavigationBarController.this._isInitialized && n <= VideoAnalysisNavigationBarController.this._videoDuration) {
                VideoAnalysisNavigationBarController.this.setVideoTime(n);
            }
        }
    }

    public static interface VideoDelegate {
        public void videoPlayed(int var1);

        public void videoStarted();
    }

}
