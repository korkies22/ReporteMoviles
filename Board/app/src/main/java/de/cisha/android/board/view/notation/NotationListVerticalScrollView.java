// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view.notation;

import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import android.view.View;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.board.view.MoveListView;
import android.widget.ScrollView;

public class NotationListVerticalScrollView extends ScrollView implements MoveListView
{
    private MoveListView _moveListView;
    
    public NotationListVerticalScrollView(final Context context) {
        super(context);
        this.inflateLayout();
    }
    
    public NotationListVerticalScrollView(final Context context, final AttributeSet set) {
        super(context, set);
        this.inflateLayout();
    }
    
    public NotationListVerticalScrollView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.inflateLayout();
    }
    
    private void inflateLayout() {
        final NotationListView moveListView = new NotationListView(this.getContext());
        this.setSmoothScrollingEnabled(true);
        this.addView((View)moveListView, -1, -1);
        this._moveListView = moveListView;
    }
    
    public void allMovesChanged(final MoveContainer moveContainer) {
        this._moveListView.allMovesChanged(moveContainer);
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                NotationListVerticalScrollView.this.fullScroll(130);
            }
        });
    }
    
    public boolean canSkipMoves() {
        return true;
    }
    
    public void newMove(final Move move) {
        this._moveListView.newMove(move);
        this.postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                NotationListVerticalScrollView.this.fullScroll(130);
            }
        }, 200L);
    }
    
    public void selectedMoveChanged(final Move move) {
        this._moveListView.selectedMoveChanged(move);
    }
    
    public void setMoveSelector(final MoveSelector moveSelector) {
        this._moveListView.setMoveSelector(moveSelector);
    }
    
    public void setNavigationSelectionEnabled(final boolean navigationSelectionEnabled) {
        this._moveListView.setNavigationSelectionEnabled(navigationSelectionEnabled);
    }
}
