// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.navigationbaritems;

import java.io.IOException;
import de.cisha.chess.util.Logger;
import android.net.Uri;
import java.util.TimerTask;
import android.view.View;
import android.os.Looper;
import java.util.Timer;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import android.media.MediaPlayer;
import android.os.Handler;
import android.content.Context;
import de.cisha.android.board.video.view.VideoWithControlsView;
import android.media.MediaPlayer.OnPreparedListener;

public class VideoAnalysisNavigationBarController extends AbstractNavigationBarItem implements MediaPlayer.OnPreparedListener
{
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
    private int _videoDuration;
    
    public VideoAnalysisNavigationBarController(final Context context, final int savedVideoPosition) {
        this._videoDuration = -1;
        this._context = context;
        this._savedVideoPosition = savedVideoPosition;
        (this._mediaPlayer = new MediaPlayer()).setOnPreparedListener((MediaPlayer.OnPreparedListener)this);
        this._contentView = new VideoWithControlsView(context, this._mediaPlayer);
        this._mainThreadHandler = new Handler(Looper.getMainLooper());
    }
    
    private void setVideoTime(final int n) {
        if (this._delegate != null) {
            this._mainThreadHandler.post((Runnable)new Runnable() {
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
    public View getContentView(final Context context) {
        return (View)this._contentView;
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
    
    public MenuBarItem getMenuItem(final Context context) {
        if (this._menuBarItem == null) {
            (this._menuBarItem = new MenuBarItem(context, "Video", 2131230986, 2131230987, -1)).setSelectionGroup(1);
        }
        return this._menuBarItem;
    }
    
    public void handleClick() {
        if (this._mediaPlayer != null) {
            if (this._mediaPlayer.isPlaying()) {
                if (this._pauseOnClick) {
                    this._mediaPlayer.pause();
                }
            }
            else {
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
    
    public void onPrepared(final MediaPlayer mediaPlayer) {
        this._isInitialized = true;
        if (this._updateTimeTimer == null) {
            (this._updateTimeTimer = new Timer()).scheduleAtFixedRate(new UpdateTask(), 333L, 333L);
        }
        this._contentView.adjustSurfaceSize();
        this.startVideo();
    }
    
    public void pauseVideo() {
        if (this._mediaPlayer != null) {
            this._mediaPlayer.pause();
        }
    }
    
    public void setVideo(final Uri uri) {
        try {
            this._mediaPlayer.setDataSource(this._context, uri);
            this._mediaPlayer.prepare();
        }
        catch (IOException ex) {
            Logger.getInstance().debug(VideoAnalysisNavigationBarController.class.getName(), IOException.class.getName(), ex);
        }
        catch (IllegalStateException ex2) {
            Logger.getInstance().debug(VideoAnalysisNavigationBarController.class.getName(), IllegalStateException.class.getName(), ex2);
        }
        catch (SecurityException ex3) {
            Logger.getInstance().debug(VideoAnalysisNavigationBarController.class.getName(), SecurityException.class.getName(), ex3);
        }
        catch (IllegalArgumentException ex4) {
            Logger.getInstance().debug(VideoAnalysisNavigationBarController.class.getName(), IllegalArgumentException.class.getName(), ex4);
        }
    }
    
    public void setVideoDelegate(final VideoDelegate videoDelegate) {
        this._delegate = videoDelegate;
        this._contentView.setVideoDelegate(videoDelegate);
    }
    
    private class UpdateTask extends TimerTask
    {
        @Override
        public void run() {
            final int currentPosition = VideoAnalysisNavigationBarController.this._mediaPlayer.getCurrentPosition();
            if (VideoAnalysisNavigationBarController.this._videoDuration <= 0) {
                VideoAnalysisNavigationBarController.this._videoDuration = VideoAnalysisNavigationBarController.this._mediaPlayer.getDuration();
            }
            if (VideoAnalysisNavigationBarController.this._isInitialized && currentPosition <= VideoAnalysisNavigationBarController.this._videoDuration) {
                VideoAnalysisNavigationBarController.this.setVideoTime(currentPosition);
            }
        }
    }
    
    public interface VideoDelegate
    {
        void videoPlayed(final int p0);
        
        void videoStarted();
    }
}
