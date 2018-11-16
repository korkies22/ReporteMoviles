// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Move;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.Iterator;
import de.cisha.chess.model.Game;
import java.util.Set;
import de.cisha.chess.model.GameHolder;

public class BroadcastGameHolder extends GameHolder
{
    private boolean _isInLiveMode;
    private Set<LiveModeChangeListener> _liveModeListener;
    private int _numberOfLiveMoves;
    
    public BroadcastGameHolder(final Game game) {
        super(game);
        this._isInLiveMode = true;
        this._numberOfLiveMoves = -1;
    }
    
    private void incrementNumberOfLiveMoves(final boolean b, final int n) {
        final int numberOfLiveMoves = this._numberOfLiveMoves;
        final boolean b2 = false;
        int numberOfLiveMoves2;
        if (numberOfLiveMoves > 0) {
            numberOfLiveMoves2 = this._numberOfLiveMoves;
        }
        else {
            numberOfLiveMoves2 = 0;
        }
        int numberOfLiveMoves3 = b2 ? 1 : 0;
        if (!b) {
            if (this._isInLiveMode) {
                numberOfLiveMoves3 = (b2 ? 1 : 0);
            }
            else {
                numberOfLiveMoves3 = numberOfLiveMoves2 + n;
            }
        }
        if (numberOfLiveMoves3 != this._numberOfLiveMoves) {
            this._numberOfLiveMoves = numberOfLiveMoves3;
            final Iterator<LiveModeChangeListener> iterator = this._liveModeListener.iterator();
            while (iterator.hasNext()) {
                iterator.next().numberOfNewMovesChanged(this._numberOfLiveMoves);
            }
        }
    }
    
    private void initListenersLazy() {
        if (this._liveModeListener == null) {
            this._liveModeListener = Collections.newSetFromMap(new WeakHashMap<LiveModeChangeListener, Boolean>());
        }
    }
    
    private void notifyObserversLiveModeChanged(final boolean b) {
        this.initListenersLazy();
        final Iterator<LiveModeChangeListener> iterator = this._liveModeListener.iterator();
        while (iterator.hasNext()) {
            iterator.next().liveModeChanged(b);
        }
        if (b) {
            this.incrementNumberOfLiveMoves(true, 0);
        }
    }
    
    private void updateIsInLiveMode() {
        final Move currentMove = this.getCurrentMove();
        boolean isInLiveMode = true;
        if (currentMove == null) {
            final MoveContainer rootMoveContainer = this.getRootMoveContainer();
            if (rootMoveContainer.hasChildren()) {
                isInLiveMode = rootMoveContainer.getFirstMove().isUserMove();
            }
        }
        else if (currentMove.isUserMove()) {
            isInLiveMode = false;
        }
        else if (currentMove.hasChildren()) {
            isInLiveMode = currentMove.getNextMove().isUserMove();
        }
        if (this._isInLiveMode != isInLiveMode) {
            this.notifyObserversLiveModeChanged(this._isInLiveMode = isInLiveMode);
        }
    }
    
    public void addLiveModeChangeListener(final LiveModeChangeListener liveModeChangeListener) {
        this.initListenersLazy();
        this._liveModeListener.add(liveModeChangeListener);
    }
    
    public Move addNewServerMove(final int n, final SEP sep, final int n2, final int n3) {
        // monitorenter(this)
        Label_0015: {
            if (n != 0) {
                break Label_0015;
            }
        Label_0198_Outer:
            while (true) {
                while (true) {
                    Label_0228: {
                        while (true) {
                            try {
                                MoveContainer moveContainer = this.getRootMoveContainer();
                                // monitorexit(this)
                                Move move4;
                                while (true) {
                                    Move move = null;
                                    final Move move2 = null;
                                    if (moveContainer != null) {
                                        if (moveContainer.hasChild(sep)) {
                                            final Move child = moveContainer.getChild(sep);
                                            child.setMoveId(n3);
                                            child.setMoveTimeInMills(n2);
                                            child.setUserGenerated(false);
                                            this.incrementNumberOfLiveMoves(false, 1);
                                            if (child.hasSiblings()) {
                                                moveContainer.promoteMove(child);
                                            }
                                            this.notifyAllMovesChanged();
                                            super.notifyMoveChanged();
                                            break Label_0228;
                                        }
                                        final Move move3 = moveContainer.getResultingPosition().createMoveFrom(sep);
                                        move4 = move2;
                                        if (move3 != null) {
                                            moveContainer.addMove(move3);
                                            move3.setMoveId(n3);
                                            move3.setMoveTimeInMills(n2);
                                            move3.setUserGenerated(false);
                                            this.incrementNumberOfLiveMoves(false, 1);
                                            if (move3.hasSiblings()) {
                                                moveContainer.promoteMove(move3);
                                                this.notifyAllMovesChanged();
                                                super.notifyMoveChanged();
                                                break Label_0228;
                                            }
                                            this.notifyObserversNewMove(move3);
                                            break Label_0228;
                                        }
                                        else {
                                            move = move4;
                                            if (this._isInLiveMode) {
                                                this.gotoLiveGamePosition();
                                                move = move4;
                                            }
                                        }
                                    }
                                    return move;
                                    moveContainer = this.getLastLiveGameMove();
                                    continue Label_0198_Outer;
                                }
                                // monitorexit(this)
                                throw move4;
                            }
                            finally {
                                continue;
                            }
                            break;
                        }
                    }
                    continue;
                }
            }
        }
    }
    
