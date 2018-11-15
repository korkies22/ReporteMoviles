/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.view.aftergame;

import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.view.AbstractAfterGameView;
import de.cisha.android.ui.patterns.dialogs.ConfirmCallback;

class AfterGameView
implements ConfirmCallback {
    AfterGameView() {
    }

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
}
