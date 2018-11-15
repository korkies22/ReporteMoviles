/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.model.BoardMarksManager;
import de.cisha.android.board.video.CommandContainer;
import de.cisha.android.board.video.model.VideoGameHolder;
import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.android.board.view.BoardView;

class VideoFragment
implements Runnable {
    VideoFragment() {
    }

    @Override
    public void run() {
        BoardView boardView = VideoFragment.this.getBoardView();
        if (VideoFragment.this._container != null && boardView != null) {
            VideoFragment.this._container.executeAllCommandsuntilCurrentPosition(VideoFragment.this._markingManager, VideoFragment.this.getGameHolder());
        }
    }
}
