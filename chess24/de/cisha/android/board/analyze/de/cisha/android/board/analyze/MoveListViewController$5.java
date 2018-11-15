/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.widget.ScrollView
 */
package de.cisha.android.board.analyze;

import android.os.Build;
import android.widget.ScrollView;
import de.cisha.android.board.view.notation.NotationListView;
import de.cisha.chess.model.Move;

class MoveListViewController
implements Runnable {
    final /* synthetic */ Move val$move;

    MoveListViewController(Move move) {
        this.val$move = move;
    }

    @Override
    public void run() {
        int n = MoveListViewController.this._notationList.getTopPositionForMove(this.val$move);
        if (Build.VERSION.SDK_INT > 10) {
            MoveListViewController.this._scrollView.smoothScrollTo(0, n);
            return;
        }
        MoveListViewController.this._scrollView.scrollTo(0, n);
    }
}
