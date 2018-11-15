/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;
import java.util.List;

public interface MainBoardView
extends MoveExecutor {
    public void addArrowSquare(Square var1, Square var2, int var3);

    public boolean addMoveInCurrentPosition(SEP var1, int var2, int var3);

    public boolean addMoveInMainLine(SEP var1, int var2, int var3);

    public void doMoveInCurrentPositionWithAnimation(Move var1, int var2);

    public Move getMove(int var1);

    public List<Move> getMoveList();

    public void markSquare(Square var1, int var2);

    public void removeAllArrows();

    public void setPosition(Position var1);

    public void setUpdateViews(boolean var1);

    public void setUserInteractionEnabled(boolean var1);

    public void startRemoteEngine(String var1, String var2, String var3);

    public void unmarkField();

    public void unmarkSquare(Square var1);

    public void updateMoveList();
}
