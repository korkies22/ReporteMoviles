/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package de.cisha.android.board.setup;

import android.view.MotionEvent;
import android.view.View;
import de.cisha.android.board.setup.model.PositionHolder;
import de.cisha.android.board.setup.view.DragBoardView;
import de.cisha.android.board.setup.view.PieceView;
import de.cisha.android.board.view.DragView;
import de.cisha.chess.model.ModifyablePosition;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;

class SetupBoardActivity
implements View.OnTouchListener {
    SetupBoardActivity() {
    }

    public boolean onTouch(View object, MotionEvent object2) {
        switch (object2.getAction()) {
            default: {
                break;
            }
            case 1: {
                object = SetupBoardActivity.this._boardView.getSquareForRawCoordinates(object2.getRawX(), object2.getRawY());
                object2 = new ModifyablePosition(SetupBoardActivity.this._position);
                object2.setPiece(SetupBoardActivity.this._actualPiece, (Square)object);
                SetupBoardActivity.this._positionHolder.setPosition(object2.getSafePosition());
                break;
            }
            case 0: {
                SetupBoardActivity.this._dragView.startDrag((View)object);
                SetupBoardActivity.this.setActualPiece(((PieceView)((Object)object)).getPiece());
            }
        }
        return true;
    }
}
