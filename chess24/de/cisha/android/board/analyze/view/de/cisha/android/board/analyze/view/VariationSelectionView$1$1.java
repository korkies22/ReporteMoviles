/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze.view;

import de.cisha.android.board.analyze.view.VariationSelectionView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveSelector;

class VariationSelectionView
implements Runnable {
    VariationSelectionView() {
    }

    @Override
    public void run() {
        if (1.this.this$0._moveSelector != null) {
            1.this.this$0._moveSelector.selectMove(1.this.val$move);
        }
    }
}
