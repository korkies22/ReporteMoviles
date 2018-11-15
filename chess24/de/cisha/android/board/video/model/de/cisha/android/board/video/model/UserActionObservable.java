/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.model;

import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class UserActionObservable
implements MoveExecutor,
MoveSelector {
    private MoveExecutor _moveExeutor;
    private MoveSelector _moveSelector;
    private Set<UserActionObserver> _observers = Collections.newSetFromMap(new WeakHashMap());

    public UserActionObservable(MoveExecutor moveExecutor, MoveSelector moveSelector) {
        this._moveExeutor = moveExecutor;
        this._moveSelector = moveSelector;
    }

    private void notifyUserActionObservers() {
        Iterator<UserActionObserver> iterator = this._observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().userDidAction();
        }
    }

    public void addUserActionObserver(UserActionObserver userActionObserver) {
        this._observers.add(userActionObserver);
    }

    @Override
    public boolean advanceOneMoveInCurrentLine() {
        this.notifyUserActionObservers();
        return this._moveExeutor.advanceOneMoveInCurrentLine();
    }

    @Override
    public Move doMoveInCurrentPosition(SEP sEP) {
        this.notifyUserActionObservers();
        return this._moveExeutor.doMoveInCurrentPosition(sEP);
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
    public void registerPremove(Square square, Square square2) {
        this.notifyUserActionObservers();
        this._moveExeutor.registerPremove(square, square2);
    }

    public void removeUserActionObserver(UserActionObserver userActionObserver) {
        this._observers.remove(userActionObserver);
    }

    @Override
    public boolean selectMove(Move move) {
        this.notifyUserActionObservers();
        return this._moveSelector.selectMove(move);
    }

    public static interface UserActionObserver {
        public void userDidAction();
    }

}
