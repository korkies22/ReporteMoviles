/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.model.SEP;

private class UCIEngine.GetMoveOutPutReceiver
extends UCIEngine.EngineOutputLineReceiver {
    SEP sep;

    private UCIEngine.GetMoveOutPutReceiver() {
        super(UCIEngine.this, null);
    }

    @Override
    boolean receivedLine(String string) {
        if (string.startsWith("bestmove")) {
            String string2 = string.substring(9);
            int n = string2.indexOf(32);
            string = string2;
            if (n > 0) {
                string = string2.substring(0, n);
            }
            if (string.length() >= 4) {
                this.sep = UCIEngine.this.getSEP(string);
            }
            return true;
        }
        return false;
    }
}
