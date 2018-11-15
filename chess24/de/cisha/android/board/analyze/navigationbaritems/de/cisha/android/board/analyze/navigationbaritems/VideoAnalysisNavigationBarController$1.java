/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze.navigationbaritems;

import de.cisha.android.board.analyze.navigationbaritems.VideoAnalysisNavigationBarController;

class VideoAnalysisNavigationBarController
implements Runnable {
    final /* synthetic */ int val$timeInMills;

    VideoAnalysisNavigationBarController(int n) {
        this.val$timeInMills = n;
    }

    @Override
    public void run() {
        VideoAnalysisNavigationBarController.this._delegate.videoPlayed(this.val$timeInMills);
    }
}
