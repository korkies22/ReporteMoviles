/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view;

class BoardView
implements Runnable {
    BoardView() {
    }

    @Override
    public void run() {
        BoardView.this.rereadSettings();
    }
}
