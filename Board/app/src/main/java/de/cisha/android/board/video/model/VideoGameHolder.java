// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model;

import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.NullMove;
import de.cisha.chess.model.SEP;
import java.util.LinkedList;
import de.cisha.chess.model.Game;
import java.util.List;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.GameHolder;

public class VideoGameHolder extends GameHolder implements MoveExecutor, VideoCommandDelegate
{
    private Move _currentVideoMove;
    private List<Game> _games;
    
    public VideoGameHolder() {
        super(new Game());
        this._games = new LinkedList<Game>();
    }
    
    @Override
    public boolean addVideoMove(final SEP sep, final int n) {
        if (sep != null) {
            if (sep.isNullMove()) {
                final Move doNullMoveInCurrentVideoPosition = this.doNullMoveInCurrentVideoPosition();
                (this._currentVideoMove = doNullMoveInCurrentVideoPosition).setMoveId(n);
                doNullMoveInCurrentVideoPosition.setUserGenerated(false);
                return true;
            }
            if (this._currentVideoMove == null) {
                super.gotoStartingPosition();
            }
            else {
                super.selectMove(this._currentVideoMove);
            }
            final Move doMoveInCurrentPosition = super.doMoveInCurrentPosition(sep, false);
            if (doMoveInCurrentPosition != null) {
                (this._currentVideoMove = doMoveInCurrentPosition).setUserGenerated(false);
                doMoveInCurrentPosition.setMoveId(n);
                return true;
            }
        }
        return false;
    }
    
    public Move doNullMoveInCurrentVideoPosition() {
        final NullMove nullMove = new NullMove(this.getPosition());
        MoveContainer moveContainer;
        if ((moveContainer = this.getCurrentMove()) == null) {
            moveContainer = this.getRootMoveContainer();
        }
        Move child;
        if (!moveContainer.hasChild(nullMove.getSEP())) {
            moveContainer.addMove(nullMove);
            child = nullMove;
        }
        else {
            child = moveContainer.getChild(nullMove.getSEP());
            child.setUserGenerated(false);
        }
        super.selectMove(this._currentVideoMove = child);
        return child;
    }
    
    @Override
    public boolean selectGame(final int n) {
        if (this._games != null && n < this._games.size()) {
            super.setNewGame(this._games.get(n));
            this._currentVideoMove = null;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean selectMove(final int n) {
        if (n == 0) {
            super.gotoStartingPosition();
            this._currentVideoMove = null;
            return true;
        }
        final Move moveById = this.getMoveById(n);
        if (moveById != null) {
            super.selectMove(moveById);
            this._currentVideoMove = moveById;
            return true;
        }
        return false;
    }
    
    public void setGames(final List<Game> games) {
        this._games = games;
    }
}
