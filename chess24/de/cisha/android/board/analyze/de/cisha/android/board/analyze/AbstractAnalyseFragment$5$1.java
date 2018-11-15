/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze;

import de.cisha.android.board.analyze.AbstractAnalyseFragment;

class AbstractAnalyseFragment
implements Runnable {
    AbstractAnalyseFragment() {
    }

    @Override
    public void run() {
        if (!5.this.this$0.advanceOneMove()) {
            5.this.this$0.stopAutoReplay();
        }
    }
}
