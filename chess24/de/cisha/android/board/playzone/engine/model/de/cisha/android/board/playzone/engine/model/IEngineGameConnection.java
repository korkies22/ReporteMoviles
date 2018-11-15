/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine.model;

import de.cisha.android.board.playzone.model.IChessGameConnection;
import de.cisha.chess.model.Game;

public interface IEngineGameConnection
extends IChessGameConnection {
    public void setGameOnStart(Game var1);

    public void startUp();
}
