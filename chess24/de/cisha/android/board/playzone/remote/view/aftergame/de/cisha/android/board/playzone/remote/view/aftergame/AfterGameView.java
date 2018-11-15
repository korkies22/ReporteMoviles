/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 */
package de.cisha.android.board.playzone.remote.view.aftergame;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.remote.view.aftergame.RematchGameDialog;
import de.cisha.android.board.playzone.view.AbstractAfterGameView;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import de.cisha.android.ui.patterns.dialogs.ConfirmCallback;
import de.cisha.chess.model.Opponent;
import java.util.List;

public class AfterGameView
extends AbstractAfterGameView {
    private BoardAction _analyzeAction;
    private BoardAction _cancelRematchAction;
    List<TimeControl> _clocks;
    private boolean _flagCancelRematchActionPerformed;
    private boolean _incomingRematchOffer;
    private BoardAction _newGameAction;
    private Opponent _opponent;
    private boolean _opponentDeclinedRematch;
    private BoardAction _rematchAction;
    private ModelHolder.ModelChangeListener<Boolean> _rematchDeclinedListener;
    private RematchGameDialog _rematchGameDialog;
    private boolean _rematchSent;
    private AbstractAfterGameView.AfterGameCategory _selectedCat;

    public AfterGameView(Context context, List<TimeControl> list, ModelHolder<AfterGameInformation> modelHolder, ModelHolder<Boolean> modelHolder2, BoardAction boardAction, BoardAction boardAction2, BoardAction boardAction3, @Nullable BoardAction boardAction4, Opponent opponent, FragmentManager fragmentManager, boolean bl) {
        super(context, modelHolder, fragmentManager, bl);
        this._clocks = list;
        this._rematchAction = boardAction;
        this._cancelRematchAction = boardAction2;
        this._newGameAction = boardAction4;
        this._opponent = opponent;
        this._analyzeAction = boardAction3;
        this._rematchDeclinedListener = new ModelHolder.ModelChangeListener<Boolean>(){

            @Override
            public void modelChanged(Boolean bl) {
                if (bl.booleanValue()) {
                    AfterGameView.this.opponentDeclinedRematch();
                }
            }
        };
        modelHolder2.addModelChangeListener(this._rematchDeclinedListener);
        if (modelHolder2.getModel().booleanValue()) {
            this.post(new Runnable(){

                @Override
                public void run() {
                    AfterGameView.this.deactivateRematch();
                }
            });
        }
    }

    private void opponentDeclinedRematch() {
        this._opponentDeclinedRematch = true;
        if (this._incomingRematchOffer && this._selectedCat == AbstractAfterGameView.AfterGameCategory.REMATCH) {
            this.selectCategory(null);
        }
        this.deactivateRematch();
    }

    private void performCancelRematchAction() {
        if (!this._flagCancelRematchActionPerformed) {
            this._cancelRematchAction.perform();
            this.deactivateRematch();
            this._flagCancelRematchActionPerformed = true;
        }
    }

    private void showRematchDialog() {
        this.closeInfoDialog();
        View view = this.findViewById(AbstractAfterGameView.AfterGameCategory.REMATCH.getResId());
        if (view != null) {
            view.setEnabled(true);
        }
        this.selectCategory(AbstractAfterGameView.AfterGameCategory.REMATCH);
    }

    @Override
    protected void deactivateRematch() {
        View view;
        super.deactivateRematch();
        if (this._rematchGameDialog != null && this._opponentDeclinedRematch) {
            this._rematchGameDialog.showRematchDeclinedView();
        }
        if ((view = this.findViewById(AbstractAfterGameView.AfterGameCategory.REMATCH.getResId())) != null) {
            view.setEnabled(false);
        }
    }

    @Override
    protected ArrowBottomContainerView getDialogFor(AbstractAfterGameView.AfterGameCategory afterGameCategory) {
        Context context = this.getContext();
        int n = .$SwitchMap$de$cisha$android$board$playzone$view$AbstractAfterGameView$AfterGameCategory[afterGameCategory.ordinal()];
        if (n != 1) {
            if (n != 3) {
                return new ArrowBottomContainerView(this.getContext());
            }
            this._rematchGameDialog = new RematchGameDialog(context, new ConfirmCallback(){

                @Override
                public void canceled() {
                    AfterGameView.this._rematchSent = false;
                    AfterGameView.this.selectCategory(null);
                    AfterGameView.this.performCancelRematchAction();
                }

                @Override
                public void confirmed() {
                    AfterGameView.this._rematchAction.perform();
                    AfterGameView.this._rematchSent = true;
                }
            }, true ^ this._incomingRematchOffer, this._opponent.getName());
            if (this._opponentDeclinedRematch) {
                this._rematchGameDialog.showRematchDeclinedView();
            }
            return this._rematchGameDialog;
        }
        return new ArrowBottomContainerView(this.getContext());
    }

    @Override
    public void rematchButtonClicked() {
        this.showRematchDialog();
        this._rematchGameDialog.showLoadingView();
        this._rematchAction.perform();
        this._rematchSent = true;
    }

    @Override
    protected void selectCategory(AbstractAfterGameView.AfterGameCategory afterGameCategory) {
        this._selectedCat = afterGameCategory;
        if (this._rematchSent && afterGameCategory != AbstractAfterGameView.AfterGameCategory.REMATCH) {
            this.performCancelRematchAction();
        }
        super.selectCategory(afterGameCategory);
        if (afterGameCategory != null) {
            switch (.$SwitchMap$de$cisha$android$board$playzone$view$AbstractAfterGameView$AfterGameCategory[afterGameCategory.ordinal()]) {
                default: {
                    return;
                }
                case 2: {
                    if (this._newGameAction == null) break;
                    this._newGameAction.perform();
                    return;
                }
                case 1: {
                    this._analyzeAction.perform();
                }
            }
        }
    }

    public void showOpponentsRematchOffer() {
        if (!this._rematchSent) {
            this._incomingRematchOffer = true;
            this.showRematchDialog();
        }
    }

    @Override
    public boolean waitForResult() {
        return true;
    }

}
