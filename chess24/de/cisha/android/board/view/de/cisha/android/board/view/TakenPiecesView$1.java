/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view;

class TakenPiecesView
implements Runnable {
    TakenPiecesView() {
    }

    @Override
    public void run() {
        TakenPiecesView.this.updateText();
    }
}
