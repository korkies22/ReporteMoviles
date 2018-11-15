/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;

public interface IGameDelegate {
    public void onGameEndDetected(GameEndInformation var1);

    public void onGameStart(Game var1, int var2, int var3);

    public void onMove(Move var1);

    public void onReceiveDrawOffer();
}
