// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view.notation;

import java.util.HashMap;
import android.view.View;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.Move;
import java.util.Map;
import android.view.View.OnClickListener;
import de.cisha.android.board.view.MoveListView;
import android.widget.LinearLayout;

public class NotationListView extends LinearLayout implements MoveListView
{
    private View.OnClickListener _clickListener;
    private boolean _invalidateNotationList;
    private Map<Move, MoveView> _mapMoveToView;
    private int _maxWidth;
    private boolean _moveSelectionEnabled;
    private MoveSelector _moveSelector;
    private Map<Move, Integer> _moveToMarkColor;
    private MoveContainer _rootMoveContainer;
    private MoveView _selectedMoveView;
    private boolean _setMaxWidth;
    private boolean _useUserMoves;
    
    public NotationListView(final Context context) {
        super(context);
        this._moveSelectionEnabled = true;
        this._setMaxWidth = false;
        this._useUserMoves = false;
        this._setMaxWidth = true;
        this.init();
    }
    
    public NotationListView(final Context context, final int maxWidth) {
        super(context);
        this._moveSelectionEnabled = true;
        this._setMaxWidth = false;
        this._useUserMoves = false;
        this._maxWidth = maxWidth;
        this.init();
    }
    
    public NotationListView(final Context context, final AttributeSet set) {
        super(context, set);
        this._moveSelectionEnabled = true;
        this._setMaxWidth = false;
        this._useUserMoves = false;
        this._setMaxWidth = true;
        this.init();
    }
    
    private Line addMovesToLine(final List<Move> list, Line line) {
        final Line parentLine = line.getParentLine();
        int lineNumber = line.getLineNumber();
        int n;
        if (parentLine != null) {
            n = parentLine.getLayer() + 1;
        }
        else {
            n = 0;
        }
        final Iterator<Move> iterator = list.iterator();
        while (iterator.hasNext()) {
            final MoveView moveView = this.createMoveView(iterator.next(), n);
            if (!line.addMoveView(moveView)) {
                line.setBlockLayout(true);
                ++lineNumber;
                final Line lineWith = this.createLineWith(lineNumber, n, parentLine, moveView);
                line.setNextLine(lineWith);
                line = lineWith;
            }
        }
        return line;
    }
    
    private void addVariationsFor(final MoveView moveView) {
        moveView.setVariationShown(true);
        moveView.refreshView();
        final Line line = moveView.getLine();
        if (line != null) {
            if (!line.reMeasureWidth()) {
                this._invalidateNotationList = true;
            }
            final Move move = moveView.getMove();
            final Line nextLine = line.getNextLine();
            final LinkedList list = new LinkedList<Move>(move.getParent().getChildren());
            list.remove(move);
            int n = line.getLineNumber();
            final Iterator<Move> iterator = (Iterator<Move>)list.iterator();
            Line addMovesToLine = line;
            while (iterator.hasNext()) {
                addMovesToLine = this.addMovesToLine(this.getMovesInLine(iterator.next()), this.createNewLineWithParent(line, addMovesToLine, n + 1));
                n = addMovesToLine.getLineNumber();
            }
            addMovesToLine.setNextLine(nextLine);
            line.updateChildrenLineNumbers();
        }
    }
    
    private Line createLineWith(final int lineNumber, final int n, final Line parentLine, final MoveView moveView) {
        final Line line = new Line(this.getContext(), n, this._maxWidth);
        this.addView((View)line, lineNumber);
        line.setParentLine(parentLine);
        line.setLineNumber(lineNumber);
        line.addMoveView(moveView);
        return line;
    }
    
    private MoveView createMoveView(final Move move, final int n) {
        final MoveView moveView = new MoveView(this.getContext(), move, n, this._useUserMoves);
        if (this._moveToMarkColor.containsKey(move)) {
            moveView.mark(this._moveToMarkColor.get(move));
        }
        this._mapMoveToView.put(move, moveView);
        moveView.setOnClickListener(this._clickListener);
        return moveView;
    }
    
    private Line createNewLineWithParent(final Line parentLine, final Line line, final int lineNumber) {
        final Line nextLine = new Line(this.getContext(), parentLine.getLayer() + 1, this._maxWidth);
        if (line.getLayer() == nextLine.getLayer()) {
            nextLine.setDividerTop(true);
        }
        this.addView((View)nextLine, lineNumber);
        nextLine.setLineNumber(lineNumber);
        nextLine.setParentLine(parentLine);
        line.setNextLine(nextLine);
        return nextLine;
    }
    