    public void deleteMoveInMainlineWithId(int n) {
        final Move currentMove = this.getCurrentMove();
        final Iterator<Move> iterator = this.getRootMoveContainer().getAllMainLineMoves().iterator();
        while (true) {
            Move move;
            do {
                final boolean hasNext = iterator.hasNext();
                final Move move2 = null;
                Move move3 = null;
                if (!hasNext) {
                    final Move move4 = null;
                    move3 = move2;
                    move = move4;
                    if (move != null) {
                        if (move != currentMove && !move.getChildrenOfAllLevels().contains(currentMove)) {
                            this.selectMove(currentMove);
                            return;
                        }
                        this.selectMove(move3);
                    }
                    return;
                }
                move = iterator.next();
            } while (move.getMoveId() != n);
            if (move.getParent() instanceof Move) {
                final Move move3 = (Move)move.getParent();
            }
            move.removeFromParent();
            final Iterator<Move> iterator2 = move.getAllMainLineMoves().iterator();
            n = 1;
            while (iterator2.hasNext() && !iterator2.next().isUserMove()) {
                ++n;
            }
            this.incrementNumberOfLiveMoves(false, -n);
            this.notifyAllMovesChanged();
            continue;
        }
    }
    
    @Override
    public Move doMoveInCurrentPosition(final SEP sep) {
        final Move doMoveInCurrentPosition = super.doMoveInCurrentPosition(sep, true);
        this.updateIsInLiveMode();
        return doMoveInCurrentPosition;
    }
    
    public void enableLiveMode() {
        this.selectMove(this.getLastLiveGameMove());
    }
    
    public Move getLastLiveGameMove() {
        final Iterator<Move> iterator = this.getRootMoveContainer().getAllMainLineMoves().iterator();
        Move move = null;
        while (iterator.hasNext()) {
            final Move move2 = iterator.next();
            if (move2.isUserMove()) {
                break;
            }
            move = move2;
        }
        return move;
    }
    
    public void gotoLastNonUserMove() {
        MoveContainer moveContainer = this.getCurrentMove();
        boolean b;
        while (true) {
            b = (moveContainer instanceof Move);
            if (!b || !((Move)moveContainer).isUserMove()) {
                break;
            }
            moveContainer = moveContainer.getParent();
        }
        if (b) {
            this.selectMove((Move)moveContainer);
            return;
        }
        this.gotoStartingPosition();
    }
    
    public void gotoLiveGamePosition() {
        this.selectMove(this.getLastLiveGameMove());
    }
    
    public boolean isInLiveMode() {
        return this._isInLiveMode;
    }
    
    @Override
    protected void notifyMoveChanged() {
        super.notifyMoveChanged();
        this.updateIsInLiveMode();
    }
    
    @Override
    public void setNewGame(final Game newGame) {
        super.setNewGame(newGame);
        this.enableLiveMode();
    }
    
    public void setNewGameAndMergeOldUserMoves(final Game game) {
        final LinkedList<SEP> list = new LinkedList<SEP>();
        Move currentMove = this.getCurrentMove();
        Move move;
        while (true) {
            move = null;
            if (currentMove == null || !currentMove.hasParent()) {
                break;
            }
            list.addFirst(currentMove.getSEP());
            if (currentMove.getParent() instanceof Move) {
                currentMove = (Move)currentMove.getParent();
            }
            else {
                currentMove = null;
            }
        }
        final LinkedList<Move> list2 = new LinkedList<Move>();
        final LinkedList<Object> list3 = new LinkedList<Object>();
        list3.addAll(this.getRootMoveContainer().getChildren());
        while (!list3.isEmpty()) {
            final Move move2 = list3.removeFirst();
            if (move2.isUserMove() && !game.containsMove(move2, this.getRootMoveContainer())) {
                list2.addFirst(move2);
            }
            else {
                list3.addAll(move2.getChildren());
            }
        }
        boolean b2;
        while (true) {
            final boolean empty = list2.isEmpty();
            final boolean b = true;
            b2 = false;
            if (empty) {
                break;
            }
            final LinkedList<SEP> list4 = new LinkedList<SEP>();
            MoveContainer parent;
            Move move3;
            for (move3 = (Move)(parent = list2.pollFirst()); parent != null && parent instanceof Move; parent = parent.getParent()) {
                list4.addFirst(((Move)parent).getSEP());
            }
            Serializable child = game;
            boolean b3;
            while (true) {
                b3 = b;
                if (list4.isEmpty()) {
                    break;
                }
                final SEP sep = list4.pollFirst();
                if (((MoveContainer)child).hasChild(sep)) {
                    if (list4.isEmpty()) {
                        continue;
                    }
                    child = ((MoveContainer)child).getChild(sep);
                }
                else {
                    if (!list4.isEmpty()) {
                        b3 = false;
                        break;
                    }
                    continue;
                }
            }
            if (!b3 || !(move3 instanceof Move)) {
                continue;
            }
            final Move move4 = move3;
            move4.removeFromParent();
            ((MoveContainer)child).addMove(move4);
        }
        Serializable child2;
        if (!list.isEmpty()) {
            final SEP sep2 = list.getLast();
            SEP sep3 = list.pollFirst();
            b2 = false;
            for (child2 = game; ((MoveContainer)child2).hasChild(sep3); sep3 = list.pollFirst()) {
                child2 = ((MoveContainer)child2).getChild(sep3);
                if (!list.isEmpty()) {
                    b2 = (sep2 == list.getFirst());
                }
            }
        }
        else {
            child2 = game;
        }
        Move move5 = move;
        if (b2) {
            move5 = move;
            if (child2 instanceof Move) {
                move5 = (Move)child2;
            }
        }
        if (move5 == null) {
            this.setNewGame(game);
        }
        else {
            super.setNewGame(game);
            this.selectMove(move5);
        }
        this.updateIsInLiveMode();
    }
    
    public interface LiveModeChangeListener
    {
        void liveModeChanged(final boolean p0);
        
        void numberOfNewMovesChanged(final int p0);
    }
}
