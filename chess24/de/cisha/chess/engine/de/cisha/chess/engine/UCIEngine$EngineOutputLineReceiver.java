/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIEngine;

private abstract class UCIEngine.EngineOutputLineReceiver {
    private UCIEngine.EngineOutputLineReceiver() {
    }

    abstract boolean receivedLine(String var1);
}
