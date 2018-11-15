/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.video.VideoFragment;
import de.cisha.android.board.video.model.Video;

class VideoFragment
implements Runnable {
    final /* synthetic */ Video val$result;

    VideoFragment(Video video) {
        this.val$result = video;
    }

    @Override
    public void run() {
        2.this.this$0.hideDialogWaiting();
        2.this.this$0.videoLoaded(this.val$result);
    }
}
