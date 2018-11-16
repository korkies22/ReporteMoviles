// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.view.aftergame;

import de.cisha.android.ui.patterns.dialogs.ConfirmCallback;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import android.view.View;
import android.support.v4.app.FragmentManager;
import android.support.annotation.Nullable;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import android.content.Context;
import de.cisha.android.board.ModelHolder;
import de.cisha.chess.model.Opponent;
import de.cisha.android.board.playzone.model.TimeControl;
import java.util.List;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.view.AbstractAfterGameView;

public class AfterGameView extends AbstractAfterGameView
{
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
    private AfterGameCategory _selectedCat;
    
    public AfterGameView(final Context context, final List<TimeControl> clocks, final ModelHolder<AfterGameInformation> modelHolder, final ModelHolder<Boolean> modelHolder2, final BoardAction rematchAction, final BoardAction cancelRematchAction, final BoardAction analyzeAction, @Nullable final BoardAction newGameAction, final Opponent opponent, final FragmentManager fragmentManager, final boolean b) {
        super(context, modelHolder, fragmentManager, b);
        this._clocks = clocks;
        this._rematchAction = rematchAction;
        this._cancelRematchAction = cancelRematchAction;
        this._newGameAction = newGameAction;
        this._opponent = opponent;
        this._analyzeAction = analyzeAction;
        modelHolder2.addModelChangeListener(this._rematchDeclinedListener = new ModelHolder.ModelChangeListener<Boolean>() {
            public void modelChanged(final Boolean b) {
                if (b) {
                    AfterGameView.this.opponentDeclinedRematch();
                }
            }
        });
        if (modelHolder2.getModel()) {
            this.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    AfterGameView.this.deactivateRematch();
                }
            });
        }
    }
    
    private void opponentDeclinedRematch() {
        this._opponentDeclinedRematch = true;
        if (this._incomingRematchOffer && this._selectedCat == AfterGameCategory.REMATCH) {
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
        final View viewById = this.findViewById(AfterGameCategory.REMATCH.getResId());
        if (viewById != null) {
            viewById.setEnabled(true);
        }
        this.selectCategory(AfterGameCategory.REMATCH);
    }
    
    @Override
    protected void deactivateRematch() {
        super.deactivateRematch();
        if (this._rematchGameDialog != null && this._opponentDeclinedRematch) {
            this._rematchGameDialog.showRematchDeclinedView();
        }
        final View viewById = this.findViewById(AfterGameCategory.REMATCH.getResId());
        if (viewById != null) {
            viewById.setEnabled(false);
        }
    }
    
    @Override
    protected ArrowBottomContainerView getDialogFor(final AfterGameCategory afterGameCategory) {
        final Context context = this.getContext();
        final int n = AfterGameView.4..SwitchMap.de.cisha.android.board.playzone.view.AbstractAfterGameView.AfterGameCategory[afterGameCategory.ordinal()];
        if (n == 1) {
            return new ArrowBottomContainerView(this.getContext());
        }
        if (n != 3) {
            return new ArrowBottomContainerView(this.getContext());
        }
        this._rematchGameDialog = new RematchGameDialog(context, new ConfirmCallback() {
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
    
    @Override
    public void rematchButtonClicked() {
        this.showRematchDialog();
        this._rematchGameDialog.showLoadingView();
        this._rematchAction.perform();
        this._rematchSent = true;
    }
    
    @Override
    protected void selectCategory(final AfterGameCategory selectedCat) {
        this._selectedCat = selectedCat;
        if (this._rematchSent && selectedCat != AfterGameCategory.REMATCH) {
            this.performCancelRematchAction();
        }
        super.selectCategory(selectedCat);
        if (selectedCat != null) {
            switch (AfterGameView.4..SwitchMap.de.cisha.android.board.playzone.view.AbstractAfterGameView.AfterGameCategory[selectedCat.ordinal()]) {
                default: {}
                case 2: {
                    if (this._newGameAction != null) {
                        this._newGameAction.perform();
                        return;
                    }
                    break;
                }
                case 1: {
                    this._analyzeAction.perform();
                    break;
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
