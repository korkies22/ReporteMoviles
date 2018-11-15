/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.engine;

import de.cisha.chess.engine.UCIEngine;

class EngineController
implements Runnable {
    EngineController() {
    }

    @Override
    public void run() {
        if (EngineController.this._engine != null && EngineController.this._engine.start()) {
            EngineController.this.notifiyObserversEngineStart();
        }
    }
}
