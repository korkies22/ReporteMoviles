/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package de.cisha.android.board.view;

import android.graphics.Bitmap;

class RookieLoadingSurfaceView
implements Runnable {
    final /* synthetic */ int val$height;
    final /* synthetic */ int val$width;

    RookieLoadingSurfaceView(int n, int n2) {
        this.val$width = n;
        this.val$height = n2;
    }

    @Override
    public void run() {
        RookieLoadingSurfaceView.this._rookieBgLeft = this.val$width / 2 - RookieLoadingSurfaceView.this._rookieBg.getWidth() / 2;
        RookieLoadingSurfaceView.this._rookieBgTop = this.val$height / 2 - RookieLoadingSurfaceView.this._rookieBg.getHeight() / 2;
    }
}
