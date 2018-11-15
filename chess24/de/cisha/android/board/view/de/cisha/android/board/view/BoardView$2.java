/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package de.cisha.android.board.view;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.view.ScaleableGridLayout;

class BoardView
implements BoardView.PromotionPresenter {
    private View _promotionView;

    BoardView() {
    }

    @Override
    public void dismissDialog() {
        BoardView.this.removeView(this._promotionView);
        BoardView.this.resetActivePiece();
    }

    @Override
    public boolean isPromotionShown() {
        if (this._promotionView != null && this._promotionView.getParent() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void showPromotionDialog(View view) {
        BoardView.this.requestFocus();
        this._promotionView = view;
        this._promotionView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
            }
        });
        ScaleableGridLayout.LayoutParams layoutParams = new ScaleableGridLayout.LayoutParams(0, 0, 8, 8);
        BoardView.this.addView(view, (ViewGroup.LayoutParams)layoutParams);
    }

}
