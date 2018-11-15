/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.model;

import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.NullMove;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.position.Position;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class VideoGameHolder
extends GameHolder
implements MoveExecutor,
VideoCommandDelegate {
    private Move _currentVideoMove;
    private List<Game> _games = new LinkedList<Game>();

    public VideoGameHolder() {
        super(new Game());
    }

    @Override
    public boolean addVideoMove(SEP serializable, int n) {
        if (serializable != null) {
            if (!serializable.isNullMove()) {
                if (this._currentVideoMove == null) {
                    super.gotoStartingPosition();
                } else {
                    super.selectMove(this._currentVideoMove);
                }
                serializable = super.doMoveInCurrentPosition((SEP)serializable, false);
                if (serializable != null) {
                    this._currentVideoMove = serializable;
                    serializable.setUserGenerated(false);
                    serializable.setMoveId(n);
                    return true;
                }
            } else {
                serializable = this.doNullMoveInCurrentVideoPosition();
                this._currentVideoMove = serializable;
                serializable.setMoveId(n);
                serializable.setUserGenerated(false);
                return true;
            }
        }
        return false;
    }

    public Move doNullMoveInCurrentVideoPosition() {
        NullMove nullMove = new NullMove(this.getPosition());
        Move move = this.getCurrentMove();
        MoveContainer moveContainer = move;
        if (move == null) {
            moveContainer = this.getRootMoveContainer();
        }
        if (!moveContainer.hasChild(nullMove.getSEP())) {
            moveContainer.addMove(nullMove);
            moveContainer = nullMove;
        } else {
            moveContainer = moveContainer.getChild(nullMove.getSEP());
            moveContainer.setUserGenerated(false);
        }
        this._currentVideoMove = moveContainer;
        super.selectMove((Move)moveContainer);
        return moveContainer;
    }

    @Override
    public boolean selectGame(int n) {
        if (this._games != null && n < this._games.size()) {
            super.setNewGame(this._games.get(n));
            this._currentVideoMove = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean selectMove(int n) {
        if (n == 0) {
            super.gotoStartingPosition();
            this._currentVideoMove = null;
            return true;
        }
        Move move = this.getMoveById(n);
        if (move != null) {
            super.selectMove(move);
            this._currentVideoMove = move;
            return true;
        }
        return false;
    }

    public void setGames(List<Game> list) {
        this._games = list;
    }
}
