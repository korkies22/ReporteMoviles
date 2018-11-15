/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIEngine;

private class UCIEngine.Waiter
extends UCIEngine.EngineOutputLineReceiver {
    private String _command;
    private boolean _commandReceived;
    private long _timeForTimeout;

    public UCIEngine.Waiter(String string) {
        super(UCIEngine.this, null);
        this._commandReceived = false;
        this._command = string;
    }

    @Override
    boolean receivedLine(String string) {
        if (!this._commandReceived) {
            this._commandReceived = string.startsWith(this._command);
        }
        return this._commandReceived;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean waitForCommand(long l) {
        this._timeForTimeout = System.currentTimeMillis() + l;
        do {
            if (this._commandReceived) {
                return true;
            }
            try {
                Thread.sleep(20L);
            }
            catch (InterruptedException interruptedException) {}
        } while (System.currentTimeMillis() < this._timeForTimeout);
        return false;
    }
}
