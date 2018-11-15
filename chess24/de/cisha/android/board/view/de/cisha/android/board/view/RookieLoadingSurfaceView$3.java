/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view;

class RookieLoadingSurfaceView
implements Runnable {
    RookieLoadingSurfaceView() {
    }

    @Override
    public void run() {
        RookieLoadingSurfaceView.this.startDrawingOperations();
    }
}
