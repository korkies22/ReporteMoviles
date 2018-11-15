/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.tactics.TacticsStopFragment;

class TacticsStopFragment
implements IErrorPresenter.IReloadAction {
    TacticsStopFragment() {
    }

    @Override
    public void performReload() {
        2.this.this$1.this$0.loadSession();
    }
}
