/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 */
package de.cisha.android.board.view.notation;

import android.content.Context;
import android.view.View;
import de.cisha.android.board.view.notation.Line;
import de.cisha.android.board.view.notation.MoveView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import java.util.List;
import java.util.Map;

class NotationListView
implements Runnable {
    final /* synthetic */ MoveContainer val$newRootMoveContainer;

    NotationListView(MoveContainer moveContainer) {
        this.val$newRootMoveContainer = moveContainer;
    }

    @Override
    public void run() {
        NotationListView.this._invalidateNotationList = false;
        NotationListView.this._selectedMoveView = null;
        NotationListView.this._mapMoveToView.clear();
        NotationListView.this._moveToMarkColor.clear();
        NotationListView.this.removeAllViews();
        List<Move> list = this.val$newRootMoveContainer.getAllMainLineMoves();
        Line line = new Line(NotationListView.this.getContext(), 0, NotationListView.this._maxWidth);
        line.setLineNumber(0);
        NotationListView.this.addView((View)line);
        NotationListView.this.addMovesToLine(list, line);
    }
}
