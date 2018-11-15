/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.view.notation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import de.cisha.android.board.view.MoveListView;
import de.cisha.android.board.view.notation.Line;
import de.cisha.android.board.view.notation.MoveView;
import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveSelector;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NotationListView
extends LinearLayout
implements MoveListView {
    private View.OnClickListener _clickListener;
    private boolean _invalidateNotationList;
    private Map<Move, MoveView> _mapMoveToView;
    private int _maxWidth;
    private boolean _moveSelectionEnabled = true;
    private MoveSelector _moveSelector;
    private Map<Move, Integer> _moveToMarkColor;
    private MoveContainer _rootMoveContainer;
    private MoveView _selectedMoveView;
    private boolean _setMaxWidth = false;
    private boolean _useUserMoves = false;

    public NotationListView(Context context) {
        super(context);
        this._setMaxWidth = true;
        this.init();
    }

    public NotationListView(Context context, int n) {
        super(context);
        this._maxWidth = n;
        this.init();
    }

    public NotationListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this._setMaxWidth = true;
        this.init();
    }

    private Line addMovesToLine(List<Move> object, Line object2) {
        Line line = object2.getParentLine();
        int n = object2.getLineNumber();
        int n2 = line != null ? line.getLayer() + 1 : 0;
        Iterator<Move> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = this.createMoveView(iterator.next(), n2);
            if (object2.addMoveView((MoveView)((Object)object))) continue;
            object2.setBlockLayout(true);
            object = this.createLineWith(++n, n2, line, (MoveView)((Object)object));
            object2.setNextLine((Line)((Object)object));
            object2 = object;
        }
        return object2;
    }

    private void addVariationsFor(MoveView object) {
        object.setVariationShown(true);
        object.refreshView();
        Line line = object.getLine();
        if (line != null) {
            if (!line.reMeasureWidth()) {
                this._invalidateNotationList = true;
            }
            object = object.getMove();
            Line line2 = line.getNextLine();
            Object object2 = new LinkedList<Move>(object.getParent().getChildren());
            object2.remove(object);
            int n = line.getLineNumber();
            object2 = object2.iterator();
            object = line;
            while (object2.hasNext()) {
                object = this.addMovesToLine(this.getMovesInLine((Move)object2.next()), this.createNewLineWithParent(line, (Line)((Object)object), n + 1));
                n = object.getLineNumber();
            }
            object.setNextLine(line2);
            line.updateChildrenLineNumbers();
        }
    }

    private Line createLineWith(int n, int n2, Line line, MoveView moveView) {
        Line line2 = new Line(this.getContext(), n2, this._maxWidth);
        this.addView((View)line2, n);
        line2.setParentLine(line);
        line2.setLineNumber(n);
        line2.addMoveView(moveView);
        return line2;
    }

    private MoveView createMoveView(Move move, int n) {
        MoveView moveView = new MoveView(this.getContext(), move, n, this._useUserMoves);
        if (this._moveToMarkColor.containsKey(move)) {
            moveView.mark(this._moveToMarkColor.get(move));
        }
        this._mapMoveToView.put(move, moveView);
        moveView.setOnClickListener(this._clickListener);
        return moveView;
    }

    private Line createNewLineWithParent(Line line, Line line2, int n) {
        Line line3 = new Line(this.getContext(), line.getLayer() + 1, this._maxWidth);
        if (line2.getLayer() == line3.getLayer()) {
            line3.setDividerTop(true);
        }
        this.addView((View)line3, n);
        line3.setLineNumber(n);
        line3.setParentLine(line);
        line2.setNextLine(line3);
        return line3;
    }

    private void fulfillWithMoveViews(Move object) {
        Object object2 = new LinkedList<Move>();
        if (!this._mapMoveToView.containsKey(object)) {
            Object object3;
            MoveContainer moveContainer;
            block9 : {
                object2.add(object);
                for (moveContainer = object.getParent(); moveContainer != null && moveContainer instanceof Move; moveContainer = moveContainer.getParent()) {
                    object = object3 = (Move)moveContainer;
                    if (!this._mapMoveToView.containsKey(object3)) {
                        object2.add(0, object3);
                        continue;
                    }
                    break block9;
                }
                object = null;
            }
            object2 = object2.iterator();
            moveContainer = object;
            while (object2.hasNext()) {
                object3 = (Move)object2.next();
                if (this._mapMoveToView.containsKey(object3)) {
                    moveContainer = object3;
                    continue;
                }
                if (!object3.hasSiblings()) {
                    LinearLayout linearLayout;
                    int n;
                    block10 : {
                        object = this._mapMoveToView.get(moveContainer);
                        object = object != null ? object.getLine() : null;
                        if (object == null || object.addMoveView((MoveView)(linearLayout = this.createMoveView((Move)object3, object.getLayer())))) continue;
                        object.setBlockLayout(true);
                        n = object.getLayer();
                        object3 = object;
                        if (object.getNextLine() != null) {
                            object3 = object;
                            if (object.getNextLine().getLayer() > n) {
                                object3 = object;
                                do {
                                    object3 = object = object3.getNextLine();
                                    if (object.getNextLine() == null) break block10;
                                    object3 = object;
                                } while (object.getNextLine().getLayer() > n);
                                object3 = object;
                            }
                        }
                    }
                    object = this.createLineWith(object3.getLineNumber() + 1, n, object3.getParentLine(), (MoveView)linearLayout);
                    linearLayout = object3.getNextLine();
                    object3.setNextLine((Line)((Object)object));
                    object.setNextLine((Line)linearLayout);
                    object3.updateChildrenLineNumbers();
                    continue;
                }
                object = moveContainer == null ? ((object = this._rootMoveContainer.getFirstMove()) != null ? this._mapMoveToView.get(object) : null) : this._mapMoveToView.get(((AbstractMoveContainer)moveContainer).getNextMove());
                if (object == null) continue;
                this.removeChildLinesFor((MoveView)((Object)object));
                this.addVariationsFor((MoveView)((Object)object));
                if ((object = object.getLine()) == null) continue;
                object.updateChildrenLineNumbers();
            }
        }
    }

    private List<Move> getMovesInLine(Move move) {
        LinkedList<Move> linkedList = new LinkedList<Move>();
        linkedList.add(move);
        while (move.hasChildren()) {
            linkedList.add(move.getNextMove());
            move = move.getNextMove();
        }
        return linkedList;
    }

    private void init() {
        this.setOrientation(1);
        this._mapMoveToView = new HashMap<Move, MoveView>();
        this._moveToMarkColor = new HashMap<Move, Integer>();
        this._clickListener = new View.OnClickListener(){

            public void onClick(View object) {
                object = (MoveView)((Object)object);
                if (NotationListView.this._moveSelectionEnabled && NotationListView.this._moveSelector != null) {
                    NotationListView.this.setSelectedMoveView((MoveView)((Object)object), object.getMove());
                    Move move = object.getMove();
                    if (object.showsVariation()) {
                        NotationListView.this.removeChildLinesFor((MoveView)((Object)object));
                        object.getLine().updateChildrenLineNumbers();
                    } else if (move.hasSiblings() && !move.isVariationStart()) {
                        NotationListView.this.removeChildLinesFor((MoveView)((Object)object));
                        NotationListView.this.addVariationsFor((MoveView)((Object)object));
                    }
                    NotationListView.this._moveSelector.selectMove(move);
                }
            }
        };
    }

    private void removeChildLinesFor(MoveView object) {
        object.setVariationShown(false);
        object = object.getLine();
        if (object != null) {
            for (Line line : object.getAllChildLines()) {
                for (MoveView moveView : line.getMoves()) {
                    this._mapMoveToView.remove(moveView.getMove());
                }
                line.removeFromParent();
            }
        }
    }

    private void setSelectedMoveView(MoveView moveView, Move move) {
        if (this._invalidateNotationList) {
            this.allMovesChanged(this._rootMoveContainer);
            this.selectedMoveChanged(move);
            return;
        }
        if (this._selectedMoveView != null) {
            this._selectedMoveView.setSelected(false);
        }
        if (moveView != null) {
            moveView.setSelected(true);
        }
        this._selectedMoveView = moveView;
    }

    @Override
    public void allMovesChanged(final MoveContainer moveContainer) {
        this._rootMoveContainer = moveContainer;
        this.post(new Runnable(){

            @Override
            public void run() {
                NotationListView.this._invalidateNotationList = false;
                NotationListView.this._selectedMoveView = null;
                NotationListView.this._mapMoveToView.clear();
                NotationListView.this._moveToMarkColor.clear();
                NotationListView.this.removeAllViews();
                List<Move> list = moveContainer.getAllMainLineMoves();
                Line line = new Line(NotationListView.this.getContext(), 0, NotationListView.this._maxWidth);
                line.setLineNumber(0);
                NotationListView.this.addView((View)line);
                NotationListView.this.addMovesToLine(list, line);
            }
        });
    }

    @Override
    public boolean canSkipMoves() {
        return true;
    }

    public int getTopPositionForMove(Move object) {
        int n;
        boolean bl = this._mapMoveToView.containsKey(object);
        int n2 = n = 0;
        if (bl) {
            object = this._mapMoveToView.get(object).getLine();
            n2 = n;
            if (object != null) {
                if (object.getTop() == 0 && object.getLineNumber() > 0 && object.getParentLine() != null) {
                    Line line = object.getParentLine();
                    n2 = n;
                    if (line != null) {
                        return line.getTop() / (1 + line.getLineNumber()) * object.getLineNumber();
                    }
                } else {
                    n2 = object.getTop();
                }
            }
        }
        return n2;
    }

    MoveView getViewForMove(Move move) {
        return this._mapMoveToView.get(move);
    }

    public void markMoveWithColor(Move move, int n) {
        MoveView moveView = this._mapMoveToView.get(move);
        if (moveView != null) {
            moveView.mark(n);
        }
        this._moveToMarkColor.put(move, n);
    }

    public void markMovesWithDeletionFlag(Move move) {
        Line line = this._mapMoveToView.get(move).getLine();
        int n = line.getLayer();
        line.markMovesDeletionStartingWith(move);
        line = line.getNextLine();
        int n2 = 999999;
        while (line != null) {
            block9 : {
                block8 : {
                    Move move2;
                    int n3;
                    block6 : {
                        block7 : {
                            Move move3;
                            n3 = line.getLayer();
                            List<MoveView> list = line.getMoves();
                            move2 = move3 = null;
                            if (list != null) {
                                move2 = move3;
                                if (list.size() > 0) {
                                    move2 = list.get(0).getMove();
                                }
                            }
                            if (n3 > n2) {
                                line = line.getNextLine();
                                continue;
                            }
                            if (n3 < n) {
                                return;
                            }
                            if (move2 == null || n3 <= n) break block6;
                            if (move2.getHalfMoveNumber() <= move.getHalfMoveNumber()) break block7;
                            line.markAllMovesDeletion();
                            break block8;
                        }
                        n2 = n;
                        break block9;
                    }
                    if (n3 == n) {
                        if (move2.isVariationStart()) break;
                        line.markAllMovesDeletion();
                    }
                }
                n2 = 999999;
            }
            line = line.getNextLine();
        }
    }

    @Override
    public void newMove(final Move move) {
        this.post(new Runnable(){

            @Override
            public void run() {
                if (NotationListView.this._mapMoveToView.isEmpty()) {
                    NotationListView.this._rootMoveContainer = move.getGame();
                    MoveView moveView = NotationListView.this.createMoveView(move, 0);
                    NotationListView.this.createLineWith(0, 0, null, moveView);
                }
                NotationListView.this.fulfillWithMoveViews(move);
            }
        });
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (this._setMaxWidth) {
            n = this._maxWidth;
            this._maxWidth = this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
            if (n != this._maxWidth && this._rootMoveContainer != null) {
                this.allMovesChanged(this._rootMoveContainer);
            }
        }
    }

    @Override
    public void selectedMoveChanged(final Move move) {
        this.post(new Runnable(){

            @Override
            public void run() {
                if (!(move == null || NotationListView.this._selectedMoveView != null && NotationListView.this._selectedMoveView.getMove().equals(move))) {
                    NotationListView.this.fulfillWithMoveViews(move);
                    NotationListView.this.setSelectedMoveView((MoveView)((Object)NotationListView.this._mapMoveToView.get(move)), move);
                    return;
                }
                if (move == null) {
                    NotationListView.this.setSelectedMoveView(null, move);
                }
            }
        });
    }

    public void setMarkUserMoves(boolean bl) {
        this._useUserMoves = bl;
        Iterator<MoveView> iterator = this._mapMoveToView.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().setMarkUserMoves(bl);
        }
    }

    @Override
    public void setMoveSelector(MoveSelector moveSelector) {
        this._moveSelector = moveSelector;
    }

    @Override
    public void setNavigationSelectionEnabled(boolean bl) {
        this._moveSelectionEnabled = bl;
    }

    public void unMarkDeletionFlagOfAllMoves() {
        Iterator<MoveView> iterator = this._mapMoveToView.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().markDeletion(false);
        }
    }

    public void unmarkMove(Move move) {
        MoveView moveView = this._mapMoveToView.get(move);
        this._moveToMarkColor.remove(move);
        if (moveView != null) {
            moveView.unmark();
        }
    }

}
