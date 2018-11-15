/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.account.StoreFragment;
import de.cisha.android.board.action.BoardAction;

class EngineOfflineOpponentChooserViewFragment
implements BoardAction {
    EngineOfflineOpponentChooserViewFragment() {
    }

    @Override
    public void perform() {
        if (EngineOfflineOpponentChooserViewFragment.this._contentPresenter != null) {
            EngineOfflineOpponentChooserViewFragment.this._contentPresenter.showFragment(new StoreFragment(), true, true);
        }
    }
}
