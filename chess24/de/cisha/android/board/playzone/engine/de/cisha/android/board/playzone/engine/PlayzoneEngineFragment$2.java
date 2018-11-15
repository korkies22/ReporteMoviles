/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.ui.patterns.dialogs.ConfirmCallback;
import de.cisha.chess.model.Game;

class PlayzoneEngineFragment
implements ConfirmCallback {
    PlayzoneEngineFragment() {
    }

    @Override
    public void canceled() {
    }

    @Override
    public void confirmed() {
        PlayzoneEngineFragment.this.startGame(PlayzoneEngineFragment.this._selectedTimeControl, PlayzoneEngineFragment.this._selectedElo, PlayzoneEngineFragment.this.getGameAdapter().playerIsWhite() ^ true, PlayzoneEngineFragment.this._engineName, null);
    }
}
