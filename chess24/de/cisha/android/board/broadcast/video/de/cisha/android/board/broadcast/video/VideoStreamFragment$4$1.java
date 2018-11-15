/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback
 *  android.view.SurfaceView
 *  android.view.View
 *  android.view.ViewGroup
 */
package de.cisha.android.board.broadcast.video;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.broadcast.video.VideoStreamFragment;

class VideoStreamFragment
implements Runnable {
    VideoStreamFragment() {
    }

    @Override
    public void run() {
        4.this.this$0._surfaceVideo = new SurfaceView((Context)4.this.this$0.getActivity());
        4.this.this$0._surfaceVideoContainer.addView((View)4.this.this$0._surfaceVideo);
        4.this.this$0._surfaceVideo.getHolder().addCallback((SurfaceHolder.Callback)4.this.this$0);
    }
}
