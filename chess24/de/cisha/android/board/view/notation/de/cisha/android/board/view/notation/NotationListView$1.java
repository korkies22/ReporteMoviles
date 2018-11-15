/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.view.notation;

import android.view.View;
import de.cisha.android.board.view.notation.Line;
import de.cisha.android.board.view.notation.MoveView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveSelector;

class NotationListView
implements View.OnClickListener {
    NotationListView() {
    }

    public void onClick(View object) {
        object = (MoveView)((Object)object);
        if (NotationListView.this._moveSelectionEnabled && NotationListView.this._moveSelector != null) {
            NotationListView.this.setSelectedMoveView((MoveView)((Object)object), object.getMove());
            Move move = object.getMove();
            if (object.showsVariation()) {
                NotationListView.this.removeChildLinesFor((MoveView)((Object)object));
                object.getLine().updateChildrenLineNumbers();
            } else if (move.hasSiblings() && !move.isVariationStart()) {
                NotationListView.this.removeChildLinesFor((MoveView)((Object)object));
                NotationListView.this.addVariationsFor((MoveView)((Object)object));
            }
            NotationListView.this._moveSelector.selectMove(move);
        }
    }
}
