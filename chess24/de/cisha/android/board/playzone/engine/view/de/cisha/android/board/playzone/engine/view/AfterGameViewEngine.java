/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.playzone.engine.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import de.cisha.android.board.playzone.view.AbstractAfterGameView;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import de.cisha.android.ui.patterns.dialogs.ConfirmCallback;

public class AfterGameViewEngine
extends AbstractAfterGameView {
    private ConfirmCallback _analyzeCallback;
    private ConfirmCallback _newGameCallback;
    private ConfirmCallback _rematchCallback;

    public AfterGameViewEngine(Context context, ModelHolder<AfterGameInformation> modelHolder, ConfirmCallback confirmCallback, ConfirmCallback confirmCallback2, ConfirmCallback confirmCallback3, FragmentManager fragmentManager, boolean bl) {
        super(context, modelHolder, fragmentManager, bl);
        this._rematchCallback = confirmCallback;
        this._analyzeCallback = confirmCallback2;
        this._newGameCallback = confirmCallback3;
    }

    @Override
    protected ArrowBottomContainerView getDialogFor(AbstractAfterGameView.AfterGameCategory afterGameCategory) {
        return null;
    }

    @Override
    public void rematchButtonClicked() {
        this._rematchCallback.confirmed();
    }

    @Override
    protected void selectCategory(AbstractAfterGameView.AfterGameCategory afterGameCategory) {
        super.selectCategory(afterGameCategory);
        switch (.$SwitchMap$de$cisha$android$board$playzone$view$AbstractAfterGameView$AfterGameCategory[afterGameCategory.ordinal()]) {
            default: {
                return;
            }
            case 3: {
                this._newGameCallback.confirmed();
                return;
            }
            case 2: {
                this._rematchCallback.confirmed();
                return;
            }
            case 1: 
        }
        this._analyzeCallback.confirmed();
    }

    @Override
    public boolean waitForResult() {
        return false;
    }

}
