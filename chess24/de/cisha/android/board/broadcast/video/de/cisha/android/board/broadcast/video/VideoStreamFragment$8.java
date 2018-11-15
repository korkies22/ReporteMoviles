/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.video;

class VideoStreamFragment
implements Runnable {
    VideoStreamFragment() {
    }

    @Override
    public void run() {
        VideoStreamFragment.this.hideLoadingView();
    }
}
