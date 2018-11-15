/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.Display
 *  android.view.SurfaceView
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.WindowManager
 */
package de.cisha.android.board.broadcast.video;

import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;

class VideoStreamFragment
implements Runnable {
    final /* synthetic */ int val$height;
    final /* synthetic */ int val$width;

    VideoStreamFragment(int n, int n2) {
        this.val$width = n;
        this.val$height = n2;
    }

    @Override
    public void run() {
        int n = this.val$width;
        int n2 = this.val$height;
        float f = (float)n / (float)n2;
        n = VideoStreamFragment.this.getActivity().getWindowManager().getDefaultDisplay().getWidth();
        n2 = VideoStreamFragment.this.getActivity().getWindowManager().getDefaultDisplay().getHeight();
        float f2 = n;
        float f3 = n2;
        float f4 = f2 / f3;
        ViewGroup.LayoutParams layoutParams = VideoStreamFragment.this._surfaceVideo.getLayoutParams();
        if (f > f4) {
            layoutParams.width = n;
            layoutParams.height = (int)(f2 / f);
        } else {
            layoutParams.width = (int)(f * f3);
            layoutParams.height = n2;
        }
        VideoStreamFragment.this._surfaceVideo.setLayoutParams(layoutParams);
    }
}
