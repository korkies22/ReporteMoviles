// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.exercise;

import de.cisha.chess.model.Move;
import java.util.Iterator;
import de.cisha.chess.model.SEP;
import android.os.Handler;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.Set;
import de.cisha.chess.model.GameHolder;

public class TacticsGameHolder extends GameHolder implements TacticsObservable
{
    private Set<TacticsObserver> _tacticObservers;
    private TacticsExerciseUserSolution _tacticsUserSolution;
    
    public TacticsGameHolder(final TacticsExerciseUserSolution tacticsUserSolution) {
        super(tacticsUserSolution.getGame());
        this._tacticObservers = Collections.newSetFromMap(new WeakHashMap<TacticsObserver, Boolean>());
        this._tacticsUserSolution = tacticsUserSolution;
        this.gotoStartingPosition();
        this.checkAndDoComputerMove();
    }
    
    private void checkAndDoComputerMove() {
        if (this._tacticsUserSolution.getState() == TacticsExerciseUserState.PARTIALLY_SOLVED_CORRECT_DO_COMPUTERMOVE) {
            final SEP currentComputerMove = this._tacticsUserSolution.getCurrentComputerMove();
            if (currentComputerMove != null) {
                new Handler().postDelayed((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        TacticsGameHolder.this.doMoveInCurrentPosition(currentComputerMove);
                    }
                }, 1000L);
            }
        }
    }
    
    private void notifyTacticObserversTacticsCompleted() {
        final Iterator<TacticsObserver> iterator = this._tacticObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().tacticsSolved(this._tacticsUserSolution);
        }
    }
    
    @Override
    public void addTacticsObserver(final TacticsObserver tacticsObserver) {
        this._tacticObservers.add(tacticsObserver);
    }
    
    @Override
    public Move doMoveInCurrentPosition(final SEP sep) {
        final TacticsExerciseUserState addSolutionMove = this._tacticsUserSolution.addSolutionMove(sep);
        final Move doMoveInCurrentPosition = super.doMoveInCurrentPosition(sep);
        if (addSolutionMove.isCompleted()) {
            this.notifyTacticObserversTacticsCompleted();
        }
        this.checkAndDoComputerMove();
        return doMoveInCurrentPosition;
    }
    
    public TacticsExerciseUserSolution getCurrentTacticsPuzzle() {
        return this._tacticsUserSolution;
    }
    
    public void removeAllTacticsObservers() {
        this._tacticObservers.clear();
    }
    
    @Override
    public void removeTacticsObserver(final TacticsObserver tacticsObserver) {
        this._tacticObservers.remove(tacticsObserver);
    }
    
    public void setNewTacticsPuzzle(final TacticsExerciseUserSolution tacticsUserSolution) {
        this.setNewGame(tacticsUserSolution.getGame());
        this._tacticsUserSolution = tacticsUserSolution;
        this.gotoStartingPosition();
        this.checkAndDoComputerMove();
    }
}
