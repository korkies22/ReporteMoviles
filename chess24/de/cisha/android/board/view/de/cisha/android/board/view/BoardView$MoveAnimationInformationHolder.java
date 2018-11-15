/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package de.cisha.android.board.view;

import android.view.animation.Animation;
import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.Move;

private static class BoardView.MoveAnimationInformationHolder {
    public Animation.AnimationListener listener;
    public Move move;
    public int timeInMills;

    public BoardView.MoveAnimationInformationHolder(Move move, Animation.AnimationListener animationListener, int n) {
        this.move = move;
        this.listener = animationListener;
        this.timeInMills = n;
    }
}