    private void fulfillWithMoveViews(Move firstMove) {
        final LinkedList<Move> list = new LinkedList<Move>();
        if (!this._mapMoveToView.containsKey(firstMove)) {
            list.add(firstMove);
            while (true) {
                for (MoveContainer moveContainer = firstMove.getParent(); moveContainer != null && moveContainer instanceof Move; moveContainer = moveContainer.getParent()) {
                    final Move move = firstMove = (Move)moveContainer;
                    if (this._mapMoveToView.containsKey(move)) {
                        final Iterator<Object> iterator = list.iterator();
                        Move move2 = firstMove;
                        while (iterator.hasNext()) {
                            final Move move3 = iterator.next();
                            if (this._mapMoveToView.containsKey(move3)) {
                                move2 = move3;
                            }
                            else if (!move3.hasSiblings()) {
                                final MoveView moveView = this._mapMoveToView.get(move2);
                                Line line;
                                if (moveView != null) {
                                    line = moveView.getLine();
                                }
                                else {
                                    line = null;
                                }
                                if (line == null) {
                                    continue;
                                }
                                final MoveView moveView2 = this.createMoveView(move3, line.getLayer());
                                if (line.addMoveView(moveView2)) {
                                    continue;
                                }
                                line.setBlockLayout(true);
                                final int layer = line.getLayer();
                                Line nextLine = line;
                                Label_0276: {
                                    if (line.getNextLine() != null) {
                                        nextLine = line;
                                        if (line.getNextLine().getLayer() > layer) {
                                            Line line2 = line;
                                            Line line3;
                                            do {
                                                line3 = (nextLine = line2.getNextLine());
                                                if (line3.getNextLine() == null) {
                                                    break Label_0276;
                                                }
                                                line2 = line3;
                                            } while (line3.getNextLine().getLayer() > layer);
                                            nextLine = line3;
                                        }
                                    }
                                }
                                final Line lineWith = this.createLineWith(nextLine.getLineNumber() + 1, layer, nextLine.getParentLine(), moveView2);
                                final Line nextLine2 = nextLine.getNextLine();
                                nextLine.setNextLine(lineWith);
                                lineWith.setNextLine(nextLine2);
                                nextLine.updateChildrenLineNumbers();
                            }
                            else {
                                MoveView moveView3;
                                if (move2 == null) {
                                    firstMove = this._rootMoveContainer.getFirstMove();
                                    if (firstMove != null) {
                                        moveView3 = this._mapMoveToView.get(firstMove);
                                    }
                                    else {
                                        moveView3 = null;
                                    }
                                }
                                else {
                                    moveView3 = this._mapMoveToView.get(move2.getNextMove());
                                }
                                if (moveView3 == null) {
                                    continue;
                                }
                                this.removeChildLinesFor(moveView3);
                                this.addVariationsFor(moveView3);
                                final Line line4 = moveView3.getLine();
                                if (line4 == null) {
                                    continue;
                                }
                                line4.updateChildrenLineNumbers();
                            }
                        }
                        return;
                    }
                    list.add(0, move);
                }
                firstMove = null;
                continue;
            }
        }
    }
    
    private List<Move> getMovesInLine(Move nextMove) {
        final LinkedList<Move> list = new LinkedList<Move>();
        list.add(nextMove);
        while (nextMove.hasChildren()) {
            list.add(nextMove.getNextMove());
            nextMove = nextMove.getNextMove();
        }
        return list;
    }
    
