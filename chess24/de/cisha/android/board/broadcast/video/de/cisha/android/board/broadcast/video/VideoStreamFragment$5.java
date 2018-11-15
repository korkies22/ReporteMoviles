/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.SurfaceHolder
 *  android.view.SurfaceView
 *  android.widget.MediaController
 */
package de.cisha.android.board.broadcast.video;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController;
import de.cisha.android.board.broadcast.video.VideoStreamService;

class VideoStreamFragment
implements Runnable {
    VideoStreamFragment() {
    }

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
}
