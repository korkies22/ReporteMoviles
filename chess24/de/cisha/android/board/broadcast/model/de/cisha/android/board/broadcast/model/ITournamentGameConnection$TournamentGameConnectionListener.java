/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.ITournamentGameConnection;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.SEP;

public static interface ITournamentGameConnection.TournamentGameConnectionListener
extends IConnection.IConnectionListener {
    public void gameStatusChanged(GameStatus var1);

    public void moveDeleted(int var1);

    public void moveTimeChanged(int var1, int var2);

    public void newMove(int var1, SEP var2, int var3, int var4);

    public void newMove(int var1, String var2, int var3, int var4);

    public void tournamentGameLoaded(Game var1);

    public void tournamentGameLoadingFailed(APIStatusCode var1);
}
