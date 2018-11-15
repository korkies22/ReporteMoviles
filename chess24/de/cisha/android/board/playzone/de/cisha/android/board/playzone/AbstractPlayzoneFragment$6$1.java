/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone;

import de.cisha.android.board.playzone.AbstractPlayzoneFragment;
import de.cisha.android.board.playzone.model.AbstractGameModel;

class AbstractPlayzoneFragment
implements Runnable {
    AbstractPlayzoneFragment() {
    }

    @Override
    public void run() {
        if (6.this.this$0._gameAdapter != null) {
            6.this.this$0.initBoard();
            6.this.this$0.initListener();
            6.this.this$0.updateUI();
        }
        6.this.this$0.updateUIOnce();
    }
}
