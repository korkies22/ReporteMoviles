/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.playzone.engine.model;

import android.content.Context;
import de.cisha.android.board.engine.IMoveSource;
import de.cisha.android.board.playzone.engine.model.EngineGameAdapter;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.chess.model.Game;

public static class EngineGameAdapter.Builder {
    public IMoveSource _moveSource;
    private boolean bColor;
    private Context bContext;
    private IGameModelDelegate bDelegate;
    private int bElo;
    private Game bGame;
    private IMoveSource bMoveSource;
    private String bName;
    private TimeControl bTime;

    public EngineGameAdapter.Builder(int n, TimeControl timeControl, Context context, IGameModelDelegate iGameModelDelegate) {
        this.bElo = n;
        this.bTime = timeControl;
        this.bContext = context;
        this.bDelegate = iGameModelDelegate;
        this.bMoveSource = null;
        this.bColor = true;
        this.bName = "Computer";
    }

    public EngineGameAdapter create() {
        return new EngineGameAdapter(this.bElo, this.bTime, this.bMoveSource, this.bContext, this.bDelegate, this.bColor, this.bName, this.bGame, null);
    }

    public EngineGameAdapter.Builder setColor(boolean bl) {
        this.bColor = bl;
        return this;
    }

    public EngineGameAdapter.Builder setGame(Game game) {
        this.bGame = game;
        return this;
    }

    public EngineGameAdapter.Builder setMoveSource(IMoveSource iMoveSource) {
        this.bMoveSource = iMoveSource;
        return this;
    }

    public EngineGameAdapter.Builder setName(String string) {
        this.bName = string;
        return this;
    }
}
