/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

class UCIEngine
implements Runnable {
    final /* synthetic */ String val$answerToWaitFor;

    UCIEngine(String string) {
        this.val$answerToWaitFor = string;
    }

    @Override
    public void run() {
        UCIEngine.this.waitForCommandAnswer(this.val$answerToWaitFor);
    }
}
