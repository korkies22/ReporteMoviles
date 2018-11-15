/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.setup.view;

import android.view.View;
import de.cisha.android.board.setup.view.PieceBar;

class PieceBar
implements Runnable {
    PieceBar() {
    }

    @Override
    public void run() {
        if (!2.this.val$show) {
            2.this.this$0._overlayDelete.setVisibility(4);
            2.this.this$0._overlayDelete.clearAnimation();
        }
    }
}