    private void init() {
        this.setOrientation(1);
        this._mapMoveToView = new HashMap<Move, MoveView>();
        this._moveToMarkColor = new HashMap<Move, Integer>();
        this._clickListener = (View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final MoveView moveView = (MoveView)view;
                if (NotationListView.this._moveSelectionEnabled && NotationListView.this._moveSelector != null) {
                    NotationListView.this.setSelectedMoveView(moveView, moveView.getMove());
                    final Move move = moveView.getMove();
                    if (moveView.showsVariation()) {
                        NotationListView.this.removeChildLinesFor(moveView);
                        moveView.getLine().updateChildrenLineNumbers();
                    }
                    else if (move.hasSiblings() && !move.isVariationStart()) {
                        NotationListView.this.removeChildLinesFor(moveView);
                        NotationListView.this.addVariationsFor(moveView);
                    }
                    NotationListView.this._moveSelector.selectMove(move);
                }
            }
        };
    }
    
    private void removeChildLinesFor(final MoveView moveView) {
        moveView.setVariationShown(false);
        final Line line = moveView.getLine();
        if (line != null) {
            for (final Line line2 : line.getAllChildLines()) {
                final Iterator<MoveView> iterator2 = line2.getMoves().iterator();
                while (iterator2.hasNext()) {
                    this._mapMoveToView.remove(iterator2.next().getMove());
                }
                line2.removeFromParent();
            }
        }
    }
    
    private void setSelectedMoveView(final MoveView selectedMoveView, final Move move) {
        if (this._invalidateNotationList) {
            this.allMovesChanged(this._rootMoveContainer);
            this.selectedMoveChanged(move);
            return;
        }
        if (this._selectedMoveView != null) {
            this._selectedMoveView.setSelected(false);
        }
        if (selectedMoveView != null) {
            selectedMoveView.setSelected(true);
        }
        this._selectedMoveView = selectedMoveView;
    }
    
    public void allMovesChanged(final MoveContainer rootMoveContainer) {
        this._rootMoveContainer = rootMoveContainer;
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                NotationListView.this._invalidateNotationList = false;
                NotationListView.this._selectedMoveView = null;
                NotationListView.this._mapMoveToView.clear();
                NotationListView.this._moveToMarkColor.clear();
                NotationListView.this.removeAllViews();
                final List<Move> allMainLineMoves = rootMoveContainer.getAllMainLineMoves();
                final Line line = new Line(NotationListView.this.getContext(), 0, NotationListView.this._maxWidth);
                line.setLineNumber(0);
                NotationListView.this.addView((View)line);
                NotationListView.this.addMovesToLine(allMainLineMoves, line);
            }
        });
    }
    
    public boolean canSkipMoves() {
        return true;
    }
    
    public int getTopPositionForMove(final Move move) {
        final boolean containsKey = this._mapMoveToView.containsKey(move);
        int top;
        final int n = top = 0;
        if (containsKey) {
            final Line line = this._mapMoveToView.get(move).getLine();
            top = n;
            if (line != null) {
                if (line.getTop() == 0 && line.getLineNumber() > 0 && line.getParentLine() != null) {
                    final Line parentLine = line.getParentLine();
                    top = n;
                    if (parentLine != null) {
                        return parentLine.getTop() / (1 + parentLine.getLineNumber()) * line.getLineNumber();
                    }
                }
                else {
                    top = line.getTop();
                }
            }
        }
        return top;
    }
    
    MoveView getViewForMove(final Move move) {
        return this._mapMoveToView.get(move);
    }
    
    public void markMoveWithColor(final Move move, final int n) {
        final MoveView moveView = this._mapMoveToView.get(move);
        if (moveView != null) {
            moveView.mark(n);
        }
        this._moveToMarkColor.put(move, n);
    }
    
    public void markMovesWithDeletionFlag(final Move move) {
        final Line line = this._mapMoveToView.get(move).getLine();
        final int layer = line.getLayer();
        line.markMovesDeletionStartingWith(move);
        Line line2 = line.getNextLine();
        int n = 999999;
        while (line2 != null) {
            final int layer2 = line2.getLayer();
            final List<MoveView> moves = line2.getMoves();
            Move move3;
            final Move move2 = move3 = null;
            if (moves != null) {
                move3 = move2;
                if (moves.size() > 0) {
                    move3 = moves.get(0).getMove();
                }
            }
            if (layer2 > n) {
                line2 = line2.getNextLine();
            }
            else {
                if (layer2 < layer) {
                    return;
                }
                Label_0184: {
                    if (move3 != null && layer2 > layer) {
                        if (move3.getHalfMoveNumber() <= move.getHalfMoveNumber()) {
                            n = layer;
                            break Label_0184;
                        }
                        line2.markAllMovesDeletion();
                    }
                    else if (layer2 == layer) {
                        if (move3.isVariationStart()) {
                            break;
                        }
                        line2.markAllMovesDeletion();
                    }
                    n = 999999;
                }
                line2 = line2.getNextLine();
            }
        }
    }
    
    public void newMove(final Move move) {
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (NotationListView.this._mapMoveToView.isEmpty()) {
                    NotationListView.this._rootMoveContainer = move.getGame();
                    NotationListView.this.createLineWith(0, 0, null, NotationListView.this.createMoveView(move, 0));
                }
                NotationListView.this.fulfillWithMoveViews(move);
            }
        });
    }
    
    protected void onMeasure(int maxWidth, final int n) {
        super.onMeasure(maxWidth, n);
        if (this._setMaxWidth) {
            maxWidth = this._maxWidth;
            this._maxWidth = this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
            if (maxWidth != this._maxWidth && this._rootMoveContainer != null) {
                this.allMovesChanged(this._rootMoveContainer);
            }
        }
    }
    
    public void selectedMoveChanged(final Move move) {
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (move != null && (NotationListView.this._selectedMoveView == null || !NotationListView.this._selectedMoveView.getMove().equals(move))) {
                    NotationListView.this.fulfillWithMoveViews(move);
                    NotationListView.this.setSelectedMoveView(NotationListView.this._mapMoveToView.get(move), move);
                    return;
                }
                if (move == null) {
                    NotationListView.this.setSelectedMoveView(null, move);
                }
            }
        });
    }
    
    public void setMarkUserMoves(final boolean b) {
        this._useUserMoves = b;
        final Iterator<MoveView> iterator = this._mapMoveToView.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().setMarkUserMoves(b);
        }
    }
    
    public void setMoveSelector(final MoveSelector moveSelector) {
        this._moveSelector = moveSelector;
    }
    
    public void setNavigationSelectionEnabled(final boolean moveSelectionEnabled) {
        this._moveSelectionEnabled = moveSelectionEnabled;
    }
    
    public void unMarkDeletionFlagOfAllMoves() {
        final Iterator<MoveView> iterator = this._mapMoveToView.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().markDeletion(false);
        }
    }
    
    public void unmarkMove(final Move move) {
        final MoveView moveView = this._mapMoveToView.get(move);
        this._moveToMarkColor.remove(move);
        if (moveView != null) {
            moveView.unmark();
        }
    }
}
