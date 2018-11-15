/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.model.BoardMarks;
import de.cisha.android.board.model.BoardMarksManager;
import de.cisha.android.board.video.CommandContainer;
import de.cisha.android.board.video.command.VideoCommand;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoGameHolder;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.MoveContainer;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class VideoFragment
implements Runnable {
    VideoFragment() {
    }

    @Override
    public void run() {
        VideoFragment.this._markingManager.resetWithInitialValues(VideoFragment.this._video.getBoardMarkings());
        VideoFragment.this.getGameHolder().setGames(VideoFragment.this._video.getGames());
        VideoFragment.this._container.addCommands(VideoFragment.this._video.getCommands());
        VideoFragment.this.initVideoViewControllerUri();
    }
}
