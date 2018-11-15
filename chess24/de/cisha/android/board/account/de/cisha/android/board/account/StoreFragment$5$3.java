/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.account;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.account.StoreFragment;
import de.cisha.android.board.account.model.Product;

class StoreFragment
implements IErrorPresenter.IReloadAction {
    StoreFragment() {
    }

    @Override
    public void performReload() {
        5.this.this$0.purchaseProduct(5.this.val$product);
    }
}
