// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine.view;

import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import android.support.v4.app.FragmentManager;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import de.cisha.android.board.ModelHolder;
import android.content.Context;
import de.cisha.android.ui.patterns.dialogs.ConfirmCallback;
import de.cisha.android.board.playzone.view.AbstractAfterGameView;

public class AfterGameViewEngine extends AbstractAfterGameView
{
    private ConfirmCallback _analyzeCallback;
    private ConfirmCallback _newGameCallback;
    private ConfirmCallback _rematchCallback;
    
    public AfterGameViewEngine(final Context context, final ModelHolder<AfterGameInformation> modelHolder, final ConfirmCallback rematchCallback, final ConfirmCallback analyzeCallback, final ConfirmCallback newGameCallback, final FragmentManager fragmentManager, final boolean b) {
        super(context, modelHolder, fragmentManager, b);
        this._rematchCallback = rematchCallback;
        this._analyzeCallback = analyzeCallback;
        this._newGameCallback = newGameCallback;
    }
    
    @Override
    protected ArrowBottomContainerView getDialogFor(final AfterGameCategory afterGameCategory) {
        return null;
    }
    
    @Override
    public void rematchButtonClicked() {
        this._rematchCallback.confirmed();
    }
    
    @Override
    protected void selectCategory(final AfterGameCategory afterGameCategory) {
        super.selectCategory(afterGameCategory);
        switch (AfterGameViewEngine.1..SwitchMap.de.cisha.android.board.playzone.view.AbstractAfterGameView.AfterGameCategory[afterGameCategory.ordinal()]) {
            default: {}
            case 3: {
                this._newGameCallback.confirmed();
            }
            case 2: {
                this._rematchCallback.confirmed();
            }
            case 1: {
                this._analyzeCallback.confirmed();
            }
        }
    }
    
    @Override
    public boolean waitForResult() {
        return false;
    }
}
