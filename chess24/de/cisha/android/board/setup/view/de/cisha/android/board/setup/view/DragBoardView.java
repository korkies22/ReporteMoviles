/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewParent
 *  android.widget.ImageView
 */
package de.cisha.android.board.setup.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;

public class DragBoardView
extends BoardView {
    private boolean _drag;
    private DragDelegate _dragDelegate;
    private float _startX;
    private float _startY;

    DragBoardView(Context context) {
        super(context);
    }

    DragBoardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    DragBoardView(Context context, Position position) {
        super(context, position);
    }

    public Square getSquareForRawCoordinates(float f, float f2) {
        int n = this.getTop();
        int n2 = this.getLeft();
        for (ViewParent viewParent = this.getParent(); viewParent != null && viewParent instanceof View; viewParent = viewParent.getParent()) {
            View view = (View)viewParent;
            n += view.getTop();
            n2 += view.getLeft();
        }
        return super.getSquareForCoordinates(f - (float)n2, f2 - (float)n);
    }

    @Override
    public boolean onTouchEvent(MotionEvent object) {
        int n = object.getAction();
        boolean bl = false;
        if (n == 0) {
            ImageView imageView;
            this._startX = object.getX();
            this._startY = object.getY();
            this._drag = false;
            if (this._dragDelegate != null && (imageView = this.findImageViewForSquare((Square)(object = this.getSquareForCoordinates((int)object.getX(), (int)object.getY())))) != null) {
                this._dragDelegate.onPieceDown(this.getPosition().getPieceFor((Square)object), (Square)object, (View)imageView);
                return true;
            }
        } else if (object.getAction() == 2) {
            if (!this._drag) {
                if (Math.abs(this._startX - object.getX()) + Math.abs(this._startY - object.getY()) > 20.0f) {
                    bl = true;
                }
                this._drag = bl;
                if (this._drag) {
                    this._dragDelegate.onDragStart();
                    return true;
                }
            }
        } else if (object.getAction() == 1 && this._dragDelegate != null) {
            object = this.getSquareForCoordinates((int)object.getX(), (int)object.getY());
            this._dragDelegate.onSquareUp((Square)object, this._drag);
        }
        return true;
    }

    public void setDragDelegate(DragDelegate dragDelegate) {
        this._dragDelegate = dragDelegate;
    }

    public static interface DragDelegate {
        public void onDragStart();

        public void onPieceDown(Piece var1, Square var2, View var3);

        public void onSquareUp(Square var1, boolean var2);
    }

}
