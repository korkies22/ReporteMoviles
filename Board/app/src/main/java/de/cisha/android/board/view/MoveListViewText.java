// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.Game;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.chess.model.Move;
import java.util.List;
import de.cisha.android.board.MainBoardView;
import de.cisha.chess.model.MoveContainer;
import android.widget.TextView;

public class MoveListViewText extends TextView implements MoveListView
{
    MoveContainer _allMoves;
    MainBoardView _mainBoardView;
    List<Move> _serverMoves;
    List<Move> _userMoves;
    
    public MoveListViewText(final Context context) {
        super(context);
    }
    
    public MoveListViewText(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public MoveListViewText(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    private boolean isVariationEnd(final Move move) {
        if (!move.hasChildren()) {
            final MoveContainer parent = move.getParent();
            if (parent != null) {
                final List<Move> children = parent.getChildren();
                if (children != null) {
                    return move.equals(children.get(children.size() - 1));
                }
            }
        }
        return false;
    }
    
    private boolean isVariationStart(final Move move) {
        final MoveContainer parent = move.getParent();
        return parent != null && parent.hasVariants() && !parent.getNextMove().equals(move);
    }
    
    private void showMoves(final MoveContainer allMoves) {
        this._allMoves = allMoves;
        final List<Move> childrenOfAllLevels = allMoves.getChildrenOfAllLevels();
        final int size = childrenOfAllLevels.size();
        final StringBuilder sb = new StringBuilder();
        int i = 0;
        int n2;
        int n = n2 = i;
        while (i < size) {
            final Move move = childrenOfAllLevels.get(i);
            if (move.isUserMove()) {
                if (i == 0) {
                    sb.append("[");
                }
                else if (!childrenOfAllLevels.get(i - 1).isUserMove()) {
                    sb.append("[");
                }
            }
            final boolean color = move.getPiece().getColor();
            if (this.isVariationStart(move)) {
                sb.append("(");
                if (!color) {
                    sb.append(move.getMoveNumber());
                    sb.append(". ... ");
                }
            }
            int n3;
            if ((n3 = n) != 0) {
                if (!color) {
                    sb.append(move.getMoveNumber());
                    sb.append(". ... ");
                }
                n3 = 0;
            }
            if (color) {
                sb.append(move.getMoveNumber());
                sb.append(". ");
            }
            sb.append(move.getSAN());
            sb.append(" ");
            int n4 = n2;
            if (!move.hasChildren()) {
                n4 = n2 + 1;
            }
            n2 = n4;
            if (this.isVariationEnd(move)) {
                int n5 = 0;
                while (true) {
                    int n6;
                    if (i == size - 1) {
                        n6 = n4 - 1;
                    }
                    else {
                        n6 = n4;
                    }
                    if (n5 >= n6) {
                        break;
                    }
                    sb.append(")");
                    ++n5;
                }
                n3 = 1;
                n2 = 0;
            }
            if (move.isUserMove()) {
                if (i == size - 1) {
                    sb.append("]");
                }
                else if (!childrenOfAllLevels.get(i + 1).isUserMove()) {
                    sb.append("]");
                }
            }
            sb.append(" ");
            ++i;
            n = n3;
        }
        this.setText((CharSequence)sb.toString());
        this.invalidate();
    }
    
    public void allMovesChanged(final MoveContainer moveContainer) {
        this.showMoves(moveContainer);
    }
    
    public boolean canSkipMoves() {
        return true;
    }
    
    public void newMove(final Move move) {
        final Game game = move.getGame();
        if (game != null) {
            this.showMoves(game);
        }
    }
    
    public void selectedMoveChanged(final Move move) {
    }
    
    public void setMoveSelector(final MoveSelector moveSelector) {
    }
    
    public void setNavigationSelectionEnabled(final boolean b) {
    }
}
