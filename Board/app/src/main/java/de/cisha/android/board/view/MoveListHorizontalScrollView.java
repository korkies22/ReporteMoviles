// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import android.view.View;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.HorizontalScrollView;

public class MoveListHorizontalScrollView extends HorizontalScrollView implements MoveListView
{
    private MoveListHorizontalLayout _moveList;
    
    public MoveListHorizontalScrollView(final Context context) {
        super(context);
        this.inflateLayout(context);
    }
    
    public MoveListHorizontalScrollView(final Context context, final AttributeSet set) {
        super(context, set);
        this.inflateLayout(context);
    }
    
    public MoveListHorizontalScrollView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.inflateLayout(context);
    }
    
    private void inflateLayout(final Context context) {
        this.setScrolledMoveListView(new MoveListHorizontalLayout(context));
        this.setSmoothScrollingEnabled(true);
    }
    
    private void setScrolledMoveListView(final MoveListHorizontalLayout moveList) {
        this._moveList = moveList;
        this.removeAllViews();
        this.addView((View)moveList);
        this.invalidate();
    }
    
    public void allMovesChanged(final MoveContainer moveContainer) {
        this._moveList.allMovesChanged(moveContainer);
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                MoveListHorizontalScrollView.this.fullScroll(66);
            }
        });
    }
    
    public boolean canSkipMoves() {
        return true;
    }
    
    public void newMove(final Move move) {
        this._moveList.newMove(move);
        this.postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                MoveListHorizontalScrollView.this.fullScroll(66);
            }
        }, 200L);
    }
    
    public void selectedMoveChanged(final Move move) {
        this._moveList.selectedMoveChanged(move);
    }
    
    public void setMoveSelector(final MoveSelector moveSelector) {
        this._moveList.setMoveSelector(moveSelector);
    }
    
    public void setNavigationSelectionEnabled(final boolean navigationSelectionEnabled) {
        this._moveList.setNavigationSelectionEnabled(navigationSelectionEnabled);
    }
}
