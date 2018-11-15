/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.widget.ImageView
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.ImageView;
import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.PieceSetup;

private class BoardView.MyAnimationListenerImageView
extends ImageView {
    private Animation _animation;
    private Animation.AnimationListener _externalListener;
    public Move _move;

    public BoardView.MyAnimationListenerImageView(Move move, Animation.AnimationListener animationListener, Context context, Animation animation) {
        super(context);
        this._externalListener = animationListener;
        this._animation = animation;
        this._move = move;
    }

    public void clearAnimation() {
        synchronized (this) {
            this._animation.cancel();
            super.clearAnimation();
            BoardView.this.getUiThreadHandler().postAtFrontOfQueue(new Runnable(){

                @Override
                public void run() {
                    MyAnimationListenerImageView.this.setVisibility(4);
                }
            });
            return;
        }
    }

    protected void onAnimationEnd() {
        synchronized (this) {
            super.onAnimationEnd();
            if (this._externalListener != null) {
                this._externalListener.onAnimationEnd(this._animation);
            }
            BoardView.this.getUiThreadHandler().postAtFrontOfQueue(new Runnable(){

                @Override
                public void run() {
                    BoardView.this.setBoardPosition(MyAnimationListenerImageView.this._move.getResultingPieceSetup());
                }
            });
            BoardView.this._animatedMovingPiece = null;
            return;
        }
    }

    public void onAnimationStart() {
        synchronized (this) {
            super.onAnimationStart();
            if (this._externalListener != null) {
                this._externalListener.onAnimationStart(this._animation);
            }
            return;
        }
    }

}
