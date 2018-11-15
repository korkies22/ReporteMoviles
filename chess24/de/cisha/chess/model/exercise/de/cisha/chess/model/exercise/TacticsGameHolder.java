/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package de.cisha.chess.model.exercise;

import android.os.Handler;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import de.cisha.chess.model.exercise.TacticsExerciseUserState;
import de.cisha.chess.model.exercise.TacticsObservable;
import de.cisha.chess.model.exercise.TacticsObserver;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class TacticsGameHolder
extends GameHolder
implements TacticsObservable {
    private Set<TacticsObserver> _tacticObservers = Collections.newSetFromMap(new WeakHashMap());
    private TacticsExerciseUserSolution _tacticsUserSolution;

    public TacticsGameHolder(TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        super(tacticsExerciseUserSolution.getGame());
        this._tacticsUserSolution = tacticsExerciseUserSolution;
        this.gotoStartingPosition();
        this.checkAndDoComputerMove();
    }

    private void checkAndDoComputerMove() {
        Object object;
        if (this._tacticsUserSolution.getState() == TacticsExerciseUserState.PARTIALLY_SOLVED_CORRECT_DO_COMPUTERMOVE && (object = this._tacticsUserSolution.getCurrentComputerMove()) != null) {
            object = new Runnable((SEP)object){
                final /* synthetic */ SEP val$computerSep;
                {
                    this.val$computerSep = sEP;
                }

                @Override
                public void run() {
                    TacticsGameHolder.this.doMoveInCurrentPosition(this.val$computerSep);
                }
            };
            new Handler().postDelayed((Runnable)object, 1000L);
        }
    }

    private void notifyTacticObserversTacticsCompleted() {
        Iterator<TacticsObserver> iterator = this._tacticObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().tacticsSolved(this._tacticsUserSolution);
        }
    }

    @Override
    public void addTacticsObserver(TacticsObserver tacticsObserver) {
        this._tacticObservers.add(tacticsObserver);
    }

    @Override
    public Move doMoveInCurrentPosition(SEP serializable) {
        TacticsExerciseUserState tacticsExerciseUserState = this._tacticsUserSolution.addSolutionMove((SEP)serializable);
        serializable = super.doMoveInCurrentPosition((SEP)serializable);
        if (tacticsExerciseUserState.isCompleted()) {
            this.notifyTacticObserversTacticsCompleted();
        }
        this.checkAndDoComputerMove();
        return serializable;
    }

    public TacticsExerciseUserSolution getCurrentTacticsPuzzle() {
        return this._tacticsUserSolution;
    }

    public void removeAllTacticsObservers() {
        this._tacticObservers.clear();
    }

    @Override
    public void removeTacticsObserver(TacticsObserver tacticsObserver) {
        this._tacticObservers.remove(tacticsObserver);
    }

    public void setNewTacticsPuzzle(TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        this.setNewGame(tacticsExerciseUserSolution.getGame());
        this._tacticsUserSolution = tacticsExerciseUserSolution;
        this.gotoStartingPosition();
        this.checkAndDoComputerMove();
    }

}
