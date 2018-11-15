/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view;

import de.cisha.android.board.view.BoardView;

class BoardView.MyAnimationListenerImageView
implements Runnable {
    BoardView.MyAnimationListenerImageView() {
    }

    @Override
    public void run() {
        MyAnimationListenerImageView.this.setVisibility(4);
    }
}
