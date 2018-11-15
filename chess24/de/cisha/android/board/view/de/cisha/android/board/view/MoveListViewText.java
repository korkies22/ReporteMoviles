/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.TextView
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import de.cisha.android.board.MainBoardView;
import de.cisha.android.board.view.MoveListView;
import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.Piece;
import java.util.List;

public class MoveListViewText
extends TextView
implements MoveListView {
    MoveContainer _allMoves;
    MainBoardView _mainBoardView;
    List<Move> _serverMoves;
    List<Move> _userMoves;

    public MoveListViewText(Context context) {
        super(context);
    }

    public MoveListViewText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MoveListViewText(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    private boolean isVariationEnd(Move move) {
        Object object;
        if (!move.hasChildren() && (object = move.getParent()) != null && (object = object.getChildren()) != null) {
            return move.equals(object.get(object.size() - 1));
        }
        return false;
    }

    private boolean isVariationStart(Move move) {
        MoveContainer moveContainer = move.getParent();
        if (moveContainer != null && moveContainer.hasVariants() && !moveContainer.getNextMove().equals(move)) {
            return true;
        }
        return false;
    }

    private void showMoves(MoveContainer object) {
        int n;
        int n2;
        this._allMoves = object;
        object = object.getChildrenOfAllLevels();
        int n3 = object.size();
        StringBuilder stringBuilder = new StringBuilder();
        int n4 = n2 = (n = 0);
        while (n < n3) {
            Move move = (Move)object.get(n);
            if (move.isUserMove()) {
                if (n == 0) {
                    stringBuilder.append("[");
                } else if (!((Move)object.get(n - 1)).isUserMove()) {
                    stringBuilder.append("[");
                }
            }
            boolean bl = move.getPiece().getColor();
            if (this.isVariationStart(move)) {
                stringBuilder.append("(");
                if (!bl) {
                    stringBuilder.append(move.getMoveNumber());
                    stringBuilder.append(". ... ");
                }
            }
            int n5 = n2;
            if (n2 != 0) {
                if (!bl) {
                    stringBuilder.append(move.getMoveNumber());
                    stringBuilder.append(". ... ");
                }
                n5 = 0;
            }
            if (bl) {
                stringBuilder.append(move.getMoveNumber());
                stringBuilder.append(". ");
            }
            stringBuilder.append(move.getSAN());
            stringBuilder.append(" ");
            n2 = n4;
            if (!move.hasChildren()) {
                n2 = n4 + 1;
            }
            n4 = n2;
            if (this.isVariationEnd(move)) {
                n4 = 0;
                do {
                    n5 = n == n3 - 1 ? n2 - 1 : n2;
                    if (n4 >= n5) break;
                    stringBuilder.append(")");
                    ++n4;
                } while (true);
                n5 = 1;
                n4 = 0;
            }
            if (move.isUserMove()) {
                if (n == n3 - 1) {
                    stringBuilder.append("]");
                } else if (!((Move)object.get(n + 1)).isUserMove()) {
                    stringBuilder.append("]");
                }
            }
            stringBuilder.append(" ");
            ++n;
            n2 = n5;
        }
        this.setText((CharSequence)stringBuilder.toString());
        this.invalidate();
    }

    @Override
    public void allMovesChanged(MoveContainer moveContainer) {
        this.showMoves(moveContainer);
    }

    @Override
    public boolean canSkipMoves() {
        return true;
    }

    @Override
    public void newMove(Move abstractMoveContainer) {
        if ((abstractMoveContainer = abstractMoveContainer.getGame()) != null) {
            this.showMoves(abstractMoveContainer);
        }
    }

    @Override
    public void selectedMoveChanged(Move move) {
    }

    @Override
    public void setMoveSelector(MoveSelector moveSelector) {
    }

    @Override
    public void setNavigationSelectionEnabled(boolean bl) {
    }
}
