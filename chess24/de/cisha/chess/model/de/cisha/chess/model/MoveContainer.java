/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.position.Position;
import java.util.List;

public interface MoveContainer {
    public void addMove(Move var1);

    public void addMoveInMainLine(Move var1);

    public boolean containsMove(Move var1, MoveContainer var2);

    public List<Move> getAllMainLineMoves();

    public Move getChild(SEP var1);

    public List<Move> getChildren();

    public List<Move> getChildrenOfAllLevels();

    public String getComment();

    public Move getFirstMove();

    public Game getGame();

    public Move getLastMoveinMainLine();

    public int getMoveId();

    public int getMoveTimeInMills();

    public Move getNextMove();

    public MoveContainer getParent();

    public Position getResultingPosition();

    public String getSymbolMisc();

    public String getSymbolMove();

    public String getSymbolPosition();

    public int getTotalTimeTakenInMills();

    public int getVariationLevel();

    public String getVariationPrefix();

    public boolean hasChild(SEP var1);

    public boolean hasChildren();

    public boolean hasSiblings();

    public boolean hasVariants();

    public boolean isMainLine();

    public boolean isMainMove();

    public void promoteMove(Move var1);

    public void removeChildAllMoves();

    public void removeMove(Move var1);

    public void setComment(String var1);

    public void setMoveTimeInMills(int var1);

    public void setSymbolMisc(String var1);

    public void setSymbolMove(String var1);

    public void setSymbolPosition(String var1);
}
