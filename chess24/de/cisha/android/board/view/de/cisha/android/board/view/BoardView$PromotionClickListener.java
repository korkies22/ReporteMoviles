/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.view;

import android.view.View;
import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Square;

private class BoardView.PromotionClickListener
implements View.OnClickListener {
    private BoardView.PromotionClickListener() {
    }

    public void onClick(View object) {
        switch (object.getId()) {
            default: {
                object = null;
                break;
            }
            case 2131296886: {
                object = BoardView.this.createMove(BoardView.this._activePieceSquare, BoardView.this._promotionSquare, 0);
                break;
            }
            case 2131296885: {
                object = BoardView.this.createMove(BoardView.this._activePieceSquare, BoardView.this._promotionSquare, 3);
                break;
            }
            case 2131296884: {
                object = BoardView.this.createMove(BoardView.this._activePieceSquare, BoardView.this._promotionSquare, 1);
                break;
            }
            case 2131296883: {
                object = BoardView.this.createMove(BoardView.this._activePieceSquare, BoardView.this._promotionSquare, 2);
            }
        }
        BoardView.this._promotionPresenter.dismissDialog();
        BoardView.this.doMove((Move)object);
    }
}
