/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.model.BoardMarksManager;
import de.cisha.android.board.video.CommandContainer;
import de.cisha.android.board.video.model.VideoGameHolder;
import de.cisha.android.board.view.BoardMarkingDisplay;

class VideoFragment
implements Runnable {
    final /* synthetic */ int val$milliseconds;

    VideoFragment(int n) {
        this.val$milliseconds = n;
    }

    @Override
    public void run() {
        if (VideoFragment.this._container != null) {
            VideoFragment.this._container.executeCommandsUntil(this.val$milliseconds, VideoFragment.this._markingManager, VideoFragment.this.getGameHolder());
        }
    }
}
