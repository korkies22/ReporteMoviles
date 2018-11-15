/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback
 *  android.view.SurfaceView
 *  android.view.View
 *  android.view.ViewGroup
 */
package de.cisha.android.board.broadcast.video;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.broadcast.video.VideoStreamService;

class VideoStreamFragment
implements ServiceConnection {
    VideoStreamFragment() {
    }

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

}
