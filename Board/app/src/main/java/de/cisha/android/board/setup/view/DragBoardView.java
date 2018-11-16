// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.setup.view;

import de.cisha.chess.model.Piece;
import android.widget.ImageView;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.view.View;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.board.view.BoardView;

public class DragBoardView extends BoardView
{
    private boolean _drag;
    private DragDelegate _dragDelegate;
    private float _startX;
    private float _startY;
    
    DragBoardView(final Context context) {
        super(context);
    }
    
    DragBoardView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    DragBoardView(final Context context, final Position position) {
        super(context, position);
    }
    
    public Square getSquareForRawCoordinates(final float n, final float n2) {
        int top = this.getTop();
        int left = this.getLeft();
        for (ViewParent viewParent = this.getParent(); viewParent != null && viewParent instanceof View; viewParent = viewParent.getParent()) {
            final View view = (View)viewParent;
            top += view.getTop();
            left += view.getLeft();
        }
        return super.getSquareForCoordinates(n - left, n2 - top);
    }
    
    @Override
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        final int action = motionEvent.getAction();
        boolean drag = false;
        if (action == 0) {
            this._startX = motionEvent.getX();
            this._startY = motionEvent.getY();
            this._drag = false;
            if (this._dragDelegate != null) {
                final Square squareForCoordinates = this.getSquareForCoordinates((int)motionEvent.getX(), (int)motionEvent.getY());
                final ImageView imageViewForSquare = this.findImageViewForSquare(squareForCoordinates);
                if (imageViewForSquare != null) {
                    this._dragDelegate.onPieceDown(this.getPosition().getPieceFor(squareForCoordinates), squareForCoordinates, (View)imageViewForSquare);
                    return true;
                }
            }
        }
        else if (motionEvent.getAction() == 2) {
            if (!this._drag) {
                if (Math.abs(this._startX - motionEvent.getX()) + Math.abs(this._startY - motionEvent.getY()) > 20.0f) {
                    drag = true;
                }
                this._drag = drag;
                if (this._drag) {
                    this._dragDelegate.onDragStart();
                    return true;
                }
            }
        }
        else if (motionEvent.getAction() == 1 && this._dragDelegate != null) {
            this._dragDelegate.onSquareUp(this.getSquareForCoordinates((int)motionEvent.getX(), (int)motionEvent.getY()), this._drag);
        }
        return true;
    }
    
    public void setDragDelegate(final DragDelegate dragDelegate) {
        this._dragDelegate = dragDelegate;
    }
    
    public interface DragDelegate
    {
        void onDragStart();
        
        void onPieceDown(final Piece p0, final Square p1, final View p2);
        
        void onSquareUp(final Square p0, final boolean p1);
    }
}
