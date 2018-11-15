/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.analyze.view;

import android.view.View;
import android.widget.LinearLayout;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveSelector;

class VariationSelectionView
implements View.OnClickListener {
    final /* synthetic */ Move val$move;

    VariationSelectionView(Move move) {
        this.val$move = move;
    }

    public void onClick(View view) {
        for (int i = 0; i < VariationSelectionView.this._mainView.getChildCount(); ++i) {
            VariationSelectionView.this._mainView.getChildAt(i).setSelected(false);
        }
        view.setSelected(true);
        view.postDelayed(new Runnable(){

            @Override
            public void run() {
                if (VariationSelectionView.this._moveSelector != null) {
                    VariationSelectionView.this._moveSelector.selectMove(1.this.val$move);
                }
            }
        }, 100L);
    }

}
