/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.exercise;

import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;

class TacticsGameHolder
implements Runnable {
    final /* synthetic */ SEP val$computerSep;

    TacticsGameHolder(SEP sEP) {
        this.val$computerSep = sEP;
    }

    @Override
    public void run() {
        TacticsGameHolder.this.doMoveInCurrentPosition(this.val$computerSep);
    }
}
