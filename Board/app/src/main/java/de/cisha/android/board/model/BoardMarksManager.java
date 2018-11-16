// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.model;

import java.util.LinkedList;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import java.util.Iterator;
import de.cisha.chess.model.MovesObservable;
import java.util.HashMap;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.Set;
import java.util.Map;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MovesObserver;
import de.cisha.android.board.view.BoardMarkingDisplay;

public class BoardMarksManager implements BoardMarkingDisplay, MovesObserver
{
    private MoveContainer _currentGame;
    private BoardMarks _currentMarks;
    private MoveContainer _currentMove;
    private Map<MoveContainer, BoardMarks> _initialMarks;
    private Map<MoveContainer, BoardMarks> _marks;
    private Set<BoardMarkingObserver> _observers;
    private boolean _updateObservers;
    
    public BoardMarksManager() {
        this._observers = Collections.newSetFromMap(new WeakHashMap<BoardMarkingObserver, Boolean>());
        this._updateObservers = true;
        this._currentGame = null;
        this._currentMove = null;
        this._marks = new HashMap<MoveContainer, BoardMarks>();
    }
    
    public BoardMarksManager(final BoardMarksManager boardMarksManager, final MovesObservable movesObservable) {
        this._observers = Collections.newSetFromMap(new WeakHashMap<BoardMarkingObserver, Boolean>());
        this._updateObservers = true;
        this._currentGame = null;
        this._currentMove = null;
        this._marks = new HashMap<MoveContainer, BoardMarks>(boardMarksManager._marks);
    }
    
    private void notifyMarkingObservers() {
        if (this._updateObservers) {
            final BoardMarks currentMarks = this.getCurrentMarks();
            final Iterator<BoardMarkingObserver> iterator = this._observers.iterator();
            while (iterator.hasNext()) {
                iterator.next().boardMarkingChanged(currentMarks);
            }
        }
    }
    
    @Override
    public void addArrowSquare(final Square square, final Square square2, final int n) {
        this.getCurrentMarks().getArrowContainer().addArrow(new Arrow(new SEP(square, square2), ArrowCategory.USER_MARKS, n));
        this.notifyMarkingObservers();
    }
    
    public void addMarkingObserver(final BoardMarkingObserver boardMarkingObserver) {
        this._observers.add(boardMarkingObserver);
    }
    
    @Override
    public void allMovesChanged(final MoveContainer currentGame) {
        this._currentGame = currentGame;
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
            }
            else if (this._currentGame != null) {
                this._marks.put(this._currentGame, this._currentMarks);
            }
        }
        return this._currentMarks;
    }
    
    @Override
    public void markSquare(final Square square, final int n) {
        this.getCurrentMarks().getSquareMarkings().addSquareMark(square, n);
        this.notifyMarkingObservers();
    }
    
    @Override
    public void newMove(final Move move) {
    }
    
    @Override
    public void removeAllArrows() {
        this.getCurrentMarks().getArrowContainer().removeArrowsForCategory(ArrowCategory.USER_MARKS);
        this.notifyMarkingObservers();
    }
    
    @Override
    public void removeArrow(final Square square, final Square square2) {
        final SEP sep = new SEP(square, square2);
        final LinkedList<Arrow> list = new LinkedList<Arrow>();
        final ArrowContainer arrowContainer = this.getCurrentMarks().getArrowContainer();
        for (final Arrow arrow : arrowContainer.getAllArrows()) {
            if (arrow.getSep().equals(sep)) {
                list.add(arrow);
            }
        }
        final Iterator<Object> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            arrowContainer.removeArrow(iterator2.next());
        }
        this.notifyMarkingObservers();
    }
    
    @Override
    public void removeArrowSquare(final Square square, final Square square2, final int n) {
        this.getCurrentMarks().getArrowContainer().removeArrow(new Arrow(new SEP(square, square2), ArrowCategory.USER_MARKS, n));
        this.notifyMarkingObservers();
    }
    
    public void removeMarkingObserver(final BoardMarkingObserver boardMarkingObserver) {
        this._observers.remove(boardMarkingObserver);
    }
    
    @Override
    public void reset() {
        this.resetWithInitialValues(this._initialMarks);
    }
    
    public void resetWithInitialValues(final Map<MoveContainer, BoardMarks> initialMarks) {
        this._initialMarks = initialMarks;
        if (this._initialMarks == null) {
            this._initialMarks = new HashMap<MoveContainer, BoardMarks>();
        }
        this._marks = new HashMap<MoveContainer, BoardMarks>();
        for (final MoveContainer moveContainer : this._initialMarks.keySet()) {
            this._marks.put(moveContainer, new BoardMarks(this._initialMarks.get(moveContainer)));
        }
        this._currentMove = null;
        this._currentMarks = null;
    }
    
    @Override
    public void selectedMoveChanged(final Move currentMove) {
        this._currentMove = currentMove;
        if (this._currentMove != null) {
            this._currentMarks = this._marks.get(currentMove);
        }
        else if (this._currentGame != null) {
            this._currentMarks = this._marks.get(this._currentGame);
        }
        else {
            this._currentMarks = null;
        }
        this.notifyMarkingObservers();
    }
    
    @Override
    public void setUpdateArrows(final boolean updateObservers) {
        final boolean b = updateObservers && !this._updateObservers;
        this._updateObservers = updateObservers;
        if (b) {
            this.notifyMarkingObservers();
        }
    }
    
    @Override
    public void unmarkField() {
        this.getCurrentMarks().getSquareMarkings().clear();
        this.notifyMarkingObservers();
    }
    
    @Override
    public void unmarkSquare(final Square square) {
        this.getCurrentMarks().getSquareMarkings().removeSquareMark(square);
        this.notifyMarkingObservers();
    }
    
    public interface BoardMarkingObserver
    {
        void boardMarkingChanged(final BoardMarks p0);
    }
}
