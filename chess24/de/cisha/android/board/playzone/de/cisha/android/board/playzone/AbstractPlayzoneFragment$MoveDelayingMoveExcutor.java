/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package de.cisha.android.board.playzone;

import android.os.Bundle;
import de.cisha.android.board.StateHolder;
import de.cisha.android.board.playzone.AbstractPlayzoneFragment;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.android.board.playzone.view.MoveConfirmationView;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;
import java.io.Serializable;

private class AbstractPlayzoneFragment.MoveDelayingMoveExcutor
implements MoveExecutor,
MoveConfirmationView.MoveConfirmationViewDelegate,
StateHolder {
    private static final String DELAYED_SEP_TO_CONFIRM = "DELAYED_SEP_TO_CONFIRM";
    private SEP _currentlyDelayedSEP;

    private AbstractPlayzoneFragment.MoveDelayingMoveExcutor() {
    }

    private MoveExecutor getMoveExecutor() {
        return AbstractPlayzoneFragment.this.getGameAdapter().getMoveExecutor();
    }

    private boolean hasDelayedMove() {
        if (this._currentlyDelayedSEP != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean advanceOneMoveInCurrentLine() {
        if (this.getMoveExecutor() != null) {
            return this.getMoveExecutor().advanceOneMoveInCurrentLine();
        }
        return false;
    }

    @Override
    public void cancelMove(Move move) {
        AbstractPlayzoneFragment.this.hideMoveConfirmationView();
        if (AbstractPlayzoneFragment.this._boardView != null && AbstractPlayzoneFragment.this.getGameAdapter() != null) {
            AbstractPlayzoneFragment.this._boardView.setPosition(AbstractPlayzoneFragment.this.getGameAdapter().getPosition());
            this._currentlyDelayedSEP = null;
        }
    }

    @Override
    public void confirmMove(Move move) {
        AbstractPlayzoneFragment.this.hideMoveConfirmationView();
        if (move != null && this.getMoveExecutor() != null) {
            this.getMoveExecutor().doMoveInCurrentPosition(move.getSEP());
            this._currentlyDelayedSEP = null;
        }
    }

    @Override
    public Move doMoveInCurrentPosition(SEP serializable) {
        if (ServiceProvider.getInstance().getSettingsService().confirmMove()) {
            Position position;
            this._currentlyDelayedSEP = serializable;
            if (AbstractPlayzoneFragment.this.getGameAdapter() != null && (position = AbstractPlayzoneFragment.this.getGameAdapter().getPosition()) != null) {
                if ((serializable = position.createMoveFrom((SEP)serializable)) != null) {
                    if (AbstractPlayzoneFragment.this._boardView != null) {
                        AbstractPlayzoneFragment.this._boardView.positionChanged(serializable.getResultingPosition(), (Move)serializable);
                    }
                    AbstractPlayzoneFragment.this.showMoveConfirmationView((Move)serializable);
                }
                return serializable;
            }
        } else if (this.getMoveExecutor() != null) {
            return this.getMoveExecutor().doMoveInCurrentPosition((SEP)serializable);
        }
        return null;
    }

    @Override
    public boolean goBackOneMove() {
        if (this.getMoveExecutor() != null) {
            return this.getMoveExecutor().goBackOneMove();
        }
        return false;
    }

    @Override
    public void gotoEndingPosition() {
        if (this.getMoveExecutor() != null) {
            this.getMoveExecutor().gotoEndingPosition();
        }
    }

    @Override
    public boolean gotoStartingPosition() {
        if (this.getMoveExecutor() != null) {
            return this.getMoveExecutor().gotoStartingPosition();
        }
        return false;
    }

    @Override
    public void registerPremove(Square square, Square square2) {
        if (this.getMoveExecutor() != null) {
            this.getMoveExecutor().registerPremove(square, square2);
        }
    }

    public void restoreState() {
        if (this.hasDelayedMove()) {
            this.doMoveInCurrentPosition(this._currentlyDelayedSEP);
        }
    }

    @Override
    public void restoreState(Bundle bundle) {
        if (bundle != null && bundle.get(DELAYED_SEP_TO_CONFIRM) != null) {
            this.doMoveInCurrentPosition(new SEP(bundle.getShort(DELAYED_SEP_TO_CONFIRM)));
        }
    }

    @Override
    public void saveState(Bundle bundle) {
        if (this._currentlyDelayedSEP != null) {
            bundle.putShort(DELAYED_SEP_TO_CONFIRM, this._currentlyDelayedSEP.getSEPNumber());
            return;
        }
        bundle.remove(DELAYED_SEP_TO_CONFIRM);
    }

    @Override
    public boolean selectMove(Move move) {
        if (this.getMoveExecutor() != null) {
            return this.getMoveExecutor().selectMove(move);
        }
        return false;
    }
}
