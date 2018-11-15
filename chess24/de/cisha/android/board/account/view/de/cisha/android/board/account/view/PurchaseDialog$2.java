/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.account.view;

import android.view.View;
import de.cisha.android.board.account.model.Product;

class PurchaseDialog
implements View.OnClickListener {
    final /* synthetic */ Product val$product;

    PurchaseDialog(Product product) {
        this.val$product = product;
    }

    public void onClick(View view) {
        PurchaseDialog.this.purchaseProduct(this.val$product);
    }
}
