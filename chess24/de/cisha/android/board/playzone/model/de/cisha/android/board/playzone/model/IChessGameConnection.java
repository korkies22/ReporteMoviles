/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import de.cisha.chess.model.Move;

public interface IChessGameConnection {
    public void acceptDrawOffer();

    public void disconnect();

    public void doMove(Move var1);

    public void offerDraw();

    public void requestAbort();

    public void resign();
}
