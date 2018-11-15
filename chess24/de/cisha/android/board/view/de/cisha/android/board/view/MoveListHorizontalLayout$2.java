/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.TextView
 */
package de.cisha.android.board.view;

import android.view.View;
import android.widget.TextView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import java.util.List;

class MoveListHorizontalLayout
implements Runnable {
    final /* synthetic */ MoveContainer val$newRootMoveContainer;

    MoveListHorizontalLayout(MoveContainer moveContainer) {
        this.val$newRootMoveContainer = moveContainer;
    }

    @Override
    public void run() {
        int n;
        _moveNo = 0;
        List<Move> list = this.val$newRootMoveContainer.getAllMainLineMoves();
        for (n = 0; n < Math.min(MoveListHorizontalLayout.this.getChildCount(), list.size()); ++n) {
            TextView textView = (TextView)MoveListHorizontalLayout.this.getChildAt(n);
            MoveListHorizontalLayout.this.addViewWithSAN(list.get(n), false, textView);
        }
        for (n = MoveListHorizontalLayout.this.getChildCount() - 1; n >= list.size(); --n) {
            MoveListHorizontalLayout.this.removeView(MoveListHorizontalLayout.this.getChildAt(n));
        }
        for (n = MoveListHorizontalLayout.this.getChildCount(); n < list.size(); ++n) {
            MoveListHorizontalLayout.this.addViewWithSAN(list.get(n), false);
        }
        MoveListHorizontalLayout.this.invalidate();
    }
}
