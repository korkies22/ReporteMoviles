/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine.model;

import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.Game;

class EngineGameAdapter
implements Runnable {
    EngineGameAdapter() {
    }

    @Override
    public void run() {
        if (EngineGameAdapter.this._game != null) {
            ServiceProvider.getInstance().getGameService().savePlayzoneGame(EngineGameAdapter.this._game);
        }
    }
}
