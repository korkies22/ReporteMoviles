// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model;

import de.cisha.chess.model.Square;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.Set;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.MoveExecutor;

public class UserActionObservable implements MoveExecutor, MoveSelector
{
    private MoveExecutor _moveExeutor;
    private MoveSelector _moveSelector;
    private Set<UserActionObserver> _observers;
    
    public UserActionObservable(final MoveExecutor moveExeutor, final MoveSelector moveSelector) {
        this._observers = Collections.newSetFromMap(new WeakHashMap<UserActionObserver, Boolean>());
        this._moveExeutor = moveExeutor;
        this._moveSelector = moveSelector;
    }
    
    private void notifyUserActionObservers() {
        final Iterator<UserActionObserver> iterator = this._observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().userDidAction();
        }
    }
    
    public void addUserActionObserver(final UserActionObserver userActionObserver) {
        this._observers.add(userActionObserver);
    }
    
    @Override
    public boolean advanceOneMoveInCurrentLine() {
        this.notifyUserActionObservers();
        return this._moveExeutor.advanceOneMoveInCurrentLine();
    }
    
    @Override
    public Move doMoveInCurrentPosition(final SEP sep) {
        this.notifyUserActionObservers();
        return this._moveExeutor.doMoveInCurrentPosition(sep);
    }
    
    @Override
    public boolean goBackOneMove() {
        this.notifyUserActionObservers();
        return this._moveExeutor.goBackOneMove();
    }
    
    @Override
    public void gotoEndingPosition() {
        this.notifyUserActionObservers();
        this._moveExeutor.gotoEndingPosition();
    }
    
    @Override
    public boolean gotoStartingPosition() {
        this.notifyUserActionObservers();
        return this._moveExeutor.gotoStartingPosition();
    }
    
    @Override
    public void registerPremove(final Square square, final Square square2) {
        this.notifyUserActionObservers();
        this._moveExeutor.registerPremove(square, square2);
    }
    
    public void removeUserActionObserver(final UserActionObserver userActionObserver) {
        this._observers.remove(userActionObserver);
    }
    
    @Override
    public boolean selectMove(final Move move) {
        this.notifyUserActionObservers();
        return this._moveSelector.selectMove(move);
    }
    
    public interface UserActionObserver
    {
        void userDidAction();
    }
}
