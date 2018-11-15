/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.account.view;

import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.account.view.PurchaseDialog;

public static interface PurchaseDialog.IPurchaseDialogListener {
    public void onRestorePressed();

    public void onSelectProduct(Product var1);
}
