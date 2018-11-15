/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.HorizontalScrollView
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import de.cisha.android.board.view.MoveListHorizontalLayout;
import de.cisha.android.board.view.MoveListView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveSelector;

public class MoveListHorizontalScrollView
extends HorizontalScrollView
implements MoveListView {
    private MoveListHorizontalLayout _moveList;

    public MoveListHorizontalScrollView(Context context) {
        super(context);
        this.inflateLayout(context);
    }

    public MoveListHorizontalScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.inflateLayout(context);
    }

    public MoveListHorizontalScrollView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.inflateLayout(context);
    }

    private void inflateLayout(Context context) {
        this.setScrolledMoveListView(new MoveListHorizontalLayout(context));
        this.setSmoothScrollingEnabled(true);
    }

    private void setScrolledMoveListView(MoveListHorizontalLayout moveListHorizontalLayout) {
        this._moveList = moveListHorizontalLayout;
        this.removeAllViews();
        this.addView((View)moveListHorizontalLayout);
        this.invalidate();
    }

    @Override
    public void allMovesChanged(MoveContainer moveContainer) {
        this._moveList.allMovesChanged(moveContainer);
        this.post(new Runnable(){

            @Override
            public void run() {
                MoveListHorizontalScrollView.this.fullScroll(66);
            }
        });
    }

    @Override
    public boolean canSkipMoves() {
        return true;
    }

    @Override
    public void newMove(Move move) {
        this._moveList.newMove(move);
        this.postDelayed(new Runnable(){

            @Override
            public void run() {
                MoveListHorizontalScrollView.this.fullScroll(66);
            }
        }, 200L);
    }

    @Override
    public void selectedMoveChanged(Move move) {
        this._moveList.selectedMoveChanged(move);
    }

    @Override
    public void setMoveSelector(MoveSelector moveSelector) {
        this._moveList.setMoveSelector(moveSelector);
    }

    @Override
    public void setNavigationSelectionEnabled(boolean bl) {
        this._moveList.setNavigationSelectionEnabled(bl);
    }

}
