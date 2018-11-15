/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import java.util.TimerTask;

class AbstractTimeoutIOAcknowledge
extends TimerTask {
    AbstractTimeoutIOAcknowledge() {
    }

    @Override
    public void run() {
        AbstractTimeoutIOAcknowledge.this.onTimeout();
    }
}
