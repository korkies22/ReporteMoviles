/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.model;

import de.cisha.android.board.model.Arrow;
import de.cisha.android.board.model.ArrowCategory;
import de.cisha.android.board.model.ArrowContainer;
import de.cisha.android.board.model.BoardMarks;
import de.cisha.android.board.model.SquareMarkings;
import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MovesObservable;
import de.cisha.chess.model.MovesObserver;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class BoardMarksManager
implements BoardMarkingDisplay,
MovesObserver {
    private MoveContainer _currentGame = null;
    private BoardMarks _currentMarks;
    private MoveContainer _currentMove = null;
    private Map<MoveContainer, BoardMarks> _initialMarks;
    private Map<MoveContainer, BoardMarks> _marks;
    private Set<BoardMarkingObserver> _observers = Collections.newSetFromMap(new WeakHashMap());
    private boolean _updateObservers = true;

    public BoardMarksManager() {
        this._marks = new HashMap<MoveContainer, BoardMarks>();
    }

    public BoardMarksManager(BoardMarksManager boardMarksManager, MovesObservable movesObservable) {
        this._marks = new HashMap<MoveContainer, BoardMarks>(boardMarksManager._marks);
    }

    private void notifyMarkingObservers() {
        if (this._updateObservers) {
            BoardMarks boardMarks = this.getCurrentMarks();
            Iterator<BoardMarkingObserver> iterator = this._observers.iterator();
            while (iterator.hasNext()) {
                iterator.next().boardMarkingChanged(boardMarks);
            }
        }
    }

    @Override
    public void addArrowSquare(Square object, Square square, int n) {
        object = new Arrow(new SEP((Square)object, square), ArrowCategory.USER_MARKS, n);
        this.getCurrentMarks().getArrowContainer().addArrow((Arrow)object);
        this.notifyMarkingObservers();
    }

    public void addMarkingObserver(BoardMarkingObserver boardMarkingObserver) {
        this._observers.add(boardMarkingObserver);
    }

    @Override
    public void allMovesChanged(MoveContainer moveContainer) {
        this._currentGame = moveContainer;
        this._currentMarks = this._marks.get(this._currentGame);
        this.notifyMarkingObservers();
    }

    @Override
    public boolean canSkipMoves() {
        return false;
    }

    public BoardMarks getCurrentMarks() {
        if (this._currentMarks == null) {
            this._currentMarks = new BoardMarks();
            if (this._currentMove != null) {
                this._marks.put(this._currentMove, this._currentMarks);
            } else if (this._currentGame != null) {
                this._marks.put(this._currentGame, this._currentMarks);
            }
        }
        return this._currentMarks;
    }

    @Override
    public void markSquare(Square square, int n) {
        this.getCurrentMarks().getSquareMarkings().addSquareMark(square, n);
        this.notifyMarkingObservers();
    }

    @Override
    public void newMove(Move move) {
    }

    @Override
    public void removeAllArrows() {
        this.getCurrentMarks().getArrowContainer().removeArrowsForCategory(ArrowCategory.USER_MARKS);
        this.notifyMarkingObservers();
    }

    @Override
    public void removeArrow(Square object, Square object2) {
        object2 = new SEP((Square)object, (Square)object2);
        LinkedList<Arrow> linkedList = new LinkedList<Arrow>();
        object = this.getCurrentMarks().getArrowContainer();
        for (Arrow arrow : object.getAllArrows()) {
            if (!arrow.getSep().equals(object2)) continue;
            linkedList.add(arrow);
        }
        object2 = linkedList.iterator();
        while (object2.hasNext()) {
            object.removeArrow((Arrow)object2.next());
        }
        this.notifyMarkingObservers();
    }

    @Override
    public void removeArrowSquare(Square object, Square square, int n) {
        object = new Arrow(new SEP((Square)object, square), ArrowCategory.USER_MARKS, n);
        this.getCurrentMarks().getArrowContainer().removeArrow((Arrow)object);
        this.notifyMarkingObservers();
    }

    public void removeMarkingObserver(BoardMarkingObserver boardMarkingObserver) {
        this._observers.remove(boardMarkingObserver);
    }

    @Override
    public void reset() {
        this.resetWithInitialValues(this._initialMarks);
    }

    public void resetWithInitialValues(Map<MoveContainer, BoardMarks> object) {
        this._initialMarks = object;
        if (this._initialMarks == null) {
            this._initialMarks = new HashMap<MoveContainer, BoardMarks>();
        }
        this._marks = new HashMap<MoveContainer, BoardMarks>();
        for (MoveContainer moveContainer : this._initialMarks.keySet()) {
            BoardMarks boardMarks = new BoardMarks(this._initialMarks.get(moveContainer));
            this._marks.put(moveContainer, boardMarks);
        }
        this._currentMove = null;
        this._currentMarks = null;
    }

    @Override
    public void selectedMoveChanged(Move move) {
        this._currentMove = move;
        this._currentMarks = this._currentMove != null ? this._marks.get(move) : (this._currentGame != null ? this._marks.get(this._currentGame) : null);
        this.notifyMarkingObservers();
    }

    @Override
    public void setUpdateArrows(boolean bl) {
        boolean bl2 = bl && !this._updateObservers;
        this._updateObservers = bl;
        if (bl2) {
            this.notifyMarkingObservers();
        }
    }

    @Override
    public void unmarkField() {
        this.getCurrentMarks().getSquareMarkings().clear();
        this.notifyMarkingObservers();
    }

    @Override
    public void unmarkSquare(Square square) {
        this.getCurrentMarks().getSquareMarkings().removeSquareMark(square);
        this.notifyMarkingObservers();
    }

    public static interface BoardMarkingObserver {
        public void boardMarkingChanged(BoardMarks var1);
    }

}
