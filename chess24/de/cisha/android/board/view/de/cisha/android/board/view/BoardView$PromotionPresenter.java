/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.view;

import android.view.View;
import de.cisha.android.board.view.BoardView;

public static interface BoardView.PromotionPresenter {
    public void dismissDialog();

    public boolean isPromotionShown();

    public void showPromotionDialog(View var1);
}
