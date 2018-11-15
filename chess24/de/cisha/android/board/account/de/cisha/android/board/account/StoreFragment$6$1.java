/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.account;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.account.StoreFragment;

class StoreFragment
implements IErrorPresenter.IReloadAction {
    StoreFragment() {
    }

    @Override
    public void performReload() {
        6.this.this$0.restoreItems();
    }
}
