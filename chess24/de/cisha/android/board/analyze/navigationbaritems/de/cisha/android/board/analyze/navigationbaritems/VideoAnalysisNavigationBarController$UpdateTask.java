/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.MediaPlayer
 */
package de.cisha.android.board.analyze.navigationbaritems;

import android.media.MediaPlayer;
import de.cisha.android.board.analyze.navigationbaritems.VideoAnalysisNavigationBarController;
import java.util.TimerTask;

private class VideoAnalysisNavigationBarController.UpdateTask
extends TimerTask {
    private VideoAnalysisNavigationBarController.UpdateTask() {
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
