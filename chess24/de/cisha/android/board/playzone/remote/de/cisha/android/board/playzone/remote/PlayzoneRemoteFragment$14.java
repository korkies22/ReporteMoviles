/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.chess.model.Game;

class PlayzoneRemoteFragment
implements BoardAction {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void perform() {
        PlayzoneRemoteFragment.this.startAnalyzeGame(PlayzoneRemoteFragment.this.getGameAdapter().getGame());
    }
}
