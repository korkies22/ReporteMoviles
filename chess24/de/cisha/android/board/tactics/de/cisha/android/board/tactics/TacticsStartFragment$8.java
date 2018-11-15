/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.IErrorPresenter;

class TacticsStartFragment
implements IErrorPresenter.IReloadAction {
    TacticsStartFragment() {
    }

    @Override
    public void performReload() {
        TacticsStartFragment.this.loadData();
    }
}
