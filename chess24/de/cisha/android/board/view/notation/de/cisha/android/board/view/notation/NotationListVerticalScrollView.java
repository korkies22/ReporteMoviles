/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.ScrollView
 */
package de.cisha.android.board.view.notation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import de.cisha.android.board.view.MoveListView;
import de.cisha.android.board.view.notation.NotationListView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveSelector;

public class NotationListVerticalScrollView
extends ScrollView
implements MoveListView {
    private MoveListView _moveListView;

    public NotationListVerticalScrollView(Context context) {
        super(context);
        this.inflateLayout();
    }

    public NotationListVerticalScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.inflateLayout();
    }

    public NotationListVerticalScrollView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.inflateLayout();
    }

    private void inflateLayout() {
        NotationListView notationListView = new NotationListView(this.getContext());
        this.setSmoothScrollingEnabled(true);
        this.addView((View)notationListView, -1, -1);
        this._moveListView = notationListView;
    }

    @Override
    public void allMovesChanged(MoveContainer moveContainer) {
        this._moveListView.allMovesChanged(moveContainer);
        this.post(new Runnable(){

            @Override
            public void run() {
                NotationListVerticalScrollView.this.fullScroll(130);
            }
        });
    }

    @Override
    public boolean canSkipMoves() {
        return true;
    }

    @Override
    public void newMove(Move move) {
        this._moveListView.newMove(move);
        this.postDelayed(new Runnable(){

            @Override
            public void run() {
                NotationListVerticalScrollView.this.fullScroll(130);
            }
        }, 200L);
    }

    @Override
    public void selectedMoveChanged(Move move) {
        this._moveListView.selectedMoveChanged(move);
    }

    @Override
    public void setMoveSelector(MoveSelector moveSelector) {
        this._moveListView.setMoveSelector(moveSelector);
    }

    @Override
    public void setNavigationSelectionEnabled(boolean bl) {
        this._moveListView.setNavigationSelectionEnabled(bl);
    }

}
